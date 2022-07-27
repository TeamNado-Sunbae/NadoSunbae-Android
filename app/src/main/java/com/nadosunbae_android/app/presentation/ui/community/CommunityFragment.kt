package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentCommunityBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}