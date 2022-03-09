package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentMyPageSettingBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.mypage.MyPageQuitItem
import kotlinx.android.synthetic.main.activity_quit_alert_custom_dialog.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MyPageSettingFragment :
    BaseFragment<FragmentMyPageSettingBinding>(R.layout.fragment_my_page_setting) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quitBranchProcessing()
        observeLoadingEnd()
        changeActivity()
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
            intentModifyInfo.putExtra("id", mainViewModel.userId.value)
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
            val dialog = QuitAlertCustomDialog(requireActivity())
            dialog.showDialog()
            dialog.initBtnClickDialog(R.layout.activity_quit_alert_custom_dialog)
            dialog.editTextWatcher()
            dialog.setOnClickListener(object : QuitAlertCustomDialog.ButtonClickListener {
                override fun onClicked(num: Int, toString: String) {
                    if (num == 2) {
                        if (toString != null) {
                            Timber.d("입력된 PW: $toString")
                            myPageViewModel.deleteMyPageQuit(MyPageQuitItem(toString))

                        } else {
                            Timber.d("check: editText is null")
                        }

                    }
                }
            })
        }

        //서비스 문의
        binding.textMypageSettingService.setOnClickListener {
            mainViewModel.getAppLink()
            mainViewModel.appLink.observe(viewLifecycleOwner) {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.data.kakaoTalkChannel))
                startActivity(intent)
            }
        }

        //차단 정보 fragment
        binding.textMypageBlock.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 3
        }


        //로그아웃
        binding.textMypageSettingLogout.setOnClickListener {
            logoutAlert()
        }

    }


    //회원 탈퇴 토스트메시지 + 분기처리
    private fun quitBranchProcessing() {
        myPageViewModel.reportStatusInfo.observe(viewLifecycleOwner) {
            if (it == 400) {
                Timber.d("회원탈퇴 서버통신 체크: 실패")
                Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else if (it == 200) {
                Timber.d("회원탈퇴 서버통신 체크: 성공")
                val intent = Intent(requireContext(), SignInActivity::class.java)
                showLoading()
                startActivity(intent)
                activity?.finish()
                Toast.makeText(context, "탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //로그아웃 서버통신
    private fun initLogOut() {
        myPageViewModel.postMyPageLogOut()
        val intent = Intent(getActivity(), SignInActivity::class.java)
        myPageViewModel.logOut.observe(viewLifecycleOwner) {
            if (it.success) {
                showLoading()
                startActivity(intent)
                Timber.d("로그아웃: 성공")

            } else {
                Timber.d("로그아웃: 실패")
            }
        }
    }


    //로그아웃 알럿
    private fun logoutAlert(): MutableLiveData<Boolean> {
        val confirm = MutableLiveData<Boolean>()
        CustomDialog(requireContext()).genericDialog(
            CustomDialog.DialogData(
                getString(R.string.logout_message),
                getString(R.string.alert_modify_review_complete),
                getString(R.string.alert_modify_review_cancel),
            ),
            complete = {
                showLoading()
                initLogOut()
                activity?.finish()
            },
            cancel = {

            }
        )
        return confirm
    }

    //뒤로가기 버튼
    private fun backBtn() {
        binding.imgMypageSettingMovePage.setOnClickListener {
            mainViewModel.myPageFragmentNum.value = 1
        }
    }


}