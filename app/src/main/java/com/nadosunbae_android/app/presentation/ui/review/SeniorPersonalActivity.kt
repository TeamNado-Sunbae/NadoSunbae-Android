package com.nadosunbae_android.app.presentation.ui.review

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySeniorPersonalBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.*
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageFragment
import com.nadosunbae_android.app.util.addFragment
import com.nadosunbae_android.app.util.changeFragment
import com.nadosunbae_android.app.util.changeFragmentNoBackStack
import com.nadosunbae_android.app.util.popFragmentBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeniorPersonalActivity :
    BaseActivity<ActivitySeniorPersonalBinding>(R.layout.activity_senior_personal) {

    private val fragment = SeniorPersonalFragment().apply {
        isActivity = true
    }
    private val mainViewModel: MainViewModel by viewModels()

    private var seniorId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFragment()
        initSeniorId()
        observeFragmentChange()
    }

    private fun initFragment() {
        addFragment(R.id.fl_senior, fragment)
    }

    private fun initSeniorId() {
        seniorId = intent.getIntExtra("userId", 0)
        mainViewModel.seniorId.value = seniorId
    }

    private fun observeFragmentChange() {
        mainViewModel.classRoomFragmentNum.observe(this) {
            when (it) {
                2 -> changeFragment(R.id.fl_senior, AskEveryoneFragment(), "askEveryOne")

                1 -> changeFragmentNoBackStack(R.id.fl_senior, ClassRoomFragment())

                3 -> changeFragment(R.id.fl_senior, SeniorFragment(), "senior")

                4 -> changeFragment(R.id.fl_senior, SeniorPersonalFragment(), "seniorPersonal")

                5 -> changeFragment(R.id.fl_senior, ClassRoomReviewFragment(), "classRoomReview")

                6 -> changeFragment(R.id.fl_senior, MyPageFragment(), "myPage")
            }
        }

        mainViewModel.classRoomBackFragmentNum.observe(this) {
            when (it) {
                1 -> finish()
                2 -> popFragmentBackStack("senior")
            }
        }
    }

}