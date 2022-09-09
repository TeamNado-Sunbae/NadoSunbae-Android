package com.nadosunbae_android.data.repositoryimpl.major

import com.nadosunbae_android.data.datasource.database.dao.MajorListDao
import com.nadosunbae_android.data.datasource.database.entity.MajorList
import com.nadosunbae_android.data.datasource.database.entity.toEntity
import com.nadosunbae_android.data.datasource.remote.major.MajorDataSource
import com.nadosunbae_android.data.model.response.major.toEntity
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.repository.major.MajorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MajorRepositoryImpl @Inject constructor(
    private val dataSource: MajorDataSource,
    private val dao: MajorListDao
) : MajorRepository {
    override fun getMajorList(
        universityId: String,
        filter: String,
        exclude: String
    ): Flow<List<MajorListData>> = flow {
        val majorData = dao.getItem()
        if (majorData.isEmpty()) {
            val response = dataSource.getMajorList(universityId, filter, exclude)
            response.data.map { it.toEntity() }.let { data ->
                dao.insert(
                    data.map {
                        MajorList(
                            majorName = it.majorName,
                            majorId = it.majorId
                        )
                    }
                )
            }
        }

        emit(majorData.map { it.toEntity() })

}.flowOn(Dispatchers.IO)
}