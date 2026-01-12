package com.swirl.pocketarcade.games.memory.model

data class MemoryCard(
    val id: Int,
    val imageUrl: String,
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false
)
