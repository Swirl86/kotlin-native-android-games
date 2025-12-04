package com.swirl.pocketarcade.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TicTacToeViewModelFactory(private val numPlayers: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TicTacToeViewModel(numPlayers) as T
    }
}
