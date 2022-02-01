package com.nadosunbae_android.presentation.ui.review.viewmodel

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.model.request.like.RequestPostLike
import com.nadosunbae_android.model.response.review.ResponseReviewDetailData

class ReviewDetailViewModel : ViewModel() {
    private val reviewRepository = ReviewRepositoryImpl()
    private val likeRepository = LikeRepositoryImpl()

    private val _reviewDetailData = MutableLiveData<ResponseReviewDetailData>()
    val reviewDetailData: LiveData<ResponseReviewDetailData>
        get() = _reviewDetailData

    private val _backgroundRes = MutableLiveData<Drawable>()
    val backgroundRes: LiveData<Drawable>
        get() = _backgroundRes


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

    // 좋아요
    fun postLikeReview(postId: Int) {
        likeRepository.likeDataSource.postLike(
            RequestPostLike(postId, 1),
            onResponse = {
                 if (it.isSuccessful) {
                     //_reviewDetailData.value!!.data.like.isLiked = it.body()!!.data.isLiked
                     Log.d(TAG, "서버통신 성공")
                 }
            },
            onFailure = {
                it.printStackTrace()
                Log.d(TAG, "서버통신 실패")
            }
        )
    }

    fun setBackgroundRes(res: Drawable?) {
        if (res != null)
            _backgroundRes.value = res!!
    }


    companion object {
        const val TAG = "ReviewDetailViewModel"
    }

}