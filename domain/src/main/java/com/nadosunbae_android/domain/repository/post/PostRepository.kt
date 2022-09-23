package com.nadosunbae_android.domain.repository.post

import com.nadosunbae_android.domain.model.post.*
import kotlinx.coroutines.flow.Flow


interface PostRepository {
    //게시글 작성
    fun postWrite(postWriteParam: PostWriteParam) : Flow<PostWriteData>

    //게시글 삭제
    fun deletePost(postId : String) : Flow<PostDeleteData>

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

    //게시글 수정
    fun putPostUpdate(postId : String, postUpdateParam: PostUpdateParam) : Flow<PostUpdateData>
}