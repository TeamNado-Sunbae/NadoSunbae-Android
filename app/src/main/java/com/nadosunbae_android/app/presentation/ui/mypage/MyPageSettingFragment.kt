package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentMyPageSettingBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MyPageSettingFragment : BaseFragment<FragmentMyPageSettingBinding>(R.layout.fragment_my_page_setting) {

    private val mainViewModel: MainViewModel by sharedViewModel()

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

        //앱 정보 activity
        binding.textMypageSettingServiceInquery.setOnClickListener {
//            val intentServiceInquery = Intent(getActivity(), AppInfoActivity::class.java)
//            startActivity(intentServiceInquery)
            mainViewModel.mypageFragmentNum.value = 2
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

//        //오픈소스 라이선스 activity
//        binding.textMypageSettingOpensource.setOnClickListener {
//            val intentOpenSource = Intent(getActivity(), AppInfoActivity::class.java)
//            startActivity(intentOpenSource)
//        }
    }

}