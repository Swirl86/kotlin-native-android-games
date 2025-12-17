package com.swirl.pocketarcade.games.hangman

import com.swirl.pocketarcade.games.hangman.model.GuessResult

class HangmanGame(
    private val word: String,
    private val maxIncorrectGuesses: Int
) {
    private val guessedLetters = mutableSetOf<Char>()
    var incorrectGuesses = 0
        private set

    fun guess(letter: Char): GuessResult {
        val upperLetter = letter.uppercaseChar()
        if (guessedLetters.contains(upperLetter)) return GuessResult.ALREADY_GUESSED

        guessedLetters.add(upperLetter)
        return if (word.uppercase().contains(upperLetter)) GuessResult.CORRECT
        else {
            incorrectGuesses++
            GuessResult.INCORRECT
        }
    }

    fun getCurrentProgress(): String =
        word.map { if (guessedLetters.contains(it.uppercaseChar())) it else '_' }
            .joinToString(" ")

    fun isGameOver(): Boolean = incorrectGuesses >= maxIncorrectGuesses || isWon()
    fun isWon(): Boolean = word.uppercase().all { guessedLetters.contains(it) }

    fun reset() {
        guessedLetters.clear()
        incorrectGuesses = 0
    }

    fun getFullWord(): String = word
    fun getMaxIncorrectGuesses(): Int = maxIncorrectGuesses
}
