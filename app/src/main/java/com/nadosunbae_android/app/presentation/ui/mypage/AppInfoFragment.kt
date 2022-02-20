package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.content.pm.PackageInfo
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
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewDetailActivity.Companion.TAG
import com.nadosunbae_android.domain.model.mypage.MyPageVersionData
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppInfoFragment : BaseFragment<ActivityAppInfoBinding>(R.layout.activity_app_info) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        versionInfo()

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

}
