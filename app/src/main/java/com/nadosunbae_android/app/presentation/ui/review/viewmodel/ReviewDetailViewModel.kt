package com.nadosunbae_android.app.presentation.ui.review.viewmodel

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.review.ReviewDetailData
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import com.nadosunbae_android.domain.usecase.review.DeleteReviewDataUseCase
import com.nadosunbae_android.domain.usecase.review.GetReviewDetailDataUseCase
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.domain.model.classroom.ReportItem
import com.nadosunbae_android.domain.usecase.classroom.PostReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ReviewDetailViewModel(
    private val getReviewDetailDataUseCase: GetReviewDetailDataUseCase,
    private val deleteReviewDataUseCase: DeleteReviewDataUseCase,
    private val postLikeDataUseCase: PostLikeDataUseCase,
    private val postReportUseCase: PostReportUseCase
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    private val _reviewDetailData = MutableLiveData<ReviewDetailData>()
    val reviewDetailData: LiveData<ReviewDetailData>
        get() = _reviewDetailData

    private val _backgroundRes = MutableLiveData<Drawable>()
    val backgroundRes: LiveData<Drawable>
        get() = _backgroundRes

    private val _signUserId = MutableLiveData<Int>()
    val signUserId: LiveData<Int>
        get() = _signUserId

    private val _reportSuccess = MutableLiveData<Boolean>()
    val reportSuccess: LiveData<Boolean>
        get() = _reportSuccess

    override var dropDownSelected = MutableLiveData<SelectableData>()
    override val onLoadingEnd = MutableLiveData<Boolean>(false)


    private var _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int>
        get() = _statusCode

    private var _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    // 후기 상세정보 불러오기
    fun getReviewDetail(postId: Int) {
        viewModelScope.launch {
            when (val reviewDetailData =
                safeApiCall(Dispatchers.IO) { getReviewDetailDataUseCase(postId) }) {
                is ResultWrapper.Success -> {
                    _reviewDetailData.value = reviewDetailData.data!!
                    _statusCode.value = 200
                    Timber.d("postReport : 신고 성공!")
                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("postReport : 네트워크 실패")


                }
                is ResultWrapper.GenericError -> {
                    Timber.d("postReport :사용자 에러")
                    _message.value = reviewDetailData.message ?: ""
                    _statusCode.value = reviewDetailData.code ?: 0
                    Timber.d("reviewDetail : ${reviewDetailData.message}")
                    Timber.d("reviewDetail : ${reviewDetailData.code}")
                }
            }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    // 좋아요
    fun postLikeReview(postId: Int) {
        val likeItem = LikeItem(postId, POST_TYPE_REVIEW)

        viewModelScope.launch {
            runCatching { postLikeDataUseCase(likeItem) }
                .onSuccess {
                    Timber.d("서버통신 성공")

                    getReviewDetail(postId)
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }


    // 후기 삭제
    fun deleteReview(postId: Int) {
        viewModelScope.launch {
            runCatching { deleteReviewDataUseCase(postId) }
                .onSuccess {
                    ReviewGlobals.isReviewed = it.isReviewed
                    Timber.d("서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    // 후기 신고
    fun postReport(reportItem: ReportItem) {
        viewModelScope.launch {
            runCatching { postReportUseCase(reportItem) }
                .onSuccess {
                    _reportSuccess.value = true
                    Timber.d("서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    _reportSuccess.value = false
                    Timber.d("서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }


    fun setBackgroundRes(res: Drawable?) {
        if (res != null)
            _backgroundRes.value = res!!
    }


    companion object {
        const val TAG = "ReviewDetailViewModel"
        const val POST_TYPE_REVIEW = 1

    }

}