package com.swirl.pocketarcade.games.hangman

import com.swirl.pocketarcade.games.hangman.model.GuessResult
import com.swirl.pocketarcade.games.hangman.model.HangmanGameState

class HangmanGame {

    fun guess(
        state: HangmanGameState,
        letter: Char
    ): Pair<HangmanGameState, GuessResult> {

        val upper = letter.uppercaseChar()

        if (state.guessedLetters.contains(letter)) {
            return state to GuessResult.ALREADY_GUESSED
        }

        val newGuessed = state.guessedLetters + upper

        return if (state.word.contains(upper)) {
            state.copy(guessedLetters = newGuessed) to GuessResult.CORRECT
        } else {
            state.copy(
                guessedLetters = newGuessed,
                incorrectGuesses = state.incorrectGuesses + 1
            ) to GuessResult.INCORRECT
        }
    }
}