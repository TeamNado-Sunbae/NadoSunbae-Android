package com.nadosunbae_android.app.presentation.ui.community

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunityWriteBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityWriteViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.community.CommunityRadioButtonData
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.major.MajorListData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CommunityWriteActivity :
    BaseActivity<ActivityCommunityWriteBinding>(R.layout.activity_community_write) {
    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    private val communityWriteViewModel: CommunityWriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomSheetDialog()
        clickMajor()
        clickCancelButton()
        completeMajor()
        activateCompleteButton()
        clickComplete()
        communityWriteViewModel.setCompleteButton()
        goDetail()
        clickMajorFavorites()
        clickCategory()
    }


    //학과 변경 세팅
    private fun initBottomSheetDialog() {
        val noMajor = intent.getIntExtra("noMajor", 0)
        binding.communityWriteViewModel = communityWriteViewModel
        communityWriteViewModel.setMajorList(
            intent.getParcelableArrayListExtra<MajorListData>("majorList") as List<MajorListData>
        )
        majorBottomSheetDialog = CustomBottomSheetDialog(
            getString(R.string.community_write_bottom_sheet_title),
            checkCommunity = true,
            checkCommunityWrite = true
        )

        communityWriteViewModel.majorList.observe(this) {
            observeBottomSheet(
                it ?: emptyList(), majorBottomSheetDialog
            )
        }

        //학과 선택 학과 무관 default
        majorBottomSheetDialog.setSelectedData(
            communityWriteViewModel.majorList.value?.get(0)?.majorId ?: 0
        )
        //기본 카테고리
        binding.layoutCommunityWriteCategory.radioBtnCategoryFreedom.isChecked = true
    }

    //카테고리 클릭 이벤트
    private fun clickCategory() {
        var categoryCheck = false
        var radioButton = CommunityRadioButtonData.DEFAULT
        binding.layoutCommunityWriteCategory.communityRadioButtonData = radioButton
        with(binding.layoutCommunityWriteCategory) {
            radioGroupCategory.setOnCheckedChangeListener { radioGroup, id ->
                categoryCheck = when (id) {
                    radioBtnCategoryFreedom.id -> {
                        radioButton = CommunityRadioButtonData(true,false,false)
                        false
                    }
                    radioBtnCategoryQuestion.id -> {
                        radioButton = CommunityRadioButtonData(false,true,false)
                        true
                    }
                    else -> {
                        radioButton = CommunityRadioButtonData(false,false,true)
                        false
                    }
                }
                binding.layoutCommunityWriteCategory.communityRadioButtonData = radioButton
                binding.category = categoryCheck
            }
        }
    }


    //학과 변경 클릭
    private fun clickMajor() {
        val showDialog = {
            majorBottomSheetDialog.show(supportFragmentManager, majorBottomSheetDialog.tag)
            setFilterMajor()
        }
        binding.layoutCommunityWriteMajor.root.setOnClickListener {
            showDialog()
        }
    }

    //학과 필터 선택
    private fun setFilterMajor() {
        val selectedDataId = communityWriteViewModel.selectedMajor.value?.majorId
        var checkId = 0
        checkId = if (selectedDataId == -1) {
            communityWriteViewModel.majorList.value?.get(0)?.majorId ?: 0
        } else {
            communityWriteViewModel.selectedMajor.value?.majorId ?: 0
        }
        majorBottomSheetDialog.setSelectedData(checkId)
    }

    //학과 변경 완료
    private fun completeMajor() {
        majorBottomSheetDialog.setCompleteListener {
            val selectedData = majorBottomSheetDialog.getSelectedData()
            val majorData = MajorSelectData(selectedData.id, selectedData.name)
            communityWriteViewModel.setFilter(selectedData)
            communityWriteViewModel.setSelectedMajor(majorData)
        }
        communityWriteViewModel.filter.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.id == 0) {
                    it.id = communityWriteViewModel.majorList.value?.get(0)?.majorId ?: 0
                }
                binding.layoutCommunityWriteMajor.bottomSheetMajor = it.name
            }
            .launchIn(lifecycleScope)
    }

    //카테 고리 entity 변형
    private fun checkCategory(): String {
        with(binding.layoutCommunityWriteCategory) {
            return when (radioGroupCategory.checkedRadioButtonId) {
                radioBtnCategoryFreedom.id -> "general"
                radioBtnCategoryQuestion.id -> "questionToEveryone"
                else -> "information"
            }
        }
    }

    // 취소 버튼
    private fun clickCancelButton() {
        binding.imgCommunityWriteCancel.setOnClickListener {
            CustomDialog(this).genericDialog(
                dialogText = CustomDialog.DialogData(
                    getString(R.string.question_update_cancel),
                    getString(R.string.mypage_modify_alert_back_continue),
                    getString(R.string.signup_alert_out)
                ),
                complete = {},
                cancel = { finish() }
            )
        }
    }

    //완료 버튼 활성화
    private fun activateCompleteButton() {
        communityWriteViewModel.completeButton.flowWithLifecycle(lifecycle)
            .onEach {
                binding.btnCommunityWriteOk.isEnabled = it
            }.launchIn(lifecycleScope)
    }

    //즐겨찾기 클릭시
    private fun clickMajorFavorites() {
        majorBottomSheetDialog.setCompleteFavoritesListener {
            communityWriteViewModel.postCommunityFavorite(it)
        }
        communityWriteViewModel.communityFavorites.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.success) {
                    communityWriteViewModel.getMajorList(
                        MainGlobals.signInData?.universityId ?: 1, "all", null,
                        MainGlobals.signInData?.userId ?: 0
                    )
                }
            }
            .launchIn(lifecycleScope)
    }

    //완료 버튼
    private fun clickComplete() {
        binding.btnCommunityWriteOk.setOnClickListener {
            CustomDialog(this).genericDialog(
                dialogText = CustomDialog.DialogData(
                    getString(R.string.question_write_complete),
                    getString(R.string.alert_write_review_complete),
                    getString(R.string.question_write_complete_no)
                ),
                complete = {
                    communityWriteViewModel.postWrite(
                        type = { checkCategory() },
                        title = communityWriteViewModel.writeTitle.value,
                        content = communityWriteViewModel.writeContent.value
                    )
                },
                cancel = {}
            )
        }
    }

    //완료 후 게시글 상세로 이동
    private fun goDetail() {
        communityWriteViewModel.onLoadingEnd.observe(this) {
            if (it) {
                val intent = Intent(this, CommunityDetailActivity::class.java)
                intent.putExtra("postId", communityWriteViewModel.postWrite.value.postId.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}
