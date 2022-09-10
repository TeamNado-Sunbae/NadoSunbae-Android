package com.nadosunbae_android.domain.repository.main

import com.nadosunbae_android.domain.model.main.AppLinkData
import com.nadosunbae_android.domain.model.main.MajorKeyData

interface MainRepository {


    suspend fun getMyPageAppLink() : AppLinkData

}