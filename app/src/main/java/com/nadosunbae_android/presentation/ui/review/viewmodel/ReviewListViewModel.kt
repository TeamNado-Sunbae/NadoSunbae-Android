package com.nadosunbae_android.presentation.ui.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.model.request.review.RequestReviewListData
import com.nadosunbae_android.model.response.review.ResponseReviewListData
import com.nadosunbae_android.model.response.sign.SelectableData
import com.nadosunbae_android.model.review.MajorData
import com.nadosunbae_android.model.review.ReviewDetailData
import com.nadosunbae_android.model.review.ReviewFilterItem
import com.nadosunbae_android.model.review.ReviewPreviewData
import com.nadosunbae_android.model.ui.PreviewData
import com.nadosunbae_android.usecase.review.GetMajorInfoDataUseCase
import com.nadosunbae_android.usecase.review.GetReviewListDataUseCase
import com.nadosunbae_android.util.DropDownSelectableViewModel
import kotlinx.coroutines.launch

class ReviewListViewModel(
    val getReviewListDataUseCase: GetReviewListDataUseCase,
    val getMajorInfoDataUseCase: GetMajorInfoDataUseCase
) : ViewModel(), DropDownSelectableViewModel {

    private val _reviewListData = MutableLiveData<List<ReviewPreviewData>>()
    val reviewListData: LiveData<List<ReviewPreviewData>>
        get() = _reviewListData

    override var dropDownSelected = MutableLiveData<SelectableData>()

    private val _majorInfo = MutableLiveData<MajorData>()
    val majorInfo: LiveData<MajorData>
        get()  =_majorInfo


    // 리뷰 목록 list
    val previewList = MutableLiveData<List<PreviewData>>()


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
        }
    }

    companion object {
        const val TAG = "reviewListViewModel"
    }
}