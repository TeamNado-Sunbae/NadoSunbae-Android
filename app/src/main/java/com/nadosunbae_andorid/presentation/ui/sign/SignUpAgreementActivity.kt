package com.nadosunbae_andorid.presentation.ui.sign

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivitySignUpAgreementBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import com.nadosunbae_andorid.util.SignInCustomDialog

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
        binding.imageAgreementCheckInformation.setOnClickListener {
            binding.imageAgreementCheckInformation.isSelected = !binding.imageAgreementCheckInformation.isSelected

            if((binding.imageAgreementCheckInformation.isSelected == false) or (binding.imageAgreementCheckService.isSelected == false)) {
                binding.clAgreementMoveNext.isSelected = false
            } else if ((binding.imageAgreementCheckInformation.isSelected == true) and (binding.imageAgreementCheckService.isSelected == true)){
                binding.clAgreementMoveNext.isSelected = true
                nextPage()
            }
        }

        binding.imageAgreementCheckService.setOnClickListener {
            binding.imageAgreementCheckService.isSelected = !binding.imageAgreementCheckService.isSelected

            if((binding.imageAgreementCheckInformation.isSelected == false) or (binding.imageAgreementCheckService.isSelected == false)) {
                binding.clAgreementMoveNext.isSelected = false
            } else if ((binding.imageAgreementCheckInformation.isSelected == true) and (binding.imageAgreementCheckService.isSelected == true)){
                binding.clAgreementMoveNext.isSelected = true
                nextPage()
            }
        }

        //전체 동의하기 클릭리스너
        binding.imageAgreementCheckAll.setOnClickListener {
            binding.imageAgreementCheckAll.isSelected = !binding.imageAgreementCheckAll.isSelected

            if(binding.imageAgreementCheckAll.isSelected == true) {
                binding.imageAgreementCheckInformation.isSelected = true
                binding.imageAgreementCheckService.isSelected = true
                binding.clAgreementMoveNext.isSelected = true
                nextPage()
            }

            else if(binding.imageAgreementCheckAll.isSelected == false) {
                binding.imageAgreementCheckInformation.isSelected = false
                binding.imageAgreementCheckService.isSelected = false
                binding.clAgreementMoveNext.isSelected = false
            }

        }
    }


    private fun nextPage() {
        binding.clAgreementMoveNext.setOnClickListener {
            startActivity(Intent(this, SignUpMajorInfoActivity::class.java))
        }
    }

    //우선 아무 외부 링크로 연결
    private fun goPage() {
        binding.imageAgreementMoveInformation.setOnClickListener {
            var intentInformation = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com/"))
            startActivity(intentInformation)
        }

        binding.imageAgreementMoveService.setOnClickListener {
            var intentService = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/nadosunbae/TEAM-d1c88809162e4cf7b4f08ecaf4a85ab9"))
            startActivity(intentService)
        }
    }


}