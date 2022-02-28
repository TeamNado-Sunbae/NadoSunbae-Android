package com.nadosunbae_android.domain.model.sign

import java.util.*

data class CertificationEmailItem(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val email: String
    )
}
