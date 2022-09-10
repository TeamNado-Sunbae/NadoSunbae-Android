package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunityWriteBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityWriteViewModel
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.data.datasource.database.entity.MajorList
import com.nadosunbae_android.domain.model.major.MajorListData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

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
    }


    //학과 변경 세팅
    private fun initBottomSheetDialog() {
        communityWriteViewModel.setMajorList(
            intent.getParcelableArrayListExtra<MajorListData>("majorList") as List<MajorListData>
        )
        majorBottomSheetDialog = CustomBottomSheetDialog(
            getString(R.string.community_write_bottom_sheet_title),
            true
        )
        observeBottomSheet(
            communityWriteViewModel.majorList.value ?: emptyList(), majorBottomSheetDialog
        )

        //학과 무관 선택
        majorBottomSheetDialog.setSelectedData(-2)
        binding.layoutCommunityWriteCategory.radioBtnCategoryFreedom.isChecked = true
    }

    //학과 변경 클릭
    private fun clickMajor(){
        val showDialog = {
            majorBottomSheetDialog.show(supportFragmentManager, majorBottomSheetDialog.tag)
        }
        binding.layoutCommunityWriteMajor.root.setOnClickListener { showDialog() }
    }

    //학과 변경 완료
    private fun completeMajor(){
        majorBottomSheetDialog.setCompleteListener {
            communityWriteViewModel.setFilter(majorBottomSheetDialog.getSelectedData())
        }
        communityWriteViewModel.filter.flowWithLifecycle(lifecycle)
            .onEach {
                binding.layoutCommunityWriteMajor.bottomSheetMajor = it.name
            }
            .launchIn(lifecycleScope)
    }

    // 취소 버튼
    private fun clickCancelButton(){
        binding.imgCommunityWriteCancel.setOnClickListener {
            finish()
        }
    }
}
