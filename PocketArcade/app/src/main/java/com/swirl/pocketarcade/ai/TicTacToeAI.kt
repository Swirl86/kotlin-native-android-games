package com.swirl.pocketarcade.ai

import com.swirl.pocketarcade.tictactoe.TicTacToeGame
import com.swirl.pocketarcade.utils.TicTacToeUtils
import kotlinx.coroutines.*

/**
 * Simple AI for TicTacToe.
 * - Chooses moves based on a basic strategy:
 *   1. Win if possible
 *   2. Block opponent if they can win next
 *   3. Otherwise, pick a random empty cell
 *
 * @param game The TicTacToeGame instance to make moves on
 */
class TicTacToeAI(private val game: TicTacToeGame, private val onMoveMade: () -> Unit) {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun makeMove() {
        if (game.winner != null) return

        // Delay to simulate thinking
        scope.launch {
            delay(500L) // 0.5 second delay

            // Try winning move
            val winMove = findWinningMove(game.currentPlayer)
            if (winMove != -1) {
                game.makeMove(winMove)
                onMoveMade();
                return@launch
            }

            // Try blocking opponent
            val opponent = if (game.currentPlayer == TicTacToeGame.Player.X)
                TicTacToeGame.Player.O else TicTacToeGame.Player.X
            val blockMove = findWinningMove(opponent)
            if (blockMove != -1) {
                game.makeMove(blockMove)
                onMoveMade();
                return@launch
            }

            // Else, random move
            val emptyCells = game.board.mapIndexedNotNull { index, cell ->
                if (cell.isEmpty()) index else null
            }
            if (emptyCells.isNotEmpty()) {
                val position = emptyCells.random()
                game.makeMove(position)
                onMoveMade();
            }
        }
    }

    // Returns the index of a winning move for the given player, or -1 if none
    private fun findWinningMove(player: TicTacToeGame.Player): Int {
        for (line in TicTacToeUtils.WINNING_LINES) {
            val (a, b, c) = line
            val values = arrayOf(game.board[a], game.board[b], game.board[c])
            // If two are the player's and the third is empty, that's a winning/block move
            if (values.count { it == player.name } == 2 && values.count { it.isEmpty() } == 1) {
                return line[values.indexOf("")]
            }
        }
        return -1
    }
}
