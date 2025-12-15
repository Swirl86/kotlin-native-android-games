package com.swirl.pocketarcade.games.tictactoe

import com.swirl.pocketarcade.games.tictactoe.model.Player
import com.swirl.pocketarcade.utils.TicTacToeUtils

class TicTacToeGame(
    val player1: Player,
    val player2: Player
) {
    var board = Array(9) { "" }
    var currentPlayer: Player
    var winner: Player? = null

    init {
        currentPlayer = getRandomPlayer()
    }

    fun makeMove(position: Int): Boolean {
        if (board[position].isNotEmpty() || winner != null) return false

        board[position] = currentPlayer.mark.name
        checkWinner()
        if (winner == null) {
            currentPlayer = if (currentPlayer == player1) player2 else player1
        }
        return true
    }

    private fun checkWinner() {
        for (line in TicTacToeUtils.WINNING_LINES) {
            val (a, b, c) = line
            if (board[a].isNotEmpty() && board[a] == board[b] && board[a] == board[c]) {
                winner = if (board[a] == player1.mark.name) player1 else player2
            }
        }
    }

    fun reset() {
        board = Array(9) { "" }
        winner = null
        currentPlayer = getRandomPlayer()
    }

    private fun getRandomPlayer(): Player = if ((0..1).random() == 0) player1 else player2
}
