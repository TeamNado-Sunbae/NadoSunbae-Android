package com.nadosunbae_android.presentation.ui.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewDetailViewModel : ViewModel() {

    private var _urlBackground = MutableLiveData<String>()
    val urlBackground: LiveData<String>
        get() = _urlBackground


 // test data 넣는 함수 (api 적용 전까지)
    fun setBackgroundUrl(url: String) {
        _urlBackground.value = url
    }

}