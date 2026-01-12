package com.swirl.pocketarcade.games.memory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.ItemCardBinding
import com.swirl.pocketarcade.games.memory.model.MemoryCard

class MemoryAdapter(
    var cards: List<MemoryCard>,
    private val onClick: (MemoryCard) -> Unit
) : RecyclerView.Adapter<MemoryAdapter.CardViewHolder>() {

    private val flipBackQueue = mutableListOf<Int>() // card IDs to flip back
    private var isAnimating = false

    inner class CardViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Required for 3D flip effect
            binding.root.cameraDistance =
                8000 * binding.root.resources.displayMetrics.density
        }

        fun bind(card: MemoryCard) {
            binding.root.animate().cancel()
            binding.root.rotationY = 0f

            val imageRes = if (card.isFaceUp || card.isMatched) card.imageUrl else R.drawable.card_back
            Glide.with(binding.imageView.context).load(imageRes).into(binding.imageView)

            binding.root.setOnClickListener {
                if (!card.isMatched && !card.isFaceUp && !isAnimating) {
                    animateFlip(card, toFaceUp = true) {
                        onClick(card)
                    }
                }
            }

            // If this card is queued for flip-back, animate it
            if (flipBackQueue.contains(card.id) && !card.isFaceUp && !card.isMatched) {
                flipBackQueue.remove(card.id)
                animateFlip(card, toFaceUp = false)
            }
        }

        /**
         * Animate card flip to either face-up or face-down.
         * @param card The card to animate
         * @param toFaceUp true to flip to front, false to flip to back
         * @param onFlipped Optional callback when front flip completes (e.g., for clicks)
         */
        private fun animateFlip(card: MemoryCard, toFaceUp: Boolean, onFlipped: (() -> Unit)? = null) {
            binding.root.animate()
                .rotationY(90f)
                .setDuration(250)
                .withEndAction {
                    val imageRes = if (toFaceUp) card.imageUrl else R.drawable.card_back
                    Glide.with(binding.imageView.context).load(imageRes).into(binding.imageView)

                    // Invoke callback only when flipping to front
                    onFlipped?.invoke()

                    binding.root.rotationY = -90f
                    binding.root.animate()
                        .rotationY(0f)
                        .setDuration(250)
                        .start()
                }
                .start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder(
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = holder.bind(cards[position])

    override fun getItemCount() = cards.size

    fun submitList(newCards: List<MemoryCard>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = cards.size
            override fun getNewListSize() = newCards.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                cards[oldItemPosition].id == newCards[newItemPosition].id
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                cards[oldItemPosition] == newCards[newItemPosition]
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        cards = newCards
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Queue cards to flip back. Call this from ViewModel when mismatch occurs.
     */
    fun queueFlipBack(cardIds: List<Int>) {
        flipBackQueue.addAll(cardIds)

        // Find positions of cards to flip back and notify only those
        cardIds.forEach { id ->
            val position = cards.indexOfFirst { it.id == id }
            if (position != -1) {
                notifyItemChanged(position)
            }
        }
    }
}