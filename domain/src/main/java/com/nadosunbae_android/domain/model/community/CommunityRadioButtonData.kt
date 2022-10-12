package com.nadosunbae_android.domain.model.community

data class CommunityRadioButtonData(
    val freedom: Boolean,
    val question: Boolean,
    val info: Boolean
) {
    companion object {
        val DEFAULT = CommunityRadioButtonData(
            freedom = true, question = false, info = false
        )
    }
}
