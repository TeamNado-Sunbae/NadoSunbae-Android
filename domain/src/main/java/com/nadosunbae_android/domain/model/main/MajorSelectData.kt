package com.nadosunbae_android.domain.model.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MajorSelectData(
    val majorId: Int,
    val majorName: String
) : Parcelable
