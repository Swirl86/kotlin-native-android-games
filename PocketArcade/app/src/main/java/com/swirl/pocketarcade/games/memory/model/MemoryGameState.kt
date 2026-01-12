package com.swirl.pocketarcade.games.memory.model

data class MemoryGameState(
    val cards: List<MemoryCard>,
    val attempts: Int = 0,
    val matchesFound: Int = 0,
    val isGameOver: Boolean = false,
    val isBusy: Boolean = false
)