package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentMyPageSettingBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.domain.model.mypage.MyPageQuitItem
import kotlinx.android.synthetic.main.activity_quit_alert_custom_dialog.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyPageSettingFragment : BaseFragment<FragmentMyPageSettingBinding>(R.layout.fragment_my_page_setting) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoadingEnd()
        changeActivity()
        initLogOut()
        backBtn()
    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }

    //각각의 activity로 이동
    private fun changeActivity() {

        //프로필 수정 activity
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

        //앱 정보 fragment
        binding.textMypageAppinfo.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 2
        }

        //탈퇴 dialog
        binding.textMypageSettingQuit.setOnClickListener {
            val dialog = getActivity()?.let { it1 -> QuitAlertCustomDialog(it1) }
            dialog?.showDialog()
            dialog?.initBtnClickDialog(R.layout.activity_quit_alert_custom_dialog)

            dialog?.setOnClickListener(object : QuitAlertCustomDialog.ButtonClickListener{
                override fun onClicked(num: Int, toString: String) {
                    if(num == 2) {
                        if(toString != null) {
                            Log.d("입력된 PW", " : $toString")
                            myPageViewModel.deleteMyPageQuit(MyPageQuitItem(toString))
                        } else {
                            Log.d("check", "editText is null")
                        }

                    } else {

                    }

                }

            })
        }

        //서비스 문의
        binding.textMypageSettingService.setOnClickListener {
            mainViewModel.getAppLink()
            mainViewModel.appLink.observe(viewLifecycleOwner) {
                val intent = Intent(getActivity(), WebViewActivity::class.java)
                intent.putExtra("url", it.data.kakaoTalkChannel)
                startActivity(intent)
            }
        }

        //차단 정보 fragment
        binding.textMypageBlock.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 3
        }

    }


    private fun initLogOut() {
        binding.textMypageSettingLogout.setOnClickListener {
            myPageViewModel.postMyPageLogOut()

            val intent = Intent(getActivity(), SignInActivity::class.java)

            myPageViewModel.logOut.observe(viewLifecycleOwner) {
                if(it.success) {
                    showLoading()
                    startActivity(intent)
                    Log.d("로그아웃", "성공")

                } else {
                    Log.d("로그아웃", "실패")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initLogOut()
    }

    //뒤로가기 버튼
    private fun backBtn() {
        binding.imgMypageSettingMovePage.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 6
        }
    }


}