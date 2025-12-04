package com.swirl.pocketarcade.tictactoe

import com.swirl.pocketarcade.utils.TicTacToeUtils

class TicTacToeGame {
    enum class Player { X, O }
    var board = Array(9) { "" }
    var currentPlayer: Player
    var winner: Player? = null

    init {
        // Randomize who starts
        currentPlayer = if ((0..1).random() == 0) Player.X else Player.O
    }

    fun makeMove(position: Int): Boolean {
        if (board[position].isNotEmpty() || winner != null) return false

        board[position] = currentPlayer.name
        checkWinner()
        if (winner == null) {
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        }
        return true
    }

    private fun checkWinner() {
        for (line in TicTacToeUtils.WINNING_LINES) {
            val (a,b,c) = line
            if (board[a].isNotEmpty() && board[a] == board[b] && board[a] == board[c]) {
                winner = if (board[a] == Player.X.name) Player.X else Player.O
            }
        }
    }

    fun reset() {
        board = Array(9) { "" }
        currentPlayer = Player.X
        winner = null
    }
}
