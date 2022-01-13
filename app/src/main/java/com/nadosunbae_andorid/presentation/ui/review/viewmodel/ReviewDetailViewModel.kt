package com.nadosunbae_andorid.presentation.ui.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewDetailViewModel : ViewModel() {

    private var _urlBackground = MutableLiveData<String>()
    val urlBackground: LiveData<String>
        get() = _urlBackground


}