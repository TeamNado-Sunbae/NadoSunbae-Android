package com.nadosunbae_android.domain.model.main

data class SelectableData(
    var id: Int,
    val name: String,
    var isSelected: Boolean,
    var isFavorites : Boolean ?= false
){
    companion object{
        val DEFAULT = SelectableData(
            id = 0,
            name = "",
            isSelected = true,
            isFavorites = false
        )
    }
}
