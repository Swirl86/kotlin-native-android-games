package com.swirl.pocketarcade.hangman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swirl.pocketarcade.hangman.model.GuessResult
import com.swirl.pocketarcade.hangman.model.HangmanStatus

class HangmanViewModel(maxIncorrectGuesses: Int = 6) : ViewModel() {

    private val game = HangmanGame(maxIncorrectGuesses)

    private val _status = MutableLiveData(getStatus())
    val status: LiveData<HangmanStatus> = _status

    val fullWord: String
        get() = game.getFullWord()

    fun guessLetter(letter: Char): GuessResult {
        val result = game.guess(letter)
        _status.value = getStatus()
        return result
    }

    fun resetGame() {
        game.reset()
        _status.value = getStatus()
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
