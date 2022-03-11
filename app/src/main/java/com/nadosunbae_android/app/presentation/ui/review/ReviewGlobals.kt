package com.nadosunbae_android.app.presentation.ui.review

import com.nadosunbae_android.domain.model.main.MajorSelectData
import timber.log.Timber

object ReviewGlobals {

    var isReviewed = false
        set(value) {
            field = value
            Timber.d("isReviewed change : ${field}")
        }
    var selectedMajor: MajorSelectData? = null
    var firstMajor: MajorSelectData? = null
    var secondMajor: MajorSelectData? = null
}