package com.swirl.pocketarcade.tictactoe

import TicTacToeGame
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swirl.pocketarcade.ai.TicTacToeAI
import com.swirl.pocketarcade.tictactoe.model.Player
import com.swirl.pocketarcade.tictactoe.model.PlayerType

class TicTacToeViewModel(
    player1: Player,
    player2: Player
) : ViewModel() {

    private val game = TicTacToeGame(player1, player2)
    private val ai = TicTacToeAI(game) {
        updateLiveData()
        updateTurnStates()
        _isPlayerTurn.value = true
    }

    private val _board = MutableLiveData(game.board.copyOf())
    val board: LiveData<Array<String>> = _board

    private val _currentPlayer = MutableLiveData(game.currentPlayer)
    val currentPlayer: LiveData<Player> = _currentPlayer

    private val _winner = MutableLiveData(game.winner)
    val winner: LiveData<Player?> = _winner

    private val _isPlayerTurn = MutableLiveData(false)
    private val _isAiTurn = MutableLiveData(false)

    init {
        updateTurnStates()
        if (_isAiTurn.value == true) makeAiMove()
    }

    private fun updateTurnStates() {
        val current = game.currentPlayer
        _isPlayerTurn.value = current.type == PlayerType.HUMAN
        _isAiTurn.value = current.type == PlayerType.AI
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

        if (_isAiTurn.value == true && game.winner == null) makeAiMove()
    }

    fun resetGame() {
        game.reset()
        updateLiveData()
        updateTurnStates()
        if (_isAiTurn.value == true) makeAiMove()
    }
}