package com.nadosunbae_android.data.model.response.mypage

import java.util.*

data class ResponseMyPageBlockUpdate(
    val data: Data,
    val message: String,
    val status: Int,
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