package com.nadosunbae_android.domain.repository.report

import com.nadosunbae_android.domain.model.report.ReportData
import com.nadosunbae_android.domain.model.report.ReportParam
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun postReport(reportParam: ReportParam) : Flow<ReportData>
}