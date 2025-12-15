package com.swirl.pocketarcade.games.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentTicTacToeBinding
import com.swirl.pocketarcade.games.tictactoe.model.Player

class TicTacToeFragment : Fragment(R.layout.fragment_tic_tac_toe) {

    private var _binding: FragmentTicTacToeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TicTacToeViewModel by viewModels {
        val args = TicTacToeFragmentArgs.Companion.fromBundle(requireArguments())
        val player1: Player = args.player1
        val player2: Player = args.player2
        TicTacToeViewModelFactory(player1, player2)
    }

    private val buttons = arrayListOf<Button>()
    private val buttonIds = arrayOf(
        R.id.btn_0, R.id.btn_1, R.id.btn_2,
        R.id.btn_3, R.id.btn_4, R.id.btn_5,
        R.id.btn_6, R.id.btn_7, R.id.btn_8
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTicTacToeBinding.bind(view)

        val closeButton = binding.btnClose
        closeButton.setOnClickListener {
            findNavController().navigate(R.id.action_ticTacToeFragment_to_menuFragment)
        }

        // Init buttons
        for (i in buttonIds.indices) {
            val button = binding.root.findViewById<Button>(buttonIds[i])
            buttons.add(button)
            button.setOnClickListener { viewModel.makeMove(i) }
        }

        viewModel.board.observe(viewLifecycleOwner) { board ->
            for (i in board.indices) {
                buttons[i].text = board[i]
            }
        }

        viewModel.gameTurnState.observe(viewLifecycleOwner) { (player, winner, isFull) ->
            binding.turnTV.text = when {
                winner != null -> "${winner.name} won!"
                isFull == true -> "Game over - it's a draw!"
                else -> "Turn ${player?.name}"
            }
        }

        binding.btnReset.setOnClickListener { viewModel.resetGame() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}