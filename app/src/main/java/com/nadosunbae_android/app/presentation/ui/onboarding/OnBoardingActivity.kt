package com.nadosunbae_android.app.presentation.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMainBinding
import com.nadosunbae_android.app.databinding.ActivityOnboardingBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageReplyActivity
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.app.presentation.ui.sign.SignUpAgreementActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

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