package com.swirl.pocketarcade.tictactoe

class TicTacToeGame {
    enum class Player { X, O }
    var board = Array(9) { "" }
    var currentPlayer = Player.X
    var winner: Player? = null

    fun makeMove(position: Int): Boolean {
        if (board[position].isNotEmpty() || winner != null) return false
        board[position] = currentPlayer.name
        checkWinner()
        if (winner == null) currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        return true
    }

    private fun checkWinner() {
        val lines = arrayOf(
            intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8), // rows
            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8), // cols
            intArrayOf(0,4,8), intArrayOf(2,4,6)                      // diagonals
        )

        for (line in lines) {
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
