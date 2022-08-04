package com.nadosunbae_android.app.presentation.ui.community

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.protobuf.ListValueOrBuilder
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentCommunityMainContentBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityViewModel
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.domain.model.main.MajorSelectData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityMainContentFragment :
    BaseFragment<FragmentCommunityMainContentBinding>(R.layout.fragment_community_main_content) {
    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    private val mainViewModel : MainViewModel by activityViewModels()
    private val communityViewModel : CommunityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeTab()
        clickSwipeTab()
        initBottomSheet()
        clickFilter()
        goCommunityWrite()
    }


    //탭 부분
    private fun initSwipeTab() {
        binding.customSwitchTab.switchText = mutableListOf("전체", "자유", "질문", "정보")
    }

    //탭 클릭
    private fun clickSwipeTab() {

        with(binding.customSwitchTab) {
            switchTab = listOf(true, false, false, false)
            itemClickListener = {
                switchTab = when (it) {
                    0 -> listOf(true, false, false, false)

                    1 -> listOf(false, true, false, false)

                    2 -> listOf(false, false, true, false)

                    else -> listOf(false, false, false, true)
                }
            }
        }
    }

    //필터 바텀 시트
    private fun initBottomSheet(){
        majorBottomSheetDialog = CustomBottomSheetDialog(resources.getString(R.string.community_bottom_sheet_title),true)
        observeBottomSheet(mainViewModel, majorBottomSheetDialog)
    }

    //필터 선택
    private fun clickFilter(){
        val showDialog = {
            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)
            majorBottomSheetDialog.setSelectedData(-2)
        }
        binding.clCommunityMainFilter.setOnClickListener{showDialog()}
        //완료버튼
        majorBottomSheetDialog.setCompleteListener {
            val selectedData = majorBottomSheetDialog.getSelectedData()
            if (selectedData != null) {
                communityViewModel.filterMajor.value = selectedData.name
                binding.imgCommunityFilter.isSelected = true
            }
        }

        //필터 이름 변경
        communityViewModel.filterMajor.observe(viewLifecycleOwner){
            binding.filterTitle = it
        }
    }

    //커뮤니티 글 작성 이동
    private fun goCommunityWrite(){
        binding.btnCommunityWrite.setOnClickListener {
         val intent = Intent(requireActivity(), CommunityWriteActivity::class.java)
         startActivity(intent)
        }
    }



}