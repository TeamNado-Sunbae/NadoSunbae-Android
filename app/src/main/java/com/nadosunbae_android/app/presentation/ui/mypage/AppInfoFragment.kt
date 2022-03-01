package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nadosunbae_android.app.BuildConfig
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityAppInfoBinding
import com.nadosunbae_android.app.databinding.ActivityMyPagePostBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewDetailActivity.Companion.TAG
import com.nadosunbae_android.domain.model.mypage.MyPageVersionData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppInfoFragment : BaseFragment<ActivityAppInfoBinding>(R.layout.activity_app_info) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val myPageViewModel: MyPageViewModel by viewModel()


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
            Log.d("버전 정보", it.data.AOS)

            showLoading()
            val info: PackageInfo = context?.packageManager!!.getPackageInfo(requireContext().packageName, 0)
            binding.textMypageAppInfoNowVersion.setText(info.versionName)
            binding.textMypageAppInfoRecentVersion.setText(it.data.AOS)
        }
    }

    private fun backBtn() {
        binding.imgMypageAppInfoMovePage.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 1
        }
    }

    private fun initIntent(url: String) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun initMovePage() {

        //개인정보처리방침
        binding.imgMypageAppInfoRulesOfUseArrow.setOnClickListener {
            initIntent("https://www.notion.so/nadosunbae/V-1-0-2022-3-1-e4637880bb1d4a6e8938f4f0c306b2d5")
        }

        //서비스 이용약관
        binding.imgMypageAppInfoTermsOfUse.setOnClickListener {
            initIntent("https://nadosunbae.notion.site/V-1-0-2022-3-1-d1d15e411b0b417198b2405468894dea")
        }


        //오픈 라이선스
        binding.imgMypageAppInfoOpensource.setOnClickListener {
            initIntent("https://www.notion.so/nadosunbae/V-1-0-2442b1af796041d09bc6e8729c172438")
        }
    }

}
