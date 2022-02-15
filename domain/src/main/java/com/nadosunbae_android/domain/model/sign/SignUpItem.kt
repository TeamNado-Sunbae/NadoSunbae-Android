package com.nadosunbae_android.domain.model.sign

//회원가입 ResponseData
data class SignUpItem(
    val success: Boolean,
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