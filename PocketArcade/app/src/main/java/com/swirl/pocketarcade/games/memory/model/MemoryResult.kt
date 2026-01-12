package com.swirl.pocketarcade.games.memory.model

sealed class MemoryResult {
    object MatchFound : MemoryResult()
    object NoMatch : MemoryResult()
    object GameOver : MemoryResult()
}
