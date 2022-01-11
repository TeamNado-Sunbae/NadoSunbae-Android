package com.nadosunbae_andorid.presentation.ui.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_andorid.data.model.review.PreviewData

class ReviewListViewModel : ViewModel() {

    // repository 인스턴스 생성할 자리

    // 과방탭과 함께 쓰기 때문에 MainViewModel로 옮길 예정..?
    private var _selectedMajor = MutableLiveData<String>()
    val selectedMajor: LiveData<String>
        get() = _selectedMajor

    private var _urlHomepage = MutableLiveData<String>()
    val urlHomepage: LiveData<String>
        get() = _urlHomepage

    private var _urlSubjectTable = MutableLiveData<String>()
    val urlSubjectTable: LiveData<String>
        get() = _urlSubjectTable

    val previewList = MutableLiveData<List<PreviewData>>()


}