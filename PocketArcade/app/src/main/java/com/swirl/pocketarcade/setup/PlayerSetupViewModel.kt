package com.swirl.pocketarcade.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerSetupViewModel : ViewModel() {

    private val computerNames = listOf("AI Bot", "RoboPlayer", "MegaMind", "TicTacMaster")

    private val _numPlayers = MutableLiveData(1)
    val numPlayers: LiveData<Int> = _numPlayers

    private val _player1Name = MutableLiveData("Player 1")
    val player1Name: LiveData<String> = _player1Name

    private val _player2Name = MutableLiveData(computerNames.random())
    val player2Name: LiveData<String> = _player2Name

    private val _isPlayer2Enabled = MutableLiveData(false)
    val isPlayer2Enabled: LiveData<Boolean> = _isPlayer2Enabled

    fun setNumPlayers(count: Int) {
        _numPlayers.value = count
        if (count == 1) {
            _player2Name.value = computerNames.random()
            _isPlayer2Enabled.value = false
        } else {
            _player2Name.value = "Player 2"
            _isPlayer2Enabled.value = true
        }
    }

    fun setPlayer1Name(name: String) {
        _player1Name.value = if (name.isBlank()) "Player 1" else name
    }

    fun setPlayer2Name(name: String) {
        _player2Name.value = if (name.isBlank() && _numPlayers.value == 2) "Player 2"
        else if (name.isBlank() && _numPlayers.value == 1) computerNames.random()
        else name
    }
}
