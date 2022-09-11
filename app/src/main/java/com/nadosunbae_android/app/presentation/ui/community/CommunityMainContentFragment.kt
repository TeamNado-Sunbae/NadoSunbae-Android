package com.nadosunbae_android.app.presentation.ui.community

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentCommunityMainContentBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityMainContentAdapter
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityViewModel
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.domain.model.major.MajorListData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.io.Serializable

@AndroidEntryPoint
class CommunityMainContentFragment :
    BaseFragment<FragmentCommunityMainContentBinding>(R.layout.fragment_community_main_content) {
    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    private val mainViewModel: MainViewModel by activityViewModels()
    private val communityViewModel: CommunityViewModel by viewModels()
    private lateinit var communityMainContentAdapter: CommunityMainContentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCommunityMainContent()
        initSwipeTab()
        clickSwipeTab()
        initBottomSheet()
        clickFilter()
        goCommunityWrite()
    }

    //메인 게시글
    private fun initCommunityMainContent() {
        communityViewModel.getCommunityMainData("1", "5", "community", "like")
        communityMainContentAdapter = CommunityMainContentAdapter()
        binding.rcCommunityMain.adapter = communityMainContentAdapter
        communityViewModel.communityMainData.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
        ).onEach {
            communityMainContentAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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
    private fun initBottomSheet() {
        majorBottomSheetDialog = CustomBottomSheetDialog(
            resources.getString(R.string.community_bottom_sheet_title),
            true
        )
        observeBottomSheet(mainViewModel, majorBottomSheetDialog)
    }

    //필터 선택
    private fun clickFilter() {
        val showDialog = {
            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)
        }
        binding.clCommunityMainFilter.setOnClickListener { showDialog() }
        //완료버튼
        majorBottomSheetDialog.setCompleteListener {
            val selectedData = majorBottomSheetDialog.getSelectedData()
            //학과 필터에 들어가는 부분
            if (selectedData != null) {
                communityViewModel.filterMajor.value = selectedData.name
                binding.imgCommunityFilter.isSelected = true
            }
        }

        //필터 이름 변경
        communityViewModel.filterMajor.observe(viewLifecycleOwner) {
            binding.filterTitle = it
        }
    }

    //커뮤니티 글 작성 이동
    private fun goCommunityWrite() {
        binding.btnCommunityWrite.setOnClickListener {
            val majorList = mainViewModel.majorList.value
            val intent = Intent(requireActivity(), CommunityWriteActivity::class.java)
            intent.putExtra("majorList", majorList as ArrayList<MajorListData>)
            startActivity(intent)
        }
    }


}