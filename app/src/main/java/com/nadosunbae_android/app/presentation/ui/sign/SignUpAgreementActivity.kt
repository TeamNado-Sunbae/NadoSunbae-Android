package com.nadosunbae_android.app.presentation.ui.sign


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySignUpAgreementBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.util.SignInCustomDialog

class SignUpAgreementActivity : BaseActivity<ActivitySignUpAgreementBinding>(R.layout.activity_sign_up_agreement) {
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
                if((imageAgreementCheckInformation.isSelected == false) or (imageAgreementCheckService.isSelected == false)) {
                    isNotAllSelected()
                } else if ((imageAgreementCheckInformation.isSelected == true) and (imageAgreementCheckService.isSelected == true)){
                    isAllSelected()
                }
            }

            imageAgreementCheckService.setOnClickListener {
                imageAgreementCheckService.isSelected = !imageAgreementCheckService.isSelected
                if((imageAgreementCheckInformation.isSelected == false) or (imageAgreementCheckService.isSelected == false)) {
                    isNotAllSelected()
                } else if ((imageAgreementCheckInformation.isSelected == true) and (imageAgreementCheckService.isSelected == true)){
                    isAllSelected()
                }
            }

            //전체 동의하기 클릭리스너
            clAgreementAll.setOnClickListener {
                imageAgreementCheckAll.isSelected = !imageAgreementCheckAll.isSelected

                if(imageAgreementCheckAll.isSelected == true) {
                    imageAgreementCheckInformation.isSelected = true
                    imageAgreementCheckService.isSelected = true
                    clAgreementMoveNext.isSelected = true
                    pressNextBtnEvent()
                }

                else if(imageAgreementCheckAll.isSelected == false) {
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

    //외부 링크로 연결
    private fun goPage() {
        binding.imageAgreementMoveInformation.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", "https://www.notion.so/nadosunbae/V-1-0-2022-3-1-e4637880bb1d4a6e8938f4f0c306b2d5")
            startActivity(intent)
        }

        binding.imageAgreementMoveService.setOnClickListener {
            var intentService = Intent(Intent.ACTION_VIEW, Uri.parse("https://nadosunbae.notion.site/V-1-0-2022-3-1-d1d15e411b0b417198b2405468894dea"))
            startActivity(intentService)
        }
    }

}