package com.nadosunbae_android.data.datasource.remote.report

import com.nadosunbae_android.data.model.request.report.RequestReportParam
import com.nadosunbae_android.data.model.response.report.ResponseReportData

interface ReportDataSource {

    //신고
    suspend fun postReport(requestReportParam: RequestReportParam): ResponseReportData
}