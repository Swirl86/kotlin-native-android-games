package com.swirl.pocketarcade.games.hangman.setup

import androidx.lifecycle.ViewModel

class HangmanSetupViewModel : ViewModel() {

    var playerName: String = ""
        private set

    var maxIncorrectGuesses: Int = 8
        private set

    fun setPlayerName(input: String) {
        playerName = input.trim().ifEmpty {
            generateRandomPlayerName()
        }
    }

    fun setDifficulty(value: Int) {
        maxIncorrectGuesses = value
    }

    private fun generateRandomPlayerName(): String {
        val names = listOf(
            "PixelHero",
            "ArcadeGhost",
            "RetroKnight",
            "NeonPlayer",
            "BitCrusher",
            "CoinMaster",
            "HighScore",
            "Joystick",
            "8BitLegend"
        )
        return names.random()
    }
}
