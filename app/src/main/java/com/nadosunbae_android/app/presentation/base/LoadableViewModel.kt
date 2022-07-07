package com.nadosunbae_android.app.presentation.base

import androidx.lifecycle.MutableLiveData


interface LoadableViewModel {
    val onLoadingEnd: MutableLiveData<Boolean>
}