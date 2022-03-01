package com.nadosunbae_android.domain.model.mypage

import java.util.*

data class MyPageBlockUpdateData(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val blockUserId: Int,
        val blockedUserId: Int,
        val createdAt: Date?,
        val id: Int,
        val isDeleted: Boolean,
        val updatedAt: String
    )
}