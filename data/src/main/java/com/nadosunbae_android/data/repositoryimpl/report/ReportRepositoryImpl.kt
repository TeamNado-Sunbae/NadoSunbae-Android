package com.nadosunbae_android.data.repositoryimpl.report

import com.nadosunbae_android.data.datasource.remote.report.ReportDataSource
import com.nadosunbae_android.data.model.request.report.toEntity
import com.nadosunbae_android.data.model.response.report.toEntity
import com.nadosunbae_android.domain.model.report.ReportData
import com.nadosunbae_android.domain.model.report.ReportParam
import com.nadosunbae_android.domain.repository.report.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val dataSource: ReportDataSource) :
    ReportRepository {
    override suspend fun postReport(reportParam: ReportParam): ReportData {
        return dataSource.postReport(reportParam.toEntity()).toEntity()
    }
}