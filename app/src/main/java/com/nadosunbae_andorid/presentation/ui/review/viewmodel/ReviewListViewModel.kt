package com.nadosunbae_andorid.presentation.ui.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_andorid.data.model.review.PreviewData

class ReviewListViewModel : ViewModel() {

    // repository 인스턴스 생성할 자리

    // 학과 그래픽 url
    private var _majorGraphicUrl = MutableLiveData<String>()
    val majorGraphicUrl: LiveData<String>
        get() = _majorGraphicUrl

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



    /*       --------- test용 data set 코드 ---------
                    ( api 연결되면 다 지울 예정!)
     */
    fun setGraphicUrl(url: String) {
        _majorGraphicUrl.value = url
    }
    fun setPageUrl(url: String) {
        _urlHomepage.value = url
    }
    fun setSubjectTableUrl(url: String) {
        _urlSubjectTable.value = url
    }
}