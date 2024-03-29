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
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityMainContentAdapter
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityViewModel
import com.nadosunbae_android.app.presentation.ui.custom.CustomSwitchTab.Companion.getSwitchTabValue
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.*
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.domain.model.major.MajorListData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

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
        clickMajorFavorites()
    }

    override fun onResume() {
        super.onResume()
        val type = communityViewModel.communityMainType.value
        val majorName = communityViewModel.communityMainMajorName.value
        showLoading()
        communityViewModel.getCommunityMainData(
            MainGlobals.signInData?.universityId ?: 1,
            "0",
            "community",
            "recent",
            "",
            type,
            majorName
        )
    }

    //메인 게시글
    private fun initCommunityMainContent() {
        communityMainContentAdapter = CommunityMainContentAdapter()
        binding.rcCommunityMain.adapter = communityMainContentAdapter
        val decoration =
            CustomDecoration(1.dpToPxF, 16.dpToPxF, requireContext().getColor(R.color.gray_0))
        binding.rcCommunityMain.addItemDecoration(decoration)
        communityViewModel.communityMainFilterData.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
        ).onEach {
            binding.size = it.isEmpty()
            communityMainContentAdapter.submitList(it) {
                binding.rcCommunityMain.scrollToPosition(0)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    //탭 부분
    private fun initSwipeTab() {
        binding.customSwitchTab.switchText = mutableListOf("전체", "자유", "질문", "정보")
    }

    //탭 클릭
    private fun clickSwipeTab() {
        val checkTypeFirebase = mapOf( 1 to "c_free_read",2 to "c_question_read",3 to "c_info_read")
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
                FirebaseAnalyticsUtil.firebaseLog("community_read","type", checkTypeFirebase[it].toString())
            }
        }
    }

    //필터 바텀 시트
    private fun initBottomSheet() {
        majorBottomSheetDialog = CustomBottomSheetDialog(
            resources.getString(R.string.community_bottom_sheet_title),
            true,
        )
        observeBottomSheet(mainViewModel, majorBottomSheetDialog)
    }


    //필터 선택
    private fun clickFilter() {
        val showDialog = {
            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)
            majorBottomSheetDialog.setSelectedData(
                communityViewModel.selectedMajor.value?.majorId ?: 0
            )
        }

        binding.clCommunityMainFilter.setOnClickListener { showDialog() }
        //완료버튼
        majorBottomSheetDialog.setCompleteListener {
            val selectedData = majorBottomSheetDialog.getSelectedData()
            val majorData = MajorSelectData(selectedData.id, selectedData.name)
            communityViewModel.setSelectedMajor(majorData)
            //학과 필터에 들어가는 부분
            with(binding) {
                val type = communityViewModel.communityMainType.value
                val majorName = selectedData.name
                communityViewModel.setCommunityMainMajorName(majorName)
                communityViewModel.setCommunityMainFilter(type, majorName)
                val filterSelect = selectedData.id != -1
                imgCommunityFilter.isSelected = filterSelect
            }
        }
    }

    //새로고침시 받아오는 새로운 데이터
    private fun setRefreshData() {
        binding.swipeCommunityMain.setOnRefreshListener {
            val type = communityViewModel.communityMainType.value
            val majorName = communityViewModel.communityMainMajorName.value
            communityViewModel.getCommunityMainData(
                MainGlobals.signInData?.universityId ?: 1,
                "0",
                "community",
                "recent",
                "",
                type,
                majorName
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

    //즐겨찾기 클릭시
    private fun clickMajorFavorites() {
        majorBottomSheetDialog.setCompleteFavoritesListener {
            Timber.d("이거 호출 $it")
            communityViewModel.postCommunityFavorite(it)
        }
        communityViewModel.communityFavorites.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it.success) {
                    mainViewModel.getMajorList(
                        MainGlobals.signInData?.universityId ?: 1, "all", null,
                        MainGlobals.signInData?.userId ?: 0
                    )
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    //커뮤니티 글 작성 이동
    private fun goCommunityWrite() {
        binding.btnCommunityWrite.setOnClickListener {
            CustomDialog(requireActivity()).restrictDialog(
                requireActivity(),
                ReviewGlobals.isReviewed,
                MainGlobals.signInData?.isUserReported ?: false,
                MainGlobals.signInData?.isReviewInappropriate ?: false,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val majorList = mainViewModel.majorList.value
                    val intent = Intent(requireActivity(), CommunityWriteActivity::class.java)
                    intent.apply {
                        putExtra("majorList", majorList as ArrayList<MajorListData>)
                        putExtra("noMajor", mainViewModel.majorList.value[0].majorId)
                    }
                    startActivity(intent)
                })
        }
    }
}