package com.nadosunbae_android.data.datasource.remote.community

import com.nadosunbae_android.data.api.community.CommunityService
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.community.ResponseCommunityMainData
import javax.inject.Inject

class CommunityDataSourceImpl @Inject constructor(
    val service: CommunityService
) : CommunityDataSource {


    override suspend fun getCommunityMain(
        majorId: String,
        filter: String,
        sort: String
    ): Response<List<ResponseCommunityMainData>> {
        return service.getCommunityMain(majorId, filter, sort)
    }
}