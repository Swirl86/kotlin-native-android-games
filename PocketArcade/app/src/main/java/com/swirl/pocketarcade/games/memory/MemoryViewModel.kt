package com.swirl.pocketarcade.games.memory

import androidx.lifecycle.*
import com.swirl.pocketarcade.games.memory.model.MemoryCard
import com.swirl.pocketarcade.games.memory.model.MemoryGameState
import com.swirl.pocketarcade.games.memory.model.MemoryResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MemoryViewModel : ViewModel() {

    private val game = MemoryGame()

    private val _state = MutableLiveData<MemoryGameState>()
    val state: LiveData<MemoryGameState> = _state

    private val _result = MutableLiveData<MemoryResult>()
    val result: LiveData<MemoryResult> = _result

    private val _flipBackEvent = MutableLiveData<List<Int>>()
    val flipBackEvent: LiveData<List<Int>> = _flipBackEvent

    fun startGame(pairCount: Int = 6) {
        _state.value = game.createGame(pairCount)
    }

    fun onCardClicked(card: MemoryCard) {
        val current = _state.value ?: return

        // Ignore clicks if busy, card already face-up, or already matched
        if (current.isBusy || card.isFaceUp || card.isMatched) return

        // Flip the clicked card
        _state.value = game.flipCard(current, card.id)

        val flipped = _state.value!!.cards.filter { it.isFaceUp && !it.isMatched }

        if (flipped.size == 2) {
            viewModelScope.launch {
                // Mark state as busy so no other clicks are processed
                _state.value = _state.value!!.copy(isBusy = true)

                // Wait 800ms so user can see the two flipped cards
                delay(800)

                // Resolve cards (mark as matched or flip back)
                val newState = game.resolveCards(_state.value!!, flipped[0], flipped[1])

                // If mismatch, allow a small delay so flip-back animation is visible
                if (flipped[0].imageUrl != flipped[1].imageUrl) {
                    _flipBackEvent.value = listOf(flipped[0].id, flipped[1].id)
                }

                // Update state
                _state.value = newState.copy(isBusy = false)

                // Trigger result for UI feedback
                val previousMatches = current.matchesFound
                _result.value = when {
                    newState.isGameOver -> MemoryResult.GameOver
                    newState.matchesFound > previousMatches -> MemoryResult.MatchFound
                    else -> MemoryResult.NoMatch
                }
            }
        }
    }
}
