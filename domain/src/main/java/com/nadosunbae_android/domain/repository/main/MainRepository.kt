package com.nadosunbae_android.domain.repository.main

import com.nadosunbae_android.domain.model.main.AppLinkData
import com.nadosunbae_android.domain.model.main.MajorKeyData

interface MainRepository {

    // 학과 목록 불러오기
    suspend fun getMajorList(universityId: Int, filter: String = "all"): List<MajorKeyData>

    suspend fun getMyPageAppLink() : AppLinkData

}