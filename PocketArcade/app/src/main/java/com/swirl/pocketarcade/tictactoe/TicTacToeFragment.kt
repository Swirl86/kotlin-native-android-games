package com.swirl.pocketarcade.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.swirl.pocketarcade.R

class TicTacToeFragment : Fragment(R.layout.fragment_tic_tac_toe) {

    private val viewModel: TicTacToeViewModel by viewModels()
    private val buttons = arrayListOf<Button>()
    private lateinit var turnTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        turnTextView = view.findViewById(R.id.turnTV)

        for (i in 0..8) {
            val resId = resources.getIdentifier("btn_$i", "id", requireContext().packageName)
            val button = view.findViewById<Button>(resId)
            buttons.add(button)
            button.setOnClickListener {
                viewModel.makeMove(i)
            }
        }

        viewModel.board.observe(viewLifecycleOwner, Observer { board ->
            for (i in board.indices) {
                buttons[i].text = board[i]
            }
        })

        viewModel.winner.observe(viewLifecycleOwner, Observer { winner ->
            winner?.let {
                Toast.makeText(requireContext(), "${it.name} vann!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.currentPlayer.observe(viewLifecycleOwner, Observer { player ->
            turnTextView.text = "Turn ${player.name}"
        })
    }
}
