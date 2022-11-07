package com.nadosunbae_android.domain.model.major

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MajorListData(
    val majorId: Int,
    val majorName: String,
    val isFavorites : Boolean ?= false
) : Parcelable{
    companion object{
        val DEFAULT = MajorListData(
            majorId = 0,
            majorName = "",
            isFavorites = false
        )
    }
}


