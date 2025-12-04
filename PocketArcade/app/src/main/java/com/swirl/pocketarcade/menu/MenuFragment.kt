package com.swirl.pocketarcade.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swirl.pocketarcade.R

class MenuFragment : Fragment(R.layout.fragment_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btn_tictactoe).setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_playerSetup)
        }
    }
}