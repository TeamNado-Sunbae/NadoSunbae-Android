package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentSignUpAgreement2Binding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.util.SignInCustomDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpAgreementFragment : BaseFragment<FragmentSignUpAgreement2Binding>(R.layout.fragment_sign_up_agreement2) {

    private val signUpBasicInfoViewModel : SignUpBasicInfoViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closePage()
        onCheckChanged()
        goPage()
        initCheckedBox()
    }

    //X버튼 클릭 리스너
    private fun closePage() {
        binding.imgAgreementDelete.setOnClickListener {
            val dialog = SignInCustomDialog(requireActivity())
            dialog.showDialog()

            dialog.setOnClickListener(object : SignInCustomDialog.ButtonClickListener{
                override fun onClicked(num: () -> Unit) {
                    startActivity(Intent(requireActivity(), SignInActivity::class.java))
                    activity?.finish()
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
            signUpBasicInfoViewModel.isAgreementChecked.value = true
            Timber.e("${signUpBasicInfoViewModel.isAgreementChecked.value}")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun initCheckedBox() {
        if(signUpBasicInfoViewModel.isAgreementChecked.value == true) {
            binding.imageAgreementCheckAll.isSelected = true
            binding.imageAgreementCheckInformation.isSelected = true
            binding.imageAgreementCheckService.isSelected = true
            binding.clAgreementMoveNext.isSelected = true
            pressNextBtnEvent()
        }
    }


    //페이지 이동 intent 함수
    private fun initIntent(url: String) {
        val intent = Intent(requireActivity(), WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }


    //외부 링크로 연결
    private fun goPage() {
        mainViewModel.getAppLink()
        mainViewModel.appLink.observe(viewLifecycleOwner) {
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