package com.swirl.pocketarcade

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.swirl.pocketarcade.tictactoe.TicTacToeViewModel
import com.swirl.pocketarcade.tictactoe.model.Moves
import com.swirl.pocketarcade.tictactoe.model.Player
import com.swirl.pocketarcade.tictactoe.model.PlayerType
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(JUnit4::class)
class TicTacToeViewModelTest {

    // This rule makes LiveData updates happen instantly
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Helper to create two human players
    private fun createPlayers(): Pair<Player, Player> {
        val player1 = Player(id = 1, type = PlayerType.HUMAN, mark = Moves.X, defaultName = "Player 1")
        val player2 = Player(id = 2, type = PlayerType.HUMAN, mark = Moves.O, defaultName = "Player 2")
        return player1 to player2
    }

    // Extension for checking empty board
    private fun TicTacToeViewModel.isBoardEmpty() = board.value!!.all { it.isEmpty() }

    @Test
    fun `initial state is correct`() {
        val (player1, player2) = createPlayers()
        val viewModel = TicTacToeViewModel(player1, player2)

        assertTrue(viewModel.isBoardEmpty())
        val current = viewModel.currentPlayer.value!!
        assertTrue(current == player1 || current == player2)
        assertNull(viewModel.winner.value)
        assertFalse(viewModel.isBoardFull.value!!)
    }

    @Test
    fun `reset game clears board and removes winner`() {
        val (player1, player2) = createPlayers()
        val viewModel = TicTacToeViewModel(player1, player2)

        // Make a move
        viewModel.makeMove(0)
        assertTrue(viewModel.board.value!![0].isNotEmpty())

        // Reset
        viewModel.resetGame()

        assertTrue(viewModel.isBoardEmpty())
        assertNull(viewModel.winner.value)
        assertFalse(viewModel.isBoardFull.value!!)
    }

    @Test
    fun `makeMove alternates turns between human players`() {
        val (player1, player2) = createPlayers()
        val viewModel = TicTacToeViewModel(player1, player2)

        val firstPlayer = viewModel.currentPlayer.value!!
        viewModel.makeMove(0)
        val secondPlayer = viewModel.currentPlayer.value!!

        assertNotEquals(firstPlayer, secondPlayer)
        assertEquals(firstPlayer.mark.name, viewModel.board.value!![0])
    }
}