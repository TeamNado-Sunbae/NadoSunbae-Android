package com.nadosunbae_android.data.mapper.main

import com.nadosunbae_android.domain.model.main.MajorData
import com.nadosunbae_android.domain.model.response.major.ResponseMajorListData

object MainMapper {

    // 학과목록 불러오기 response
    fun mapperToMajorData(responseMajorListData: ResponseMajorListData): List<MajorData> {
        return responseMajorListData.data.map {
            MajorData(
                isFirstMajor = it.isFirstMajor,
                isSecondMajor = it.isSecondMajor,
                majorId = it.majorId,
                majorName = it.majorName
            )
        }
    }

}