package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeQuestionBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeQuestionFragment : BaseFragment<FragmentHomeQuestionBinding>(R.layout.fragment_home_question) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        naviControl()

    }

    private fun naviControl() {
        binding.ivHomeQuestionBack.setOnClickListener {
            findNavController().navigate(R.id.action_homeQuestionFragment_to_homeFragment)
        }
    }
}