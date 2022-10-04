package com.nadosunbae_android.domain.model.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MajorSelectData(
    var majorId: Int,
    var majorName: String
) : Parcelable{
    companion object{
        val DEFAULT = MajorSelectData(
            majorId = -1,
            majorName = ""
        )
    }
}
