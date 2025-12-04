package com.swirl.pocketarcade.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.utils.extensions.combineWith

class TicTacToeFragment : Fragment(R.layout.fragment_tic_tac_toe) {

    private val viewModel: TicTacToeViewModel by viewModels()
    private val buttons = arrayListOf<Button>()
    private lateinit var turnTextView: TextView
    private lateinit var resetButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        turnTextView = view.findViewById(R.id.turnTV)
        resetButton = view.findViewById(R.id.btn_reset)

        for (i in 0..8) {
            val resId = resources.getIdentifier("btn_$i", "id", requireContext().packageName)
            val button = view.findViewById<Button>(resId)
            buttons.add(button)
            button.setOnClickListener {
                viewModel.makeMove(i)
            }
        }

        viewModel.board.observe(viewLifecycleOwner) { board ->
            for (i in board.indices) {
                buttons[i].text = board[i]
            }
        }

        viewModel.currentPlayer.combineWith(viewModel.winner)
            .observe(viewLifecycleOwner) { (player, winner) ->
                turnTextView.text = winner?.let { "${it.name} won!" } ?: "Turn ${player?.name}"
            }

        resetButton.setOnClickListener {
            viewModel.resetGame()
        }
    }
}
