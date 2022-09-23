package com.nadosunbae_android.domain.model.report

data class ReportData(
        val status : Int,
        val createdAt: String,
        val reportId: Int
)

data class ReportParam(
    val targetId : Int,
    val type : String,
    val reason : String
)
