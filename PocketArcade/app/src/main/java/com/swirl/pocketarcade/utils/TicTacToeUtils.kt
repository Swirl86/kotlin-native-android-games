package com.swirl.pocketarcade.utils

/**
 * TicTacToe utility constants and helper functions
 */
object TicTacToeUtils {

    /**
     * All possible winning lines for a 3x3 TicTacToe board
     * Each line is an array of 3 indices
     */
    val WINNING_LINES: Array<IntArray> = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // rows
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // columns
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                       // diagonals
    )
}
