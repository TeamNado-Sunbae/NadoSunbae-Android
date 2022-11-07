package com.nadosunbae_android.domain.model.mypage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyPageModifyItem(
    var profileImageId: Int,
    var nickname : String,
    var bio : String,
    var firstMajorId : Int,
    var firstMajorStart : String,
    var secondMajorId : Int,
    var secondMajorStart : String,
    var isOnQuestion : Boolean
) : Parcelable
