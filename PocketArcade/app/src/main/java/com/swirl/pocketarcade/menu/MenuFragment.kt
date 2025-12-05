package com.swirl.pocketarcade.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swirl.pocketarcade.R
import com.swirl.pocketarcade.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMenuBinding.bind(view)

        binding.btnTictactoe.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_playerSetup)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}