package com.swirl.pocketarcade.games.hangman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swirl.pocketarcade.data.repository.WordRepository
import com.swirl.pocketarcade.games.hangman.model.GuessResult
import com.swirl.pocketarcade.games.hangman.model.HangmanPart
import com.swirl.pocketarcade.games.hangman.model.HangmanStatus
import com.swirl.pocketarcade.utils.HangmanUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HangmanViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private lateinit var game: HangmanGame

    private val _visibleParts = MutableLiveData<List<HangmanPart>>(emptyList())
    val visibleParts: LiveData<List<HangmanPart>> = _visibleParts

    private val _status = MutableLiveData<HangmanStatus>()
    val status: LiveData<HangmanStatus> = _status

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    val fullWord: String
        get() = game.getFullWord()

    init {
        startNewGame()
    }

    fun startNewGame(maxIncorrect: Int = 6) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val word = repository.fetchRandomWord()
                game = HangmanGame(word, maxIncorrect)
            } catch (e: Exception) {
                val fallbackWord = HangmanUtils.getRandomDefaultWord()
                game = HangmanGame(fallbackWord, maxIncorrect)
            } finally {
                _visibleParts.value = emptyList()
                _status.value = getStatus()
                _isLoading.value = false
            }
        }
    }

    fun resetGame(maxIncorrect: Int = game.getMaxIncorrectGuesses()) {
        _isLoading.value = true
        viewModelScope.launch {
            val word = try {
                repository.fetchRandomWord()
            } catch (e: Exception) {
                HangmanUtils.getRandomDefaultWord()
            }

            game = HangmanGame(word, maxIncorrect)
            _visibleParts.value = emptyList()
            _status.value = getStatus()
            _isLoading.value = false
        }
    }

    fun guessLetter(letter: Char): GuessResult {
        val result = game.guess(letter)
        updateVisibleParts()
        _status.value = getStatus()
        return result
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