package com.nadosunbae_android.app.presentation.ui.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.data.model.response.sign.SelectableData
import com.nadosunbae_android.data.model.review.BackgroundImageData
import com.nadosunbae_android.data.model.review.ReviewEditItem
import com.nadosunbae_android.data.model.review.ReviewWriteItem
import com.nadosunbae_android.domain.usecase.review.GetBackgroundImageListDataUseCase
import com.nadosunbae_android.domain.usecase.review.PostReviewDataUseCase
import com.nadosunbae_android.domain.usecase.review.PutReviewDataUseCase
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import kotlinx.coroutines.launch

class ReviewWriteViewModel(
    val getBackgroundImageListDataUseCase: GetBackgroundImageListDataUseCase,
    val postReviewDataUseCase: PostReviewDataUseCase,
    val putReviewDataUseCase: PutReviewDataUseCase
) : ViewModel(), DropDownSelectableViewModel {

    override var dropDownSelected = MutableLiveData<SelectableData>()

    private val _backgroundImageList = MutableLiveData<List<BackgroundImageData>>()
    val backgroundImageList: LiveData<List<BackgroundImageData>>
        get() = _backgroundImageList

    // 후기 배경 목록 불러오기
    fun getBackgroundImageList() {
        viewModelScope.launch {
            runCatching { getBackgroundImageListDataUseCase() }
                .onSuccess {
                    _backgroundImageList.value = it
                    Log.d(TAG, "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "서버통신 실패")
                }
        }
    }

    // 후기 작성
    fun postReview(reviewWriteItem: ReviewWriteItem) {
        viewModelScope.launch {
            runCatching { postReviewDataUseCase(reviewWriteItem) }
                .onSuccess {
                    Log.d(TAG, "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "서버통신 실패")
                }
        }
    }

    // 후기 수정
    fun putReview(postId: Int, reviewEditItem: ReviewEditItem) {
        viewModelScope.launch {
            runCatching { putReviewDataUseCase(postId, reviewEditItem) }
                .onSuccess {
                    Log.d(TAG, "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "서버통신 실패")
                }
        }
    }


    companion object {
        const val TAG = "ReviewWriteViewModel"
    }

}