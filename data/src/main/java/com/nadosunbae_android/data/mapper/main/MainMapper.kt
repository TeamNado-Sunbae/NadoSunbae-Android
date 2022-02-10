package com.nadosunbae_android.data.mapper.main

import com.nadosunbae_android.domain.model.main.MajorKeyData
import com.nadosunbae_android.data.model.response.major.ResponseMajorListData

object MainMapper {

    // 학과목록 불러오기 response
    fun mapperToMajorData(responseMajorListData: ResponseMajorListData): List<MajorKeyData> {
        return responseMajorListData.data.map {
            MajorKeyData(
                isFirstMajor = it.isFirstMajor,
                isSecondMajor = it.isSecondMajor,
                majorId = it.majorId,
                majorName = it.majorName
            )
        }
    }

}