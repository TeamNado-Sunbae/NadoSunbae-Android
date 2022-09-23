package com.nadosunbae_android.data.model.response.report

import com.nadosunbae_android.domain.model.report.ReportData

data class ResponseReportData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val createdAt: String,
        val reportId: Int
    )
}

fun ResponseReportData.toEntity() : ReportData{
    return ReportData(
        createdAt = this.data.createdAt,
        reportId = this.data.reportId
    )

}