package com.nadosunbae_android.app.presentation.ui.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.review.BackgroundImageData
import com.nadosunbae_android.domain.model.review.ReviewEditItem
import com.nadosunbae_android.domain.model.review.ReviewWriteItem
import com.nadosunbae_android.domain.usecase.review.GetBackgroundImageListDataUseCase
import com.nadosunbae_android.domain.usecase.review.PostReviewDataUseCase
import com.nadosunbae_android.domain.usecase.review.PutReviewDataUseCase
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import kotlinx.coroutines.launch

class ReviewWriteViewModel(
    val getBackgroundImageListDataUseCase: GetBackgroundImageListDataUseCase,
    val postReviewDataUseCase: PostReviewDataUseCase,
    val putReviewDataUseCase: PutReviewDataUseCase
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override var dropDownSelected = MutableLiveData<SelectableData>()

    override val onLoadingEnd = MutableLiveData<Boolean>(false)

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
                .also {
                    onLoadingEnd.value = true
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
                .also {
                    onLoadingEnd.value = true
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
                .also {
                    onLoadingEnd.value = true
                }
        }
    }


    companion object {
        const val TAG = "ReviewWriteViewModel"
    }

}