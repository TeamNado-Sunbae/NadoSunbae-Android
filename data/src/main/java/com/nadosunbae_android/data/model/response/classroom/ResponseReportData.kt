package com.nadosunbae_android.data.model.response.classroom

import java.util.*

data class ResponseReportData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val createdAt: Date,
        val reportId: Int
    )
}