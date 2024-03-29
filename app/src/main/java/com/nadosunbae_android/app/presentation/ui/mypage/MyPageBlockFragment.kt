package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentMyPageBlockBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageBlockAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.mypage.MyPageBlockData
import com.nadosunbae_android.domain.model.mypage.MyPageBlockUpdateItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageBlockFragment :
    BaseFragment<FragmentMyPageBlockBinding>(R.layout.fragment_my_page_block) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var myPageBlockAdapter: MyPageBlockAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backBtn()
        initBlockList()
        setClickListener()
        observeLoadingEnd()
    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }



    //뒤로가기 버튼 리스너
    private fun backBtn() {
        binding.imgMypageBlock.setOnClickListener {
            mainViewModel.myPageFragmentNum.value = 3
        }
    }

    override fun onResume() {
        super.onResume()

    }

    //1:1질문 엠티뷰
    private fun initBlockEmpty(size : Int){
        if(size == 0){
            binding.textBlockEmpty.visibility = View.VISIBLE
        }else{
            binding.textBlockEmpty.visibility = View.GONE
        }
    }

    //마이페이지 차단 리스트
    private fun initBlockList() {
        mainViewModel.signData.observe(viewLifecycleOwner) {
            myPageViewModel.getMyPageBlock()
        }
        myPageBlockAdapter = MyPageBlockAdapter(mainViewModel.userId.value ?: 0)
        binding.rcMyPageQuestion.adapter = myPageBlockAdapter
        myPageViewModel.blockList.observe(viewLifecycleOwner) {
            initBlockEmpty(it.data.size)
            myPageBlockAdapter.setBlockMain((it.data) as MutableList<MyPageBlockData.Data>)
        }
    }

    //차단 해제
    private fun initBlockUpdate(userId: Int) {
        myPageViewModel.postMyPageBlockUpdate(
            MyPageBlockUpdateItem(userId)
        )
    }

    private fun setClickListener() {
        myPageBlockAdapter.setItemClickListener(
            object : MyPageBlockAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val userNickName = myPageBlockAdapter.myPageBlockData[position].nickname
                    val userId = myPageBlockAdapter.myPageBlockData[position].userId


                    CustomDialog(requireContext()).genericDialog(
                        CustomDialog.DialogData(
                            userNickName + getString(R.string.mypage_block_alret),
                            getString(R.string.mypage_block_ok),
                            getString(R.string.mypage_block_cancel)
                        ),
                        complete = {
                            initBlockUpdate(userId)
                            myPageBlockAdapter.removeItem(position)
                        },
                        cancel = {

                        }
                    )


                }
            }
        )
    }



}
