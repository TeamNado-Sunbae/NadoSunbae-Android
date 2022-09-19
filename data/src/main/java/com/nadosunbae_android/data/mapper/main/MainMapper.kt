package com.nadosunbae_android.data.mapper.main

import com.nadosunbae_android.data.model.response.mypage.ResponseAppLinkData
import com.nadosunbae_android.domain.model.main.AppLinkData

object MainMapper {

    //각종 링크 조회
    fun mapperToLookUpLinkData(responseMyPageAppLinkData: ResponseAppLinkData): AppLinkData {
        return AppLinkData(
            data = AppLinkData.Data(
                kakaoTalkChannel = responseMyPageAppLinkData.data.kakaoTalkChannel,
                openSourceLicense = responseMyPageAppLinkData.data.openSourceLicense,
                personalInformationPolicy = responseMyPageAppLinkData.data.personalInformationPolicy,
                termsOfService = responseMyPageAppLinkData.data.termsOfService
            ),
            success = responseMyPageAppLinkData.success
        )
    }

}