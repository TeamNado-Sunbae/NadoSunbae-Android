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
import com.nadosunbae_android.app.databinding.*
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

class MyPageBlockFragment : BaseFragment<FragmentMyPageBlockBinding>(R.layout.fragment_my_page_block) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backBtn()
    }

    private fun backBtn() {
        binding.imgMypageBlock.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 1
        }
    }

}
