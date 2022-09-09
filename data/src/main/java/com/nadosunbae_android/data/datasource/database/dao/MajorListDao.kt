package com.nadosunbae_android.data.datasource.database.dao

import com.nadosunbae_android.data.datasource.database.entity.MajorList

interface MajorListDao {
    fun insert(items : List<MajorList>)
}