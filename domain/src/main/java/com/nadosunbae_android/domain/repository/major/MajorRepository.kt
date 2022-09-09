package com.nadosunbae_android.domain.repository.major

import com.nadosunbae_android.domain.model.major.MajorListData
import kotlinx.coroutines.flow.Flow

interface MajorRepository {

    //학과 리스트 조회 (학교 별로)
    fun getMajorList(universityId : String, filter : String, exclude : String) : Flow<List<MajorListData>>
}