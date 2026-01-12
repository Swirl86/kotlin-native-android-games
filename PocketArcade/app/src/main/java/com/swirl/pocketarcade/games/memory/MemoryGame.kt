package com.swirl.pocketarcade.games.memory

import com.swirl.pocketarcade.games.memory.model.MemoryCard
import com.swirl.pocketarcade.games.memory.model.MemoryGameState

class MemoryGame {

    fun createGame(pairCount: Int): MemoryGameState {
        val ids = (1..1000).shuffled().take(pairCount)

        val images = ids.flatMap { id ->
            listOf(
                "https://picsum.photos/id/$id/200",
                "https://picsum.photos/id/$id/200"
            )
        }.shuffled()

        val cards = images.mapIndexed { index, url ->
            MemoryCard(id = index, imageUrl = url)
        }

        return MemoryGameState(cards = cards)
    }

    fun flipCard(state: MemoryGameState, cardId: Int): MemoryGameState {
        val cards = state.cards.map {
            if (it.id == cardId) it.copy(isFaceUp = true) else it
        }

        return state.copy(cards = cards)
    }

    fun resolveCards(
        state: MemoryGameState,
        first: MemoryCard,
        second: MemoryCard
    ): MemoryGameState {

        val isMatch = first.imageUrl == second.imageUrl

        val updatedCards = state.cards.map {
            when (it.id) {
                first.id, second.id ->
                    if (isMatch) it.copy(isMatched = true) else it.copy(isFaceUp = false)
                else -> it
            }
        }

        val matchesFound = updatedCards.count { it.isMatched } / 2
        val attempts = state.attempts + 1
        val isGameOver = updatedCards.all { it.isMatched }

        return state.copy(
            cards = updatedCards,
            attempts = attempts,
            matchesFound = matchesFound,
            isGameOver = isGameOver
        )
    }
}
