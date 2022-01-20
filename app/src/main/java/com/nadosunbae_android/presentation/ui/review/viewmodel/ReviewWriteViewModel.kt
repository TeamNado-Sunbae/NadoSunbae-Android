package com.nadosunbae_android.presentation.ui.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.review.ResponseBackgroundImageListData
import com.nadosunbae_android.data.repository.review.ReviewRepositoryImpl

class ReviewWriteViewModel : ViewModel() {
    private val reviewRepository = ReviewRepositoryImpl()

    private val _backgroundImageList = MutableLiveData<ResponseBackgroundImageListData>()
    val backgroundImageList: LiveData<ResponseBackgroundImageListData>
        get() = _backgroundImageList

    fun getBackgroundImageList() {
        reviewRepository.getBackgroundImageList(
            onResponse = {
                if (it.isSuccessful) {
                    _backgroundImageList.value = it.body()

                    Log.d(TAG, "서버통신 성공")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d(TAG, "서버통신 실패")
            }
        )
    }


    companion object {
        const val TAG = "ReviewWriteViewModel"
    }
}