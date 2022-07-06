package com.nadosunbae_android.app.presentation.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentNotificationBinding
import com.nadosunbae_android.app.databinding.FragmentOnBoardingForthBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.notification.adapter.NotificationAdapter
import com.nadosunbae_android.app.presentation.ui.notification.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class OnBoardingForthFragment : BaseFragment<FragmentOnBoardingForthBinding>(R.layout.fragment_on_boarding_forth) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


}