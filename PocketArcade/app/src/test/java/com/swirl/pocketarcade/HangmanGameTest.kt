package com.swirl.pocketarcade

import com.swirl.pocketarcade.games.hangman.HangmanGame
import com.swirl.pocketarcade.games.hangman.model.GuessResult
import com.swirl.pocketarcade.games.hangman.model.HangmanGameState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HangmanGameTest {

    private lateinit var game: HangmanGame

    @Before
    fun setup() {
        game = HangmanGame()
    }

    @Test
    fun `guess correct letter`() {
        val state = game.guess(HangmanGameState(word = "KOTLIN"), 'K')
        assertEquals(GuessResult.CORRECT, state.second)
        assertEquals("K _ _ _ _ _", state.first.currentProgress)
    }

    @Test
    fun `guess incorrect letter`() {
        val state = game.guess(HangmanGameState(word = "KOTLIN"), 'A')
        assertEquals(GuessResult.INCORRECT, state.second)
        assertEquals(1, state.first.incorrectGuesses)
    }

    @Test
    fun `guess already guessed letter`() {
        val initial = HangmanGameState(word = "KOTLIN", guessedLetters = setOf('K'))
        val state = game.guess(initial, 'K')
        assertEquals(GuessResult.ALREADY_GUESSED, state.second)
    }

    @Test
    fun `winning game`() {
        var state = HangmanGameState(word = "KO", guessedLetters = setOf('K'))
        state = game.guess(state, 'O').first
        assertTrue(state.isWon)
        assertTrue(state.isGameOver)
    }

    @Test
    fun `losing game`() {
        var state = HangmanGameState(word = "A", incorrectGuesses = 5, maxIncorrectGuesses = 6)
        state = game.guess(state, 'B').first
        assertEquals(6, state.incorrectGuesses)
        assertTrue(state.isGameOver)
        assertFalse(state.isWon)
    }
}