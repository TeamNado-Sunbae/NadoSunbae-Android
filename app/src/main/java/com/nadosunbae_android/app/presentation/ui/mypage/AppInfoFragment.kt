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

    }

    private fun versionInfo() {
        myPageViewModel.getMyPageVersion()
        myPageViewModel.versionInfo.observe(viewLifecycleOwner) {
            Log.d("버전 정보", it.data.AOS)

            val info: PackageInfo = context?.packageManager!!.getPackageInfo(requireContext().packageName, 0)
            binding.textMypageAppInfoNowVersion.setText("v"+info.versionName)
            binding.textMypageAppInfoRecentVersion.setText(it.data.AOS)
        }
    }

    private fun backBtn() {
        binding.imgMypageAppInfoMovePage.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 1
        }

        binding.imgMypageAppInfoOpensource.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/nadosunbae/V-1-0-2442b1af796041d09bc6e8729c172438"))
            startActivity(intent)
        }
    }

}
