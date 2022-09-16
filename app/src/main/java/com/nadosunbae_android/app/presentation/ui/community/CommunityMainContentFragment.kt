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
import com.nadosunbae_android.app.presentation.ui.custom.CustomSwitchTab.Companion.getSwitchTabValue
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDecoration
import com.nadosunbae_android.app.util.dpToPxF
import com.nadosunbae_android.domain.model.major.MajorListData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        setRefreshData()
        setLoading()
    }

    //메인 게시글
    private fun initCommunityMainContent() {
        showLoading()
        communityViewModel.getCommunityMainData("1", "0", "community", "like")
        communityMainContentAdapter = CommunityMainContentAdapter()
        binding.rcCommunityMain.adapter = communityMainContentAdapter
        val decoration = CustomDecoration(1.dpToPxF, 16.dpToPxF, requireContext().getColor(R.color.gray_0))
        binding.rcCommunityMain.addItemDecoration(decoration)
        communityViewModel.communityMainFilterData.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
        ).onEach {
            binding.size = it.isEmpty()
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
            switchTab = getSwitchTabValue(0)
            itemClickListener = {
                switchTab = getSwitchTabValue(it)
                with(communityViewModel) {
                    setCommunityMainType(it)
                    val type = communityMainType.value
                    val majorName = communityMainMajorName.value
                    setCommunityMainFilter(type, majorName)
                }


            }
        }
    }

    //필터 바텀 시트
    private fun initBottomSheet() {
        binding.filterTitle = getString(R.string.no_major)
        majorBottomSheetDialog = CustomBottomSheetDialog(
            resources.getString(R.string.community_bottom_sheet_title),
            true,
            mainViewModel.majorList.value[0].majorId
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
                with(binding) {
                    val type = communityViewModel.communityMainType.value
                    val majorName =
                        if (selectedData.name == getString(R.string.no_major)) null else selectedData.name
                    communityViewModel.setCommunityMainMajorName(majorName)
                    communityViewModel.setCommunityMainFilter(type, majorName)
                    filterTitle = selectedData.name
                    imgCommunityFilter.isSelected = true
                }
            }
        }
    }

    //새로고침시 받아오는 새로운 데이터
    private fun setRefreshData() {
        binding.swipeCommunityMain.setOnRefreshListener {
            val type = communityViewModel.communityMainType.value
            val majorName = communityViewModel.communityMainMajorName.value
            communityViewModel.getCommunityMainData(
                "1", "0", "community", "like", "", type, majorName
            )
        }
    }

    //로딩시에
    private fun setLoading() {
        communityViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            if (it) {
                dismissLoading()
                binding.swipeCommunityMain.isRefreshing = false
            }
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
