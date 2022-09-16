package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunityWriteBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityWriteViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog
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
        checkCategory()
        activateCompleteButton()
        clickComplete()
        communityWriteViewModel.setCompleteButton()
        goDetail()
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
            true,
            noMajor
        )
        observeBottomSheet(
            communityWriteViewModel.majorList.value ?: emptyList(), majorBottomSheetDialog
        )
        //학과 무관 선택
        majorBottomSheetDialog.setSelectedData(0)
        binding.layoutCommunityWriteCategory.radioBtnCategoryFreedom.isChecked = true
    }

    //학과 변경 클릭
    private fun clickMajor() {
        val showDialog = {
            majorBottomSheetDialog.show(supportFragmentManager, majorBottomSheetDialog.tag)
        }
        binding.layoutCommunityWriteMajor.root.setOnClickListener {
            showDialog()
        }
    }

    //학과 변경 완료
    private fun completeMajor() {
        majorBottomSheetDialog.setCompleteListener {
            communityWriteViewModel.setFilter(majorBottomSheetDialog.getSelectedData())
        }
        communityWriteViewModel.filter.flowWithLifecycle(lifecycle)
            .onEach {
                binding.layoutCommunityWriteMajor.bottomSheetMajor = it.name
            }
            .launchIn(lifecycleScope)
    }

    //카테 고리
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
                finish()
            }
        }
    }
}
