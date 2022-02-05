package com.nadosunbae_android.presentation.ui.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.model.request.review.RequestPostReviewData
import com.nadosunbae_android.model.response.review.ResponseBackgroundImageListData
import com.nadosunbae_android.model.response.sign.SelectableData
import com.nadosunbae_android.util.DropDownSelectableViewModel

class ReviewWriteViewModel : ViewModel(), DropDownSelectableViewModel {
    private val reviewRepository = ReviewRepositoryImpl()

    override var dropDownSelected = MutableLiveData<SelectableData>()

    private val _backgroundImageList = MutableLiveData<ResponseBackgroundImageListData>()
    val backgroundImageList: LiveData<ResponseBackgroundImageListData>
        get() = _backgroundImageList

    fun getBackgroundImageList() {
        reviewRepository.getBackgroundImageList(
            onResponse = {
                if (it.isSuccessful) {
                    _backgroundImageList.value = it.body()

                    Log.d(TAG, "서버통신 성공")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d(TAG, "서버통신 실패")
            }
        )
    }

    fun postReview(requestBody: RequestPostReviewData) {
        reviewRepository.postReview(requestBody,
            onResponse = {
                 if (it.isSuccessful) {
                     Log.d(TAG, "Post Review의 Resopnse: ${it.body().toString()}")
                     Log.d(TAG, "서버통신 성공")
                 }
            },
            onFailure = {
                it.printStackTrace()
                Log.d(TAG, "서버통신 실패")
            }
        )
    }

    fun putReview(postId: Int, requestBody: RequestPutReview) {
        reviewRepository.putReview(postId, requestBody,
            onResponse = {
                 if (it.isSuccessful) {

                     Log.d(TAG, "서버통신 성공")
                 }

            },
            onFailure = {
                it.printStackTrace()
                Log.d(TAG, "서버통신 실패")
            }
        )
    }


    companion object {
        const val TAG = "ReviewWriteViewModel"
    }

}