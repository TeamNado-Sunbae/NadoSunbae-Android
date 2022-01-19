package com.nadosunbae_android.presentation.ui.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.review.ResponseReviewDetailData
import com.nadosunbae_android.data.repository.review.ReviewRepositoryImpl

class ReviewDetailViewModel : ViewModel() {
    private val reviewRepository = ReviewRepositoryImpl()

    private val _reviewDetailData = MutableLiveData<ResponseReviewDetailData>()
    val reviewDetailData: LiveData<ResponseReviewDetailData>
        get() = _reviewDetailData

    private var _urlBackground = MutableLiveData<String>()
    val urlBackground: LiveData<String>
        get() = _urlBackground


    // 서버 통신
    fun getReviewDetail(postId: Int) {
        reviewRepository.getReviewDetail(postId,
            onResponse = {
                if (it.isSuccessful) {
                    _reviewDetailData.value = it.body()

                    Log.d(TAG, "서버통신 성공")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d(TAG, "서버통신 실패")
            }
        )
    }

 // test data 넣는 함수 (api 적용 전까지)
    fun setBackgroundUrl(url: String) {
        _urlBackground.value = url

    }

    companion object {
        const val TAG = "ReviewDetailViewModel"
    }

}