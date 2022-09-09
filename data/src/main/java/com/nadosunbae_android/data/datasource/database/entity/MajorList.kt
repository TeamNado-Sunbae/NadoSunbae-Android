package com.nadosunbae_android.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MajorList(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val majorId : Int,
    val majorName : Int
)
