package com.nadosunbae_android.data.model.response.sign

data class ResponseSignUp(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Data
) {
    data class Data(
        val user: User
    ) {
        data class User(
            val userId: Int,
            val createdAt: String
        )
    }
}