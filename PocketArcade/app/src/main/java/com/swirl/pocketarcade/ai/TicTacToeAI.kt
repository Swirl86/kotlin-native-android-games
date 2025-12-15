package com.swirl.pocketarcade.ai

import com.swirl.pocketarcade.games.tictactoe.TicTacToeGame
import com.swirl.pocketarcade.games.tictactoe.model.Moves
import com.swirl.pocketarcade.utils.TicTacToeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        scope.launch {
            delay(500L)

            val winMove = findWinningMove(game.currentPlayer.mark)
            if (winMove != -1) { game.makeMove(winMove); onMoveMade(); return@launch }

            val opponentMark = if (game.currentPlayer.mark == Moves.X) Moves.O else Moves.X
            val blockMove = findWinningMove(opponentMark)
            if (blockMove != -1) { game.makeMove(blockMove); onMoveMade(); return@launch }

            val emptyCells = game.board.mapIndexedNotNull { index, cell -> if (cell.isEmpty()) index else null }
            if (emptyCells.isNotEmpty()) {
                game.makeMove(emptyCells.random())
                onMoveMade()
            }
        }
    }

    private fun findWinningMove(mark: Moves): Int {
        for (line in TicTacToeUtils.WINNING_LINES) {
            val (a, b, c) = line
            val values = arrayOf(game.board[a], game.board[b], game.board[c])
            if (values.count { it == mark.name } == 2 && values.count { it.isEmpty() } == 1) {
                return line[values.indexOf("")]
            }
        }
        return -1
    }
}