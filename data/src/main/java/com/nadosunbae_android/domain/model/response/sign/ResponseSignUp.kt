package com.nadosunbae_android.domain.model.response.sign

data class ResponseSignUp(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val accesstoken: String,
        val user: User
    ) {
        data class User(
            val createdAt: String,
            val userId: Int
        )
    }
}