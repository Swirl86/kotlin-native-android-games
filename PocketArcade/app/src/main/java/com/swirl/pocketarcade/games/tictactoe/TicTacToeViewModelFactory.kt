package com.swirl.pocketarcade.games.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swirl.pocketarcade.games.tictactoe.model.Player

class TicTacToeViewModelFactory(
    private val player1: Player,
    private val player2: Player
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return runCatching {
            if (modelClass.isAssignableFrom(TicTacToeViewModel::class.java)) {
                TicTacToeViewModel(player1, player2) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }.getOrElse { throw it }
    }
}