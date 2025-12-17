package com.swirl.pocketarcade.games.hangman.model

import com.swirl.pocketarcade.utils.HangmanUtils

data class HangmanGameState(
    val word: String,
    val guessedLetters: Set<Char> = emptySet(),
    val incorrectGuesses: Int = 0,
    val maxIncorrectGuesses: Int = 6,
    val isLoading: Boolean = false
) {
    val currentProgress: String
        get() = word.map { char ->
            if (guessedLetters.contains(char)) char else '_'
        }.joinToString(" ")

    val isWon: Boolean
        get() = word.all { guessedLetters.contains(it) }

    val isGameOver: Boolean
        get() = incorrectGuesses >= maxIncorrectGuesses || isWon

    val visibleParts: List<HangmanPart>
        get() {
            val parts = HangmanUtils.getPartsForIncorrectGuess(
                incorrectGuesses = incorrectGuesses,
                maxIncorrectGuesses = maxIncorrectGuesses
            ).toMutableList()

            if (isGameOver && HangmanPart.FACE !in parts) {
                parts.add(HangmanPart.FACE)
            }
            return parts
        }
}