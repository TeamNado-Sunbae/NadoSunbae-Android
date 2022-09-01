package com.nadosunbae_android.domain.repository.community

import com.nadosunbae_android.domain.model.community.CommunityMainData
import kotlinx.coroutines.flow.Flow


interface CommunityRepository {

    fun getCommunityMain(
        majorId: String,
        filter: String,
        sort: String
    ): Flow<List<CommunityMainData>>
}