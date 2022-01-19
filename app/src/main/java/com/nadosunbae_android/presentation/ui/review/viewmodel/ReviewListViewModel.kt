package com.nadosunbae_android.presentation.ui.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import com.nadosunbae_android.data.model.ui.PreviewData
import com.nadosunbae_android.data.repository.review.ReviewRepositoryImpl

class ReviewListViewModel : ViewModel() {
    private val reviewRepository = ReviewRepositoryImpl()
    private val _reviewListData = MutableLiveData<ResponseReviewListData>()
    val reviewListData: LiveData<ResponseReviewListData>
        get() = _reviewListData

    // 선택 학과홈페이지 링크
    private var _urlHomepage = MutableLiveData<String>()
    val urlHomepage: LiveData<String>
        get() = _urlHomepage

    // 선택 학과 이수과목 일람표 링크
    private var _urlSubjectTable = MutableLiveData<String>()
    val urlSubjectTable: LiveData<String>
        get() = _urlSubjectTable

    // 리뷰 목록 list
    val previewList = MutableLiveData<List<PreviewData>>()

    fun getReviewList(sort: String = "recent", request: RequestReviewListData) {
        reviewRepository.getReviewList(sort, request,
            onResponse = {
                if (it.isSuccessful) {
                    _reviewListData.value = it.body()
                    Log.d("reviewViewModel", "서버 통신 성공")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d("reviewViewModel", "서버 통신 실패")
            }
        )
    }



    /*       --------- test용 data set 코드 ---------
                    ( api 연결되면 다 지울 예정!)
     */
    fun setPageUrl(url: String) {
        _urlHomepage.value = url
    }
    fun setSubjectTableUrl(url: String) {
        _urlSubjectTable.value = url
    }
}