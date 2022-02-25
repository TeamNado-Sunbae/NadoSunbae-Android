package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentMyPageBlockBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageBlockAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPageBlockData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageBlockFragment : BaseFragment<FragmentMyPageBlockBinding>(R.layout.fragment_my_page_block) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val myPageViewModel: MyPageViewModel by viewModel()
    private lateinit var myPageBlockAdapter: MyPageBlockAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backBtn()
        initBlockList()
    }

    //뒤로가기 버튼 리스너
    private fun backBtn() {
        binding.imgMypageBlock.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 1
        }
    }

    //마이페이지 차단 리스트
    private fun initBlockList() {
        mainViewModel.signData.observe(viewLifecycleOwner) {
            myPageViewModel.getMyPageBlock()
        }
        myPageBlockAdapter = MyPageBlockAdapter()
        binding.rcMyPageQuestion.adapter = myPageBlockAdapter
        myPageViewModel.blockList.observe(viewLifecycleOwner) {
            myPageBlockAdapter.setBlockMain((it.data) as MutableList<MyPageBlockData.Data>)
        }

    }

}
