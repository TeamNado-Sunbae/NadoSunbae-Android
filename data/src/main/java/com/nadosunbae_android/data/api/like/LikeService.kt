package com.nadosunbae_android.data.api.like

import com.nadosunbae_android.data.model.request.like.RequestPostLike
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.like.ResponsePostLike
import retrofit2.http.Body
import retrofit2.http.POST

interface LikeService {
    @POST("like")
    suspend fun postLike(
        @Body requestBody: RequestPostLike
    ) : Response<ResponsePostLike>
}