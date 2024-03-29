package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunityWriteUpdateBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityWriteUpdateViewModel
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.community.CommunityWriteUpdateData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class CommunityWriteUpdateActivity :
    BaseActivity<ActivityCommunityWriteUpdateBinding>(R.layout.activity_community_write_update) {
    private val communityWriteUpdateViewModel: CommunityWriteUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUpdateView()
        initCategory()
        clickCancelButton()
        setCompleteButton()
        clickCompleteButton()
        completeUpdate()
    }


    //기본 뷰 구성
    private fun initUpdateView() {
        communityWriteUpdateViewModel.setUpdateData(
            intent.getParcelableExtra("updateData") ?: CommunityWriteUpdateData.DEFAULT
        )
        binding.viewModel = communityWriteUpdateViewModel
    }

    //카테고리
    private fun initCategory(){
        communityWriteUpdateViewModel.initUpdateData.flowWithLifecycle(lifecycle)
            .onEach {
                when(it.category){
                    "자유" -> binding.layoutCommunityWriteUpdateCategory.radioBtnCategoryFreedom.isChecked = true
                    "정보" ->binding.layoutCommunityWriteUpdateCategory.radioBtnCategoryInfo.isChecked = true
                    else -> binding.layoutCommunityWriteUpdateCategory.radioBtnCategoryInfo.isChecked = true
                }
            }.launchIn(lifecycleScope)
    }
    //취소 버튼
    private fun clickCancelButton() {
        binding.imgCommunityWriteUpdateCancel.setOnClickListener {
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
    //완료 버튼
    private fun setCompleteButton(){
        communityWriteUpdateViewModel.checkComplete.observe(this){
            binding.btnCommunityWriteUpdateOk.isEnabled = it
        }
    }

    //완료 버튼 클릭
    private fun clickCompleteButton(){
        binding.btnCommunityWriteUpdateOk.setOnClickListener {
            CustomDialog(this).genericDialog(
                dialogText = CustomDialog.DialogData(
                    getString(R.string.question_write_complete),
                    getString(R.string.alert_write_review_complete),
                    getString(R.string.question_write_complete_no)
                ),
                complete = {
                    communityWriteUpdateViewModel.putPostUpdate()
                },
                cancel = {}
            )
        }
    }

    //완료시 닫기
    private fun completeUpdate(){
        communityWriteUpdateViewModel.updateSuccess.flowWithLifecycle(lifecycle)
            .onEach {
                if(it) finish()
            }.launchIn(lifecycleScope)
    }
}