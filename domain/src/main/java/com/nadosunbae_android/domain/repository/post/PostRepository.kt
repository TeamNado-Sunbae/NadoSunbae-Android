package com.nadosunbae_android.domain.repository.post

import com.nadosunbae_android.domain.model.post.PostDetailData
import kotlinx.coroutines.flow.Flow


interface PostRepository {

    //게시글 상세 조회
    fun getPostDetail(postId : String) : Flow<PostDetailData>
}