package com.nadosunbae_android.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadosunbae_android.data.datasource.database.entity.MajorList

@Database(
    entities = [
        MajorList::class
    ],
    version = 1
)


abstract class NadoSunbaeDatabaseImpl : RoomDatabase(),NadoSunbaeDatabase