package com.swirl.pocketarcade.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swirl.pocketarcade.tictactoe.model.Moves
import com.swirl.pocketarcade.tictactoe.model.Player
import com.swirl.pocketarcade.tictactoe.model.PlayerType
import com.swirl.pocketarcade.utils.TicTacToeUtils

class PlayerSetupViewModel : ViewModel() {
    private val _numPlayers = MutableLiveData(1)
    val numPlayers: LiveData<Int> = _numPlayers

    private val _player1 = MutableLiveData<Player>()
    val player1: LiveData<Player> = _player1

    private val _player2 = MutableLiveData<Player>()
    val player2: LiveData<Player> = _player2

    init {
        setupPlayers()
    }

    private fun setupPlayers() {
        val moves = randomizeMoves()
        _player1.value = Player(
            id = 1,
            type = PlayerType.HUMAN,
            mark = moves[0],
            defaultName = "Player 1"
        )
        _player2.value = Player(
            id = 2,
            type = PlayerType.AI,
            mark = moves[1],
            defaultName = TicTacToeUtils.getRandomAiName()
        )
    }
    private fun randomizeMoves(): List<Moves> = listOf(Moves.X, Moves.O).shuffled()

    fun setNumPlayers(count: Int) {
        _numPlayers.value = count
        if (count == 1) { // player vs AI
            val moves = listOf(Moves.X, Moves.O).shuffled()
            _player1.value = _player1.value?.copy(mark = moves[0])
            _player2.value = Player(
                id = 2,
                type = PlayerType.AI,
                mark = moves[1],
                defaultName = TicTacToeUtils.getRandomAiName()
            )
        } else { // Two players
            val moves = listOf(Moves.X, Moves.O).shuffled()
            _player1.value = _player1.value?.copy(type = PlayerType.HUMAN, mark = moves[0])
            _player2.value = _player2.value?.copy(type = PlayerType.HUMAN, mark = moves[1])
        }
    }

    fun setPlayerName(playerId: Int, name: String) {
        if (playerId == 1) {
            _player1.value = _player1.value?.copy(
                name = name.ifBlank { _player1.value?.defaultName ?: "Player 1" }
            )
        } else {
            _player2.value = _player2.value?.copy(
                name = name.ifBlank {
                    if (_numPlayers.value == 1) TicTacToeUtils.getRandomAiName()
                    else _player2.value?.defaultName ?: "Player 2"
                }
            )
        }
    }
}
