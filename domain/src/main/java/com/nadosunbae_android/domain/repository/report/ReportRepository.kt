package com.nadosunbae_android.domain.repository.report

import com.nadosunbae_android.domain.model.report.ReportData
import com.nadosunbae_android.domain.model.report.ReportParam

interface ReportRepository {

    suspend fun postReport(reportParam: ReportParam) : ReportData
}