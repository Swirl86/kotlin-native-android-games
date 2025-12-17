package com.swirl.pocketarcade.games.hangman

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentHangmanBinding
import com.swirl.pocketarcade.utils.HangmanUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HangmanFragment : Fragment(R.layout.fragment_hangman) {

    private val viewModel: HangmanViewModel by viewModels()
    private var _binding: FragmentHangmanBinding? = null
    private val binding get() = _binding!!

    private lateinit var hangmanDrawable: HangmanDrawable

    private var maxIncorrectGuesses: Int = 6

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHangmanBinding.bind(view)

        arguments?.let { bundle ->
            maxIncorrectGuesses = HangmanFragmentArgs.fromBundle(bundle).maxIncorrectGuesses
        }

        viewModel.startNewGame(maxIncorrectGuesses)

        hangmanDrawable = HangmanDrawable()
        binding.ivHangman.setImageDrawable(hangmanDrawable)

        setupLetterButtons()
        setupObservers()

        binding.btnReset.setOnClickListener {
            viewModel.resetGame(maxIncorrectGuesses)
            resetLetterButtons()
        }

        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.action_hangmanFragment_to_menuFragment)
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.loadingOverlay.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.visibleParts.observe(viewLifecycleOwner) { parts ->
            hangmanDrawable.setVisibleParts(parts)
        }

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            binding.tvWord.text = status.currentProgress
            binding.tvGuessed.text = getString(
                R.string.hangman_incorrect,
                status.incorrectGuesses,
                status.maxIncorrectGuesses
            )

            // Disable all letters if game over
            for (i in 0 until binding.gridLetters.childCount) {
                binding.gridLetters.getChildAt(i).isEnabled = !status.isGameOver
            }

            if (status.isGameOver) {
                binding.tvWord.text = viewModel.fullWord
                val message = if (status.isWon) {
                    getString(R.string.you_won)
                } else {
                    getString(R.string.game_over)
                }
                binding.tvGuessed.text = message
            }
        })
    }

    private fun setupLetterButtons() {
        for (letter in HangmanUtils.SWEDISH_ALPHABET) {
            val button = Button(requireContext()).apply {
                text = letter.toString()
                layoutParams = createLetterButtonLayoutParams()
                setOnClickListener {
                    viewModel.guessLetter(letter)
                    // Disable button and update appearance
                    isEnabled = false
                    setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }

            binding.gridLetters.addView(button)
        }
    }

    private fun createLetterButtonLayoutParams(): GridLayout.LayoutParams {
        return GridLayout.LayoutParams().apply {
            width = 0
            height = GridLayout.LayoutParams.WRAP_CONTENT
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            setMargins(4, 4, 4, 4)
        }
    }

    private fun resetLetterButtons() {
        binding.gridLetters.removeAllViews()
        setupLetterButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}