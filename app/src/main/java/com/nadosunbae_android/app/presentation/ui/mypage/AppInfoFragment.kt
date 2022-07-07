package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityAppInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppInfoFragment : BaseFragment<ActivityAppInfoBinding>(R.layout.activity_app_info) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        versionInfo()
        backBtn()
        initMovePage()
        observeLoadingEnd()


    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }

    private fun versionInfo() {
        myPageViewModel.getMyPageVersion()
        myPageViewModel.versionInfo.observe(viewLifecycleOwner) {
            showLoading()
            val info: PackageInfo = context?.packageManager!!.getPackageInfo(requireContext().packageName, 0)
            binding.textMypageAppInfoNowVersion.setText(info.versionName)
            binding.textMypageAppInfoRecentVersion.setText(it.data.AOS)
        }
    }

    private fun backBtn() {
        binding.imgMypageAppInfoMovePage.setOnClickListener {
            mainViewModel.myPageFragmentNum.value = 2
        }
    }

    private fun initIntent(url: String) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun initMovePage() {

        mainViewModel.getAppLink()
        mainViewModel.appLink.observe(viewLifecycleOwner){
            val privacyPolicy = it.data.personalInformationPolicy
            val termsOfService = it.data.termsOfService
            val openSource = it.data.openSourceLicense

            //개인정보처리방침
            binding.textMypageAppInfoRulesOfUse.setOnClickListener {
                initIntent(privacyPolicy)
            }

            //서비스 이용약관
            binding.textMypageAppInfoTermsOfUse.setOnClickListener {
                initIntent(termsOfService)
            }

            //오픈 라이선스
            binding.textMypageAppInfoOpensource.setOnClickListener {
                initIntent(openSource)
            }
        }

    }

}
