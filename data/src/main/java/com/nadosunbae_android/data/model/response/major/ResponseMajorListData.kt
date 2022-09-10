package com.nadosunbae_android.data.model.response.major

import com.nadosunbae_android.domain.model.major.MajorListData

data class ResponseMajorListData(
    val majorId: Int,
    val majorName: String
)

fun ResponseMajorListData.toEntity(): MajorListData {
    return MajorListData(
        majorId = this.majorId,
        majorName = this.majorName
    )
}