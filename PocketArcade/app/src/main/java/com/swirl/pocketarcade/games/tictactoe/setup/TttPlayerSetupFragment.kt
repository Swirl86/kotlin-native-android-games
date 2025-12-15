package com.swirl.pocketarcade.games.tictactoe.setup

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentTttPlayerSetupBinding
import com.swirl.pocketarcade.games.tictactoe.model.PlayerType

class TttPlayerSetupFragment : Fragment(R.layout.fragment_ttt_player_setup) {

    private var _binding: FragmentTttPlayerSetupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TttPlayerSetupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTttPlayerSetupBinding.bind(view)

        val closeButton = binding.btnClose
        closeButton.setOnClickListener {
            findNavController().navigate(R.id.action_tttPlayerSetup_to_menuFragment)
        }

        // Observers
        viewModel.player1.observe(viewLifecycleOwner) { player ->
            if (binding.etPlayer1Name.text.toString() != player.name)
                binding.etPlayer1Name.setText(player.name)
        }
        viewModel.player2.observe(viewLifecycleOwner) { player ->
            if (binding.etPlayer2Name.text.toString() != player.name)
                binding.etPlayer2Name.setText(player.name)
            binding.etPlayer2Name.isEnabled = player.type == PlayerType.HUMAN
        }
        viewModel.numPlayers.observe(viewLifecycleOwner) { count ->
            binding.rbSinglePlayer.isChecked = count == 1
            binding.rbTwoPlayers.isChecked = count == 2
        }

        // RadioGroup listener
        binding.rgPlayerCount.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setNumPlayers(if (checkedId == R.id.rbSinglePlayer) 1 else 2)
        }

        // EditText listeners
        binding.etPlayer1Name.doAfterTextChanged {
            viewModel.setPlayerName(1, it.toString())
        }
        binding.etPlayer2Name.doAfterTextChanged {
            viewModel.setPlayerName(2, it.toString())
        }

        // Start game button
        binding.btnStartGame.setOnClickListener {
            val action = TttPlayerSetupFragmentDirections.Companion.actionTttPlayerSetupToTicTacToe(
                player1 = viewModel.player1.value!!,
                player2 = viewModel.player2.value!!
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}