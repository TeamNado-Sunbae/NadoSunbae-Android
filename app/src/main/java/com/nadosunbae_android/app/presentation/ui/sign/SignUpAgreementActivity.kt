package com.nadosunbae_android.app.presentation.ui.sign


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySignUpAgreementBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.SignInCustomDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpAgreementActivity : BaseActivity<ActivitySignUpAgreementBinding>(R.layout.activity_sign_up_agreement) {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        closePage()
        onCheckChanged()
        goPage()
    }

    //X버튼 클릭 리스너
    private fun closePage() {
        binding.imgAgreementDelete.setOnClickListener {
            val dialog = SignInCustomDialog(this)
            dialog.showDialog()

            dialog.setOnClickListener(object : SignInCustomDialog.ButtonClickListener{
                override fun onClicked(num: () -> Unit) {
                    startActivity(Intent(this@SignUpAgreementActivity, SignInActivity::class.java))
                    finish()
                }
            })
        }
    }

    //체크박스 클릭 리스너
    private fun onCheckChanged() {
        binding.apply {
            imageAgreementCheckInformation.setOnClickListener {
                imageAgreementCheckInformation.isSelected = !imageAgreementCheckInformation.isSelected
                if((!imageAgreementCheckInformation.isSelected) or (!imageAgreementCheckService.isSelected)) {
                    isNotAllSelected()
                } else if ((imageAgreementCheckInformation.isSelected) and (imageAgreementCheckService.isSelected)){
                    isAllSelected()
                }
            }

            imageAgreementCheckService.setOnClickListener {
                imageAgreementCheckService.isSelected = !imageAgreementCheckService.isSelected
                if((!imageAgreementCheckInformation.isSelected) or (!imageAgreementCheckService.isSelected)) {
                    isNotAllSelected()
                } else if ((imageAgreementCheckInformation.isSelected) and (imageAgreementCheckService.isSelected)){
                    isAllSelected()
                }
            }

            //전체 동의하기 클릭리스너
            clAgreementAll.setOnClickListener {
                imageAgreementCheckAll.isSelected = !imageAgreementCheckAll.isSelected

                if(imageAgreementCheckAll.isSelected) {
                    imageAgreementCheckInformation.isSelected = true
                    imageAgreementCheckService.isSelected = true
                    clAgreementMoveNext.isSelected = true
                    pressNextBtnEvent()
                }

                else if(!imageAgreementCheckAll.isSelected) {
                    imageAgreementCheckInformation.isSelected = false
                    imageAgreementCheckService.isSelected = false
                    clAgreementMoveNext.isSelected = false
                }

            }
        }

    }

    //체크박스 모두 선택되지 않았을 때
    private fun isNotAllSelected() {
        binding.clAgreementMoveNext.isSelected = false
        binding.imageAgreementCheckAll.isSelected = false
    }

    //체크박스 모두 선택되었을 때
    private fun isAllSelected() {
        binding.clAgreementMoveNext.isSelected = true
        binding.imageAgreementCheckAll.isSelected = true
        pressNextBtnEvent()
    }


    //회원가입 중 다음 페이지로 이동
    private fun pressNextBtnEvent() {
        binding.clAgreementMoveNext.setOnClickListener {
            startActivity(Intent(this, SignUpMajorInfoActivity::class.java))
            finish()
        }
    }


    //페이지 이동 intent 함수
    private fun initIntent(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }


    //외부 링크로 연결
    private fun goPage() {
        mainViewModel.getAppLink()
        mainViewModel.appLink.observe(this) {
            val privacyPolicy = it.data.personalInformationPolicy
            val termsOfService = it.data.termsOfService

            //개인정보 수집 동의
            binding.imageAgreementMoveInformation.setOnClickListener {
                initIntent(privacyPolicy)
            }

            //서비스 이용 약관 동의
            binding.imageAgreementMoveService.setOnClickListener {
                initIntent(termsOfService)
            }
        }
    }

}