package com.swirl.pocketarcade.games.hangman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swirl.pocketarcade.games.hangman.model.GuessResult
import com.swirl.pocketarcade.games.hangman.model.HangmanPart
import com.swirl.pocketarcade.games.hangman.model.HangmanStatus
import com.swirl.pocketarcade.utils.HangmanUtils

class HangmanViewModel(private var  maxIncorrectGuesses: Int = 6) : ViewModel() {

    private var game: HangmanGame = HangmanGame(maxIncorrectGuesses)

    private val _visibleParts = MutableLiveData<List<HangmanPart>>(emptyList())
    val visibleParts: LiveData<List<HangmanPart>> = _visibleParts

    private val _status = MutableLiveData(getStatus())
    val status: LiveData<HangmanStatus> = _status

    val fullWord: String
        get() = game.getFullWord()

    fun startNewGame(maxIncorrect: Int = maxIncorrectGuesses) {
        maxIncorrectGuesses = maxIncorrect
        game = HangmanGame(maxIncorrectGuesses)
        _visibleParts.value = emptyList()
        _status.value = getStatus()
    }

    fun guessLetter(letter: Char): GuessResult {
        val result = game.guess(letter)
        updateVisibleParts()
        _status.value = getStatus()
        return result
    }

    fun resetGame(maxIncorrect: Int = maxIncorrectGuesses) {
        startNewGame(maxIncorrect)
    }

    private fun updateVisibleParts() {
        val parts = HangmanUtils.getPartsForIncorrectGuess(
            incorrectGuesses = game.incorrectGuesses,
            maxIncorrectGuesses = game.getMaxIncorrectGuesses()
        ).toMutableList()

        if (game.isGameOver() && HangmanPart.FACE !in parts) {
            parts.add(HangmanPart.FACE)
        }

        _visibleParts.value = parts
    }

    private fun getStatus(): HangmanStatus {
        return HangmanStatus(
            currentProgress = game.getCurrentProgress(),
            incorrectGuesses = game.incorrectGuesses,
            maxIncorrectGuesses = game.getMaxIncorrectGuesses(),
            isGameOver = game.isGameOver(),
            isWon = game.isWon()
        )
    }
}