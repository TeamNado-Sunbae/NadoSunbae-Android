package com.nadosunbae_android.domain.model.post

data class PostWriteData(
    val answererId: Int,
    val content: String,
    val createdAt: String,
    val postId: Int,
    val majorId: Int,
    val title: String,
    val type: String,
    val firstMajorName: String,
    val firstMajorStart: String,
    val writeId: Int,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String
)

data class PostWriteParam(
    var type : String,
    var majorId : String?,
    var answerId : String,
    var title : String,
    var content : String
){
    companion object{
        val DEFAULT = PostWriteParam(
            "general","","","",""
        )
    }
}
