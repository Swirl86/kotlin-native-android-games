package com.swirl.pocketarcade.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swirl.pocketarcade.ai.TicTacToeAI

class TicTacToeViewModel(
    val numPlayers: Int = 1 // 1 = vs AI, 2 = two players
) : ViewModel() {

    private val game = TicTacToeGame()
    private val ai = TicTacToeAI(game) {
        updateLiveData()
        updateTurnStates()
        _isPlayerTurn.value = true
    }

    private val _board = MutableLiveData(game.board.copyOf())
    val board: LiveData<Array<String>> = _board

    private val _currentPlayer = MutableLiveData(game.currentPlayer)
    val currentPlayer: LiveData<TicTacToeGame.Player> = _currentPlayer

    private val _winner = MutableLiveData<TicTacToeGame.Player?>(game.winner)
    val winner: LiveData<TicTacToeGame.Player?> = _winner

    private val _isPlayerTurn = MutableLiveData<Boolean>(false)

    private val _isAiTurn = MutableLiveData<Boolean>(false)

    init {
        updateTurnStates()
        if (_isAiTurn.value == true) makeAiMove()
    }

    /** Updates turn state for both player and AI */
    private fun updateTurnStates() {
        val current = game.currentPlayer

        _isPlayerTurn.value =
            (numPlayers == 2 || current == TicTacToeGame.Player.X)

        _isAiTurn.value =
            (numPlayers == 1 && current == TicTacToeGame.Player.O)
    }

    private fun updateLiveData() {
        _board.value = game.board.copyOf()
        _currentPlayer.value = game.currentPlayer
        _winner.value = game.winner
    }

    private fun makeAiMove() {
        _isPlayerTurn.value = false
        ai.makeMove()
    }

    fun makeMove(position: Int) {
        if (_isPlayerTurn.value == false) return
        if (!game.makeMove(position)) return

        updateLiveData()
        updateTurnStates()

        if (_isAiTurn.value == true && game.winner == null) {
            makeAiMove()
        }
    }

    fun resetGame() {
        game.reset()
        updateLiveData()
        updateTurnStates()

        if (_isAiTurn.value == true) makeAiMove()
    }
}