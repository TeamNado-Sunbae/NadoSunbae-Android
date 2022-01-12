package com.nadosunbae_andorid.data.model.response.classroom

data class ResponseClassRoomMainData(
    val data: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val commentCount: Int,
        val content: String,
        val createdAt: String,
        val likeCount: Int,
        val postId: Int,
        val title: String,
        val writer: Writer
    ) {
        data class Writer(
            val nickname: String,
            val userId: Int
        )
    }
}