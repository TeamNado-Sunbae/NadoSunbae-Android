package com.nadosunbae_android.app.presentation.ui.review.viewmodel

import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.review.MajorInfoData
import com.nadosunbae_android.domain.model.review.ReviewFilterItem
import com.nadosunbae_android.domain.model.review.ReviewPreviewData
import com.nadosunbae_android.domain.usecase.review.GetMajorInfoDataUseCase
import com.nadosunbae_android.domain.usecase.review.GetReviewListDataUseCase
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import kotlinx.coroutines.launch

class ReviewListViewModel(
    val getReviewListDataUseCase: GetReviewListDataUseCase,
    val getMajorInfoDataUseCase: GetMajorInfoDataUseCase
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    private val _reviewListData = MutableLiveData<List<ReviewPreviewData>>()
    val reviewListData: LiveData<List<ReviewPreviewData>>
        get() = _reviewListData

    override var dropDownSelected = MutableLiveData<SelectableData>()
    override val onLoadingEnd = MutableLiveData<Boolean>(false)

    private val _majorInfo = MutableLiveData<MajorInfoData>()
    val majorInfo: LiveData<MajorInfoData>
        get()  =_majorInfo


    // 리뷰 목록 list
    //val previewList = MutableLiveData<List<ReviewPreviewData>>()


    // 후기 목록 불러오기
    fun getReviewList(reviewFilterItem: ReviewFilterItem, sort: String = "recent") {
        viewModelScope.launch {
            runCatching { getReviewListDataUseCase(reviewFilterItem, sort) }
                .onSuccess {
                    _reviewListData.value = it
                    Log.d(TAG, "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    // 학과정보 불러오기
    fun getMajorInfo(majorId: Int) {
        viewModelScope.launch {
            runCatching { getMajorInfoDataUseCase(majorId) }
                .onSuccess {
                    _majorInfo.value = it
                    Log.d(TAG, "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    companion object {
        const val TAG = "reviewListViewModel"
    }

}