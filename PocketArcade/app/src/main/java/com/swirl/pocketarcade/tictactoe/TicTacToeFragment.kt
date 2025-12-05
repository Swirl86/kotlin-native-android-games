package com.swirl.pocketarcade.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentTicTacToeBinding
import com.swirl.pocketarcade.tictactoe.model.Moves
import com.swirl.pocketarcade.tictactoe.model.Player
import com.swirl.pocketarcade.tictactoe.model.PlayerType
import com.swirl.pocketarcade.utils.extensions.combineWith

class TicTacToeFragment : Fragment(R.layout.fragment_tic_tac_toe) {

    private var _binding: FragmentTicTacToeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TicTacToeViewModel by viewModels {
        val args = TicTacToeFragmentArgs.fromBundle(requireArguments())
        val player1 = Player(
            id = 1,
            type = PlayerType.valueOf(args.player1Type),
            mark = Moves.valueOf(args.player1Mark),
            defaultName = args.player1Name,
            name = args.player1Name
        )
        val player2 = Player(
            id = 2,
            type = PlayerType.valueOf(args.player2Type),
            mark = Moves.valueOf(args.player2Mark),
            defaultName = args.player2Name,
            name = args.player2Name
        )
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

        viewModel.currentPlayer.combineWith(viewModel.winner)
            .observe(viewLifecycleOwner) { (player, winner) ->
                binding.turnTV.text = winner?.let { "${it.name} won!" } ?: "Turn ${player?.name}"
            }

        binding.btnReset.setOnClickListener { viewModel.resetGame() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}