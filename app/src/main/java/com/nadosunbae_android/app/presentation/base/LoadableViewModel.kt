package com.nadosunbae_android.app.presentation.base

import androidx.lifecycle.*

interface LoadableViewModel {
    val onLoadingEnd: MutableLiveData<Boolean>
}