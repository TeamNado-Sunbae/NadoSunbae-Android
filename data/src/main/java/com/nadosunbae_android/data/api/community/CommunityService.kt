package com.nadosunbae_android.data.api.community

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.community.ResponseCommunityMainData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityService {


    @GET("post/university/{universityId}")
    suspend fun getCommunityMain(
        @Path("universityId") universityId : String,
        @Query("majorId") majorId : String?,
        @Query("filter") filter : String,
        @Query("sort") sort : String,
        @Query("search") search : String?= ""
    ) : Response<List<ResponseCommunityMainData>>
}