package com.nadosunbae_android.app.presentation.ui.onboarding

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityOnboardingBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomAskEveryoneAdapter
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.app.presentation.ui.sign.SignUpAgreementActivity
import kotlinx.android.synthetic.main.activity_onboarding.*
import java.lang.Boolean.getBoolean

class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPageMove()

        val pagerAdater = OnBoardingPagerAdapter(this)
        binding.vpOnboarding.adapter = pagerAdater
        binding.dotsIndicator.setViewPager2(binding.vpOnboarding)

    }

    private inner class OnBoardingPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa){
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> OnBoardingFirstFragment()
                1 -> OnBoardingSecondFragment()
                2 -> OnBoardingThirdFragment()
                else -> OnBoardingForthFragment()
            }
        }
    }

    private fun initPageMove() {
        binding.textSignup.setOnClickListener {
            val intentSignUp = Intent(this, SignUpAgreementActivity::class.java)
            startActivity(intentSignUp)
            finish()
        }

        binding.textLogIn.setOnClickListener {
            val intentLogIn = Intent(this, SignInActivity::class.java)
            startActivity(intentLogIn)
            finish()
        }
    }





}