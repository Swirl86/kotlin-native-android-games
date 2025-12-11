package com.swirl.pocketarcade.hangman

import com.swirl.pocketarcade.hangman.model.GuessResult

class HangmanGame(
    private val maxIncorrectGuesses: Int
) {
    private var word: String = generateWord()
    private val guessedLetters = mutableSetOf<Char>()
    var incorrectGuesses = 0
        private set

    companion object {
        private val words = listOf("KOTLIN", "ANDROID", "HANGMAN", "COMPOSE", "FRAGMENT")
        fun generateWord(): String = words.random()
    }

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

    fun reset(newWord: String = generateWord()) {
        word = newWord
        guessedLetters.clear()
        incorrectGuesses = 0
    }

    fun getFullWord(): String = word
    fun getMaxIncorrectGuesses(): Int = maxIncorrectGuesses
}
