package com.nadosunbae_android.app.presentation.ui.review.viewmodel

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.data.model.response.sign.SelectableData
import com.nadosunbae_android.domain.model.review.ReviewDetailData
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import com.nadosunbae_android.domain.usecase.review.DeleteReviewDataUseCase
import com.nadosunbae_android.domain.usecase.review.GetReviewDetailDataUseCase
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import kotlinx.coroutines.launch

class ReviewDetailViewModel(
    private val getReviewDetailDataUseCase: GetReviewDetailDataUseCase,
    private val deleteReviewDataUseCase: DeleteReviewDataUseCase,
    private val postLikeDataUseCase: PostLikeDataUseCase
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

    // 좋아요
    fun postLikeReview(postId: Int) {
        val likeItem = LikeItem(postId, POST_TYPE_REVIEW)

        viewModelScope.launch {
            runCatching { postLikeDataUseCase(likeItem) }
                .onSuccess {
                    Log.d(TAG, "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "서버통신 실패")
                }
        }
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
    TODO: MyPage CA 적용 끝난 후에 적용할 예정, 후기 수정 권한 확인 시 필요!!

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

     */

    fun setBackgroundRes(res: Drawable?) {
        if (res != null)
            _backgroundRes.value = res!!
    }


    companion object {
        const val TAG = "ReviewDetailViewModel"
        const val POST_TYPE_REVIEW = 1

    }

}