package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.ReportData
import com.nadosunbae_android.domain.model.classroom.ReportItem
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class PostReportUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(reportItem : ReportItem) : ReportData{
        return repository.postReport(reportItem)
    }
}