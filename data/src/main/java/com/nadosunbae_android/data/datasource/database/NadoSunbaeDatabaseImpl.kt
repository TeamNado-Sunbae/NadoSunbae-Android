package com.nadosunbae_android.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [

    ],
    version = 1
)


abstract class NadoSunbaeDatabaseImpl : RoomDatabase(),NadoSunbaeDatabase