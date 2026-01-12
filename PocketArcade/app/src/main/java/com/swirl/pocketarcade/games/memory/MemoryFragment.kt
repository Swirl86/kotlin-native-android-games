package com.swirl.pocketarcade.games.memory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentMemoryBinding
import com.swirl.pocketarcade.games.memory.model.MemoryResult

class MemoryFragment : Fragment(R.layout.fragment_memory) {

    private val viewModel: MemoryViewModel by viewModels()
    private lateinit var binding: FragmentMemoryBinding
    private lateinit var adapter: MemoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMemoryBinding.bind(view)

        adapter = MemoryAdapter(emptyList()) { card ->
            viewModel.onCardClicked(card)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.tvGameOver.visibility = View.GONE
            adapter.submitList(state.cards)
        }

        viewModel.flipBackEvent.observe(viewLifecycleOwner) { cardIds ->
            adapter.queueFlipBack(cardIds)
        }

        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is MemoryResult.MatchFound -> {
                    val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.glow_pulse)

                    // Animate only matched cards
                    val matchedCardPositions = adapter.cards.mapIndexed { index, card ->
                        if (card.isMatched) index else null
                    }.filterNotNull()

                    matchedCardPositions.forEach { pos ->
                        binding.recyclerView.findViewHolderForAdapterPosition(pos)?.itemView?.startAnimation(anim)
                    }
                }
                is MemoryResult.GameOver -> showGameOver()
                is MemoryResult.NoMatch -> Unit
            }
        }

        binding.btnReset.setOnClickListener {
            viewModel.startGame()
        }

        viewModel.startGame()
    }

    private fun showGameOver() {
        binding.tvGameOver.apply {
            visibility = View.VISIBLE
            alpha = 0f
            scaleX = 0.7f
            scaleY = 0.7f

            // Fade + scale in
            animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(600)
                .setInterpolator(OvershootInterpolator())
                .start()
        }

        // Disable further interaction
        binding.recyclerView.isEnabled = false
    }
}
