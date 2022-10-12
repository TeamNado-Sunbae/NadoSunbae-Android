package com.nadosunbae_android.domain.model.main

data class SelectableData(
    var id: Int,
    var name: String,
    var isSelected: Boolean,
    var isFavorites : Boolean ?= false
){
    companion object{
        val DEFAULT = SelectableData(
            id = -1,
            name = "",
            isSelected = true,
            isFavorites = false
        )
        val COMMUNITYDEFAULT = SelectableData(
            id = -1,
            name = "학과 무관",
            isSelected = true,
            isFavorites = false
        )
    }
}
