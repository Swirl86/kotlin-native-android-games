package com.swirl.pocketarcade.setup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.swirl.pocketarcade.R

class PlayerSetupFragment : Fragment(R.layout.fragment_player_setup) {

    private val viewModel: PlayerSetupViewModel by viewModels()

    private lateinit var etPlayer1Name: EditText
    private lateinit var etPlayer2Name: EditText
    private lateinit var rbSinglePlayer: RadioButton
    private lateinit var rbTwoPlayers: RadioButton
    private lateinit var rgPlayerCount: RadioGroup
    private lateinit var btnStartGame: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etPlayer1Name = view.findViewById(R.id.etPlayer1Name)
        etPlayer2Name = view.findViewById(R.id.etPlayer2Name)
        rbSinglePlayer = view.findViewById(R.id.rbSinglePlayer)
        rbTwoPlayers = view.findViewById(R.id.rbTwoPlayers)
        rgPlayerCount = view.findViewById(R.id.rgPlayerCount)
        btnStartGame = view.findViewById(R.id.btnStartGame)

        viewModel.player1Name.observe(viewLifecycleOwner) { etPlayer1Name.setText(it) }
        viewModel.player2Name.observe(viewLifecycleOwner) { etPlayer2Name.setText(it) }
        viewModel.isPlayer2Enabled.observe(viewLifecycleOwner) { etPlayer2Name.isEnabled = it }

        viewModel.numPlayers.observe(viewLifecycleOwner) { count ->
            if (count == 1) rbSinglePlayer.isChecked = true else rbTwoPlayers.isChecked = true
        }

        rgPlayerCount.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbSinglePlayer) viewModel.setNumPlayers(1)
            else viewModel.setNumPlayers(2)
        }

        etPlayer1Name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) viewModel.setPlayer1Name(etPlayer1Name.text.toString())
        }

        etPlayer2Name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) viewModel.setPlayer2Name(etPlayer2Name.text.toString())
        }

        btnStartGame.setOnClickListener {
            val action = PlayerSetupFragmentDirections.actionPlayerSetupToTicTacToe(
                numPlayers = viewModel.numPlayers.value ?: 1,
                player1Name = viewModel.player1Name.value ?: "Player 1",
                player2Name = viewModel.player2Name.value ?: "Computer"
            )
            findNavController().navigate(action)
        }
    }
}
