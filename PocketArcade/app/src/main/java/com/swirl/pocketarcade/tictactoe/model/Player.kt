package com.swirl.pocketarcade.tictactoe.model

enum class PlayerType { HUMAN, AI }
enum class Moves { X, O }

data class Player(
    val id: Int,
    val type: PlayerType,        // HUMAN or AI
    val mark: Moves,
    val defaultName: String,     // "Player 1" or generated AI-name
    var name: String = defaultName
)
