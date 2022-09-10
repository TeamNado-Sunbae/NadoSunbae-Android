package com.nadosunbae_android.domain.model.major

data class MajorListData(
    val majorId: Int,
    val majorName: String
){
    companion object{
        val DEFAULT = MajorListData(
            majorId = 0,
            majorName = ""
        )
    }
}
