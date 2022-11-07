package com.nadosunbae_android.app.presentation.ui.classroom.review.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.domain.model.classroom.ReportItem
import com.nadosunbae_android.domain.model.like.LikeParam
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.review.ReviewDetailData
import com.nadosunbae_android.domain.repository.like.LikeRepository
import com.nadosunbae_android.domain.usecase.classroom.PostReportUseCase
import com.nadosunbae_android.domain.usecase.review.DeleteReviewDataUseCase
import com.nadosunbae_android.domain.usecase.review.GetReviewDetailDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReviewDetailViewModel @Inject constructor(
    private val getReviewDetailDataUseCase: GetReviewDetailDataUseCase,
    private val deleteReviewDataUseCase: DeleteReviewDataUseCase,
    private val likeRepository: LikeRepository,
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
            runCatching { getReviewDetailDataUseCase(postId) }
                .onSuccess {
                    _reviewDetailData.value = it
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

    // 좋아요
    fun postLikeReview(postId: Int) {
        viewModelScope.launch {
            likeRepository.postLike(LikeParam(postId.toString(), "review"))
                .catch {
                    Timber.d("서버통신 실패")
                }
                .collectLatest {
                    getReviewDetail(postId)
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
                    Timber.d("후기 삭제 서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("후기 삭제 서버통신 실패")
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