package com.nadosunbae_android.domain.model.main

data class SelectableData(
    var id: Int,
    val name: String,
    var isSelected: Boolean
){
    companion object{
        val DEFAULT = SelectableData(
            id = -2,
            name = "학과 무관",
            isSelected = true
        )
    }
}
