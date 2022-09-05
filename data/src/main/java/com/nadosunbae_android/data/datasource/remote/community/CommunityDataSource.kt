package com.nadosunbae_android.data.datasource.remote.community

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.community.ResponseCommunityMainData

interface CommunityDataSource {

    //커뮤니티 메인 게시글 데이터
    suspend fun getCommunityMain(universityId : String,majorId: String?, filter: String, sort: String, search : String?)
            : Response<List<ResponseCommunityMainData>>
}