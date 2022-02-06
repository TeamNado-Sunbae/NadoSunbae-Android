package com.nadosunbae_android.presentation.ui.review.viewmodel

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.model.request.like.RequestPostLike
import com.nadosunbae_android.model.response.review.ResponseReviewDetailData
import com.nadosunbae_android.model.response.sign.SelectableData
import com.nadosunbae_android.model.review.ReviewDetailData
import com.nadosunbae_android.usecase.review.DeleteReviewDataUseCase
import com.nadosunbae_android.usecase.review.GetReviewDetailDataUseCase
import com.nadosunbae_android.util.DropDownSelectableViewModel
import kotlinx.coroutines.launch

class ReviewDetailViewModel(
    private val getReviewDetailDataUseCase: GetReviewDetailDataUseCase,
    private val deleteReviewDataUseCase: DeleteReviewDataUseCase
) : ViewModel(), DropDownSelectableViewModel {

    private val _reviewDetailData = MutableLiveData<ReviewDetailData>()
    val reviewDetailData: LiveData<ReviewDetailData>
        get() = _reviewDetailData

    private val _backgroundRes = MutableLiveData<Drawable>()
    val backgroundRes: LiveData<Drawable>
        get() = _backgroundRes

    private val _signUserId = MutableLiveData<Int>()
    val signUserId: LiveData<Int>
        get() = _signUserId

    override var dropDownSelected = MutableLiveData<SelectableData>()

    // 후기 상세정보 불러오기
    fun getReviewDetail(postId: Int) {
        viewModelScope.launch {
            runCatching { getReviewDetailDataUseCase(postId) }
                .onSuccess {
                    _reviewDetailData.value = it
                    Log.d(TAG, "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "서버통신 실패")
                }
        }
    }

    /*

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

     */

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

    // 후기 삭제
    fun deleteReview(postId: Int) {
        viewModelScope.launch {
            runCatching { deleteReviewDataUseCase(postId) }
                .onSuccess {
                    Log.d(TAG, "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "서버통신 실패")
                }
        }
    }


    /*
    // 후기 삭제
    fun deleteReview(postId: Int) {
        reviewRepository.deleteReview(postId,
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

     */

    // 로그인 유저 정보 불러오기
    fun getSignUserId() {
        myPageRepository.getMyPageMyInfo(
            onResponse = {
                if (it.isSuccessful) {

                    // null check
                    if (it.body() != null)
                        _signUserId.value = it.body()!!.data.userId

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