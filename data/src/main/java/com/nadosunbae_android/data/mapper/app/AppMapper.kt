package com.nadosunbae_android.data.mapper.app

import com.nadosunbae_android.data.model.response.app.ResponseAppBanner
import com.nadosunbae_android.domain.model.app.AppBannerData

object AppMapper {
    fun mapperToAppBanner(responseAppBanner: ResponseAppBanner) : List<AppBannerData> {
        return responseAppBanner.data.map {
            AppBannerData(
                imageUrl = it.imageUrl,
                redirectUrl = it.redirectUrl
            )
        }
    }
}