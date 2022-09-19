package com.nadosunbae_android.domain.model.major

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MajorListData(
    val majorId: Int,
    val majorName: String
) : Parcelable{
    companion object{
        val DEFAULT = MajorListData(
            majorId = 0,
            majorName = ""
        )
    }
}


