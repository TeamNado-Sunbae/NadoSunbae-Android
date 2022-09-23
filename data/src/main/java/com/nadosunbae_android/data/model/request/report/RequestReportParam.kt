package com.nadosunbae_android.data.model.request.report

import com.nadosunbae_android.domain.model.report.ReportParam

data class RequestReportParam(
    val targetId : Int,
    val type : String,
    val reason : String
)


fun ReportParam.toEntity() : RequestReportParam{
    return RequestReportParam(
        targetId = this.targetId,
        type = this.type,
        reason = this.reason
    )
}