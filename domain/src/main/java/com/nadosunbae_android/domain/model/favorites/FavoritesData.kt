package com.nadosunbae_android.domain.model.favorites

data class FavoritesData(
    val isDeleted: Boolean,
    val majorId: Int,
    val userId: Int,
    val success: Boolean
) {
    companion object{
        val DEFAULT = FavoritesData(
            false,0,0,false
        )
    }
}

data class FavoritesParam(
    val majorId : Int
)
