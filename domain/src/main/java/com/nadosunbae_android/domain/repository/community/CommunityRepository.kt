package com.nadosunbae_android.domain.repository.community

import com.nadosunbae_android.domain.model.community.CommunityMainData
import kotlinx.coroutines.flow.Flow


interface CommunityRepository {

    fun getCommunityMain(
        universityId : String,
        majorId: String?,
        filter: String,
        sort: String,
        search : String?
    ): Flow<List<CommunityMainData>>
}