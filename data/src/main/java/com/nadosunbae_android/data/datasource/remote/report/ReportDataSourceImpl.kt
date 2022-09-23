package com.nadosunbae_android.data.datasource.remote.report

import com.nadosunbae_android.data.api.report.ReportService
import com.nadosunbae_android.data.model.request.report.RequestReportParam
import com.nadosunbae_android.data.model.response.report.ResponseReportData
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(private val service: ReportService) :
    ReportDataSource {
    override suspend fun postReport(requestReportParam: RequestReportParam): ResponseReportData {
        return service.postReport(requestReportParam)
    }
}