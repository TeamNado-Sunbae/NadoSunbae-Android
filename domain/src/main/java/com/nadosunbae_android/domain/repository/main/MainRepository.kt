package com.nadosunbae_android.domain.repository.main

import com.nadosunbae_android.domain.model.main.MajorData

interface MainRepository {

    // 학과 목록 불러오기
    suspend fun getMajorList(universityId: Int, filter: String = "all"): List<MajorData>

}