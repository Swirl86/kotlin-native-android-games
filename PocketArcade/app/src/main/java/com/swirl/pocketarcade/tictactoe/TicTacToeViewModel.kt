package com.swirl.pocketarcade.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swirl.pocketarcade.ai.TicTacToeAI

class TicTacToeViewModel(
    val numPlayers: Int = 1 // 1 = computer, 2 = two players
) : ViewModel() {

    private val game = TicTacToeGame()
    private val ai = TicTacToeAI(game) {
        updateLiveData() // LiveData is only updated when the AI has made the move
        _isPlayerTurn.value = true
    }

    private val _board = MutableLiveData(game.board.copyOf())
    val board: LiveData<Array<String>> = _board

    private val _currentPlayer = MutableLiveData(game.currentPlayer)
    val currentPlayer: LiveData<TicTacToeGame.Player> = _currentPlayer

    private val _isPlayerTurn = MutableLiveData(
        numPlayers == 2 || game.currentPlayer == TicTacToeGame.Player.X
    )

    private val _winner = MutableLiveData<TicTacToeGame.Player?>(game.winner)
    val winner: LiveData<TicTacToeGame.Player?> = _winner

    fun makeMove(position: Int) {
        if (_isPlayerTurn.value == false) return
        if (!game.makeMove(position)) return
        updateLiveData()

        // AI movement for single player mode
        if (numPlayers == 1 && game.winner == null && game.currentPlayer == TicTacToeGame.Player.O) {
            _isPlayerTurn.value = false
            ai.makeMove()
        }
    }

    private fun updateLiveData() {
        _board.value = game.board.copyOf()
        _currentPlayer.value = game.currentPlayer
        _winner.value = game.winner
    }

    fun resetGame() {
        game.reset()
        updateLiveData()
    }
}
