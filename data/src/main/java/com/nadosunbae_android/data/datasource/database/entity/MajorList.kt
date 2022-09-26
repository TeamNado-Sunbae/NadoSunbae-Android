package com.nadosunbae_android.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nadosunbae_android.domain.model.major.MajorListData

@Entity
data class MajorList(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val majorId : Int,
    val majorName : String,
    val isFavorites : Boolean?= false
)

fun MajorList.toEntity() : MajorListData {
    return MajorListData(
        majorName = this.majorName,
        majorId = this.majorId,
        isFavorites = isFavorites
    )
}
