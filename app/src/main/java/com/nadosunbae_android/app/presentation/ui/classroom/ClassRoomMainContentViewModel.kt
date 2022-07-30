package com.nadosunbae_android.app.presentation.ui.classroom

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.domain.usecase.review.GetMajorInfoDataUseCase
import com.nadosunbae_android.domain.usecase.review.GetReviewListDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassRoomMainContentViewModel @Inject constructor(
) : ViewModel() {

    val curFragment = ObservableField(0)

}