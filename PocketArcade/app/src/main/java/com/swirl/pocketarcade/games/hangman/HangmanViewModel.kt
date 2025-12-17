package com.swirl.pocketarcade.games.hangman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swirl.pocketarcade.data.repository.WordRepository
import com.swirl.pocketarcade.games.hangman.model.GuessResult
import com.swirl.pocketarcade.games.hangman.model.HangmanGameState
import com.swirl.pocketarcade.utils.HangmanUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HangmanViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private val game = HangmanGame()

    private val _state = MutableLiveData<HangmanGameState>()
    val state: LiveData<HangmanGameState> = _state

    fun startNewGame(maxIncorrect: Int) {
        viewModelScope.launch {
            _state.value = HangmanGameState(
                word = "",
                isLoading = true
            )

            val word = try {
                repository.fetchRandomWord()
            } catch (_: Exception) {
                HangmanUtils.getRandomDefaultWord()
            }

            _state.value = HangmanGameState(
                word = word.uppercase(),
                maxIncorrectGuesses = maxIncorrect,
                isLoading = false
            )
        }
    }

    fun guessLetter(letter: Char): GuessResult {
        val current = _state.value ?: return GuessResult.ALREADY_GUESSED
        val (newState, result) = game.guess(current, letter)
        _state.value = newState
        return result
    }

    fun resetGame(maxIncorrect: Int) {
        startNewGame(maxIncorrect)
    }
}