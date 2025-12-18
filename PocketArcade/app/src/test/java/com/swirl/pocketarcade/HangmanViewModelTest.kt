package com.swirl.pocketarcade

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.swirl.pocketarcade.data.repository.WordRepository
import com.swirl.pocketarcade.games.hangman.HangmanViewModel
import com.swirl.pocketarcade.games.hangman.model.GuessResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HangmanViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: WordRepository = mockk()
    private lateinit var viewModel: HangmanViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HangmanViewModel(repository)
    }

    @Test
    fun `start new game sets loading and word`() = runTest {
        coEvery { repository.fetchRandomWord() } returns "KOTLIN"

        viewModel.startNewGame(6)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertNotNull(state)
        assertEquals("KOTLIN", state?.word)
        assertFalse(state!!.isLoading)
    }

    @Test
    fun `guess letter updates state correctly`() = runTest {
        coEvery { repository.fetchRandomWord() } returns "KOTLIN"

        viewModel.startNewGame(6)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.guessLetter('K')
        val state = viewModel.state.value

        assertEquals(GuessResult.CORRECT, result)
        assertTrue(state!!.guessedLetters.contains('K'))
        assertEquals("K _ _ _ _ _", state.currentProgress)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}