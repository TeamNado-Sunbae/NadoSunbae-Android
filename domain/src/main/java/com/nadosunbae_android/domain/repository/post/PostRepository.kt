package com.nadosunbae_android.domain.repository.post

import com.nadosunbae_android.domain.model.post.PostData
import com.nadosunbae_android.domain.model.post.PostDetailData
import kotlinx.coroutines.flow.Flow


interface PostRepository {

    //게시글 메인 조회
    fun getPost(
        universityId: String,
        majorId: String?,
        filter: String,
        sort: String,
        search: String?
    ): Flow<List<PostData>>

    //게시글 상세 조회
    fun getPostDetail(postId: String): Flow<PostDetailData>
}