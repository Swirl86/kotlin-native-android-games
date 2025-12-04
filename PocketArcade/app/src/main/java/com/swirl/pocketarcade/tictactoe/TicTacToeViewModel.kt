package com.swirl.pocketarcade.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicTacToeViewModel : ViewModel() {

    private val game = TicTacToeGame()

    private val _board = MutableLiveData(game.board.copyOf())
    val board: LiveData<Array<String>> = _board

    private val _currentPlayer = MutableLiveData(game.currentPlayer)
    val currentPlayer: LiveData<TicTacToeGame.Player> = _currentPlayer

    private val _winner = MutableLiveData<TicTacToeGame.Player?>(game.winner)
    val winner: LiveData<TicTacToeGame.Player?> = _winner

    fun makeMove(position: Int) {
        if (game.makeMove(position)) {
            _board.value = game.board.copyOf()
            _currentPlayer.value = game.currentPlayer
            _winner.value = game.winner
        }
    }

    fun resetGame() {
        game.reset()
        _board.value = game.board.copyOf()
        _currentPlayer.value = game.currentPlayer
        _winner.value = game.winner
    }
}
