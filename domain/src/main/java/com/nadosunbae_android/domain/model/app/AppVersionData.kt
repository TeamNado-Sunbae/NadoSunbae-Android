package com.nadosunbae_android.domain.model.app

data class AppVersionData(
    val AOS: String
){
    companion object{
        val DEFAULT = AppVersionData(
            AOS = ""
        )
    }
}
