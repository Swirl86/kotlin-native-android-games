package com.swirl.pocketarcade.setup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentPlayerSetupBinding

class PlayerSetupFragment : Fragment(R.layout.fragment_player_setup) {

    private var _binding: FragmentPlayerSetupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerSetupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlayerSetupBinding.bind(view)

        // Observers
        viewModel.player1Name.observe(viewLifecycleOwner) { binding.etPlayer1Name.setText(it) }
        viewModel.player2Name.observe(viewLifecycleOwner) { binding.etPlayer2Name.setText(it) }
        viewModel.isPlayer2Enabled.observe(viewLifecycleOwner) { binding.etPlayer2Name.isEnabled = it }
        viewModel.numPlayers.observe(viewLifecycleOwner) { count ->
            binding.rbSinglePlayer.isChecked = count == 1
            binding.rbTwoPlayers.isChecked = count == 2
        }

        // RadioGroup listener
        binding.rgPlayerCount.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbSinglePlayer) viewModel.setNumPlayers(1)
            else viewModel.setNumPlayers(2)
        }

        // EditText focus listeners
        binding.etPlayer1Name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) viewModel.setPlayer1Name(binding.etPlayer1Name.text.toString())
        }
        binding.etPlayer2Name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) viewModel.setPlayer2Name(binding.etPlayer2Name.text.toString())
        }

        // Start game button
        binding.btnStartGame.setOnClickListener {
            val action = PlayerSetupFragmentDirections.actionPlayerSetupToTicTacToe(
                numPlayers = viewModel.numPlayers.value ?: 1,
                player1Name = viewModel.player1Name.value ?: "Player 1",
                player2Name = viewModel.player2Name.value ?: "Computer"
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}