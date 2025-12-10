package com.swirl.pocketarcade.hangman

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentHangmanBinding
import kotlin.random.Random

class HangmanFragment : Fragment(R.layout.fragment_hangman) {

    private var _binding: FragmentHangmanBinding? = null
    private val binding get() = _binding!!

    private val words = listOf("KOTLIN", "ANDROID", "GAME", "PLAY")
    private lateinit var currentWord: String
    private var guessedLetters = mutableSetOf<Char>()
    private var remainingLives = 6

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHangmanBinding.bind(view)

        startNewGame()

        binding.btnGuess.setOnClickListener {
            val input = binding.etLetter.text.toString().uppercase()
            if (input.isNotEmpty()) {
                guessLetter(input[0])
                binding.etLetter.text.clear()
            }
        }
    }

    private fun startNewGame() {
        currentWord = words[Random.nextInt(words.size)].uppercase()
        guessedLetters.clear()
        remainingLives = 6
        updateDisplay()
    }

    private fun guessLetter(letter: Char) {
        if (guessedLetters.contains(letter)) {
            Toast.makeText(requireContext(), "Already guessed $letter", Toast.LENGTH_SHORT).show()
            return
        }

        guessedLetters.add(letter)
        if (!currentWord.contains(letter)) {
            remainingLives--
        }

        updateDisplay()
        checkGameOver()
    }

    private fun updateDisplay() {
        val display = currentWord.map { if (guessedLetters.contains(it)) it else '_' }.joinToString(" ")
        binding.tvWord.text = display
        binding.tvLives.text = "Lives: $remainingLives"
        binding.tvGuessed.text = "Guessed: ${guessedLetters.joinToString(", ")}"
    }

    private fun checkGameOver() {
        if (!binding.tvWord.text.contains('_')) {
            Toast.makeText(requireContext(), "You won!", Toast.LENGTH_LONG).show()
            startNewGame()
        } else if (remainingLives <= 0) {
            Toast.makeText(requireContext(), "You lost! Word was $currentWord", Toast.LENGTH_LONG).show()
            startNewGame()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}