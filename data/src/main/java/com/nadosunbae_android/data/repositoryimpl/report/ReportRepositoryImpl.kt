package com.nadosunbae_android.data.repositoryimpl.report

import com.nadosunbae_android.data.datasource.remote.report.ReportDataSource
import com.nadosunbae_android.data.model.request.report.toEntity
import com.nadosunbae_android.data.model.response.report.toEntity
import com.nadosunbae_android.domain.model.report.ReportData
import com.nadosunbae_android.domain.model.report.ReportParam
import com.nadosunbae_android.domain.repository.report.ReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val dataSource: ReportDataSource) :
    ReportRepository {
    override fun postReport(reportParam: ReportParam): Flow<ReportData> {
        return flow {
            emit(dataSource.postReport(reportParam.toEntity()).toEntity())
        }.flowOn(Dispatchers.IO)
    }
}