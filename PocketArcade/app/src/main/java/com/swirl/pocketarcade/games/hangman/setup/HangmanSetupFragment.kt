package com.swirl.pocketarcade.games.hangman.setup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentHangmanSetupBinding

class HangmanSetupFragment : Fragment(R.layout.fragment_hangman_setup) {

    private val viewModel: HangmanSetupViewModel by viewModels()

    private var _binding: FragmentHangmanSetupBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHangmanSetupBinding.bind(view)

        binding.btnClose.setOnClickListener {
            findNavController()
                .navigate(R.id.action_hangmanSetupFragment_to_menuFragment)
        }

        binding.btnStartGame.setOnClickListener {
            viewModel.setPlayerName(binding.etPlayerName.text.toString())
            viewModel.setDifficulty(getSelectedDifficulty())

            val action =
                HangmanSetupFragmentDirections
                    .actionHangmanSetupFragmentToHangmanFragment(
                        playerName = viewModel.playerName,
                        maxIncorrectGuesses = viewModel.maxIncorrectGuesses
                    )

            findNavController().navigate(action)
        }
    }

    private fun getSelectedDifficulty(): Int =
        when (binding.rgDifficulty.checkedRadioButtonId) {
            R.id.rb_4 -> 4
            R.id.rb_6 -> 6
            R.id.rb_8 -> 8
            R.id.rb_10 -> 10
            R.id.rb_11 -> 11
            else -> 8
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}