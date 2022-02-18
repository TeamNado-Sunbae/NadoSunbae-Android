package com.nadosunbae_android.domain.model.mypage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyPageModifyData(
    val data: Data,
    val success: Boolean
): Parcelable {
    @Parcelize
    data class Data(
        val firstMajorId: Int,
        val firstMajorStart: String,
        val isOnQuestion: Boolean,
        val nickname: String,
        val secondMajorId: Int,
        val secondMajorStart: String,
        val updatedAt: String
    ) : Parcelable
}