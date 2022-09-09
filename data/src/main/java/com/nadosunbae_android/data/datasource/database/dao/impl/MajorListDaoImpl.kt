package com.nadosunbae_android.data.datasource.database.dao.impl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nadosunbae_android.data.datasource.database.dao.MajorListDao
import com.nadosunbae_android.data.datasource.database.entity.MajorList

@Dao
abstract class MajorListDaoImpl : MajorListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insert(items: List<MajorList>)
}