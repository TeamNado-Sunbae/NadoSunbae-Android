package com.nadosunbae_android.app.presentation.ui.classroom.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.review.BackgroundImageData
import com.nadosunbae_android.domain.model.review.ReviewEditItem
import com.nadosunbae_android.domain.model.review.ReviewWriteItem
import com.nadosunbae_android.domain.usecase.review.PostReviewDataUseCase
import com.nadosunbae_android.domain.usecase.review.PutReviewDataUseCase
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReviewWriteViewModel @Inject constructor(
    val postReviewDataUseCase: PostReviewDataUseCase,
    val putReviewDataUseCase: PutReviewDataUseCase
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override var dropDownSelected = MutableLiveData<SelectableData>()

    override val onLoadingEnd = MutableLiveData<Boolean>(false)

    private val _backgroundImageList = MutableLiveData<List<BackgroundImageData>>()
    val backgroundImageList: LiveData<List<BackgroundImageData>>
        get() = _backgroundImageList

    // 입력값 길이
    val oneLineLength = MutableLiveData<Int>(0)
    val prosConsLength = MutableLiveData<Int>(0)
    val curriculumLength = MutableLiveData<Int>(0)
    val recommendLectureLength = MutableLiveData<Int>(0)
    val nonRecommendLectureLength = MutableLiveData<Int>(0)
    val careerLength = MutableLiveData<Int>(0)
    val tipLength = MutableLiveData<Int>(0)

    private val _writeFinish = MutableLiveData<Boolean>()
    val writeFinish: LiveData<Boolean>
        get() = _writeFinish
  
//    // 후기 배경 목록 불러오기 -> 사용x 변경됨
//    fun getBackgroundImageList() {
//        viewModelScope.launch {
//            runCatching { getBackgroundImageListDataUseCase() }
//                .onSuccess {
//                    _backgroundImageList.value = it
//                    Timber.d("서버통신 성공")
//                }
//                .onFailure {
//                    it.printStackTrace()
//                    Timber.d("서버통신 실패")
//                }
//                .also {
//                    onLoadingEnd.value = true
//                }
//        }
//    }

    // 후기 작성
    fun postReview(reviewWriteItem: ReviewWriteItem) {
        viewModelScope.launch {
            runCatching { postReviewDataUseCase(reviewWriteItem) }
                .onSuccess {
                    Timber.d("서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("서버통신 실패")
                }
                .also {
                    // TODO 이상하게 성공해도 fail로 와서.. onSuccess에 있어야하지만 여기 두겠습니다..!
                    if (!ReviewGlobals.isReviewed)     // 후기 글을 처음 작성하는 사람
                        FirebaseAnalyticsUtil.userPost(FirebaseAnalyticsUtil.Post.REVIEW_NEW)
                    else
                        FirebaseAnalyticsUtil.userPost(FirebaseAnalyticsUtil.Post.REVIEW_ADD)
                    MainGlobals.signInData?.isReviewInappropriate = false
                    ReviewGlobals.isReviewed = true
                    _writeFinish.value = true
                    onLoadingEnd.value = true
                }
        }

    }


    // 후기 수정
    fun putReview(postId: Int, reviewEditItem: ReviewEditItem) {
        viewModelScope.launch {
            runCatching { putReviewDataUseCase(postId, reviewEditItem) }
                .onSuccess {
                    Timber.d("서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                    _writeFinish.value = true
                }
        }
    }

    fun setBackgroundImageList(list: List<BackgroundImageData>) {
        _backgroundImageList.value = list
    }

    companion object {
        const val TAG = "ReviewWriteViewModel"
    }

}