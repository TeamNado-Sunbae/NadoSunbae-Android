package com.nadosunbae_android.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.FragmentMyPageSettingBinding
import com.nadosunbae_android.presentation.base.BaseFragment


class MyPageSettingFragment : BaseFragment<FragmentMyPageSettingBinding>(R.layout.fragment_my_page_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeActivity()
    }

    //각각의 activity로 이동
    private fun changeActivity() {

        //내 정보 수정 activity
        binding.textMypageSettingModifyInformation.setOnClickListener {
            val intentModifyInfo = Intent(getActivity(), ModifyMyInfoActivity::class.java)
            startActivity(intentModifyInfo)
        }

        //비밀번호 변경 activity
        binding.textMypageSettingChagnePw.setOnClickListener {
            val intentPw = Intent(getActivity(), ChangePasswordActivity::class.java)
            startActivity(intentPw)
        }

        //알림 설정 activity
        binding.textMypageSettingNotification.setOnClickListener {
            val intentNotification = Intent(getActivity(), NotificationSettingActivity::class.java)
            startActivity(intentNotification)
        }

        //서비스 문의 activity
        binding.textMypageSettingServiceInquery.setOnClickListener {
            val intentServiceInquery = Intent(getActivity(), ServiceInqueryActivity::class.java)
            startActivity(intentServiceInquery)
        }

        //로그아웃 activity
        binding.textMypageSettingLogout.setOnClickListener {
            val intentLogOut = Intent(getActivity(), LogOutActivity::class.java)
            startActivity(intentLogOut)
        }

        //탈퇴 activity
        binding.textMypageSettingQuit.setOnClickListener {
            val intentQuit = Intent(getActivity(), QuitActivity::class.java)
            startActivity(intentQuit)
        }

        //오픈소스 라이선스 activity
        binding.textMypageSettingOpensource.setOnClickListener {
            val intentOpenSource = Intent(getActivity(), OpenSourceActivity::class.java)
            startActivity(intentOpenSource)
        }
    }
}