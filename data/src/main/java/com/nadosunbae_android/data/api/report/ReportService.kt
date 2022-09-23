package com.nadosunbae_android.data.api.report

import com.nadosunbae_android.data.model.request.report.RequestReportParam
import com.nadosunbae_android.data.model.response.report.ResponseReportData
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {

    @POST("report")
    suspend fun postReport(
        @Body requestReportParam: RequestReportParam
    ) : ResponseReportData
}