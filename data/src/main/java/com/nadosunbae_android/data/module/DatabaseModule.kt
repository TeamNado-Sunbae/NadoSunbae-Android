package com.nadosunbae_android.data.module

import android.content.Context
import androidx.room.Room
import com.nadosunbae_android.data.datasource.database.NadoSunbaeDatabase
import com.nadosunbae_android.data.datasource.database.NadoSunbaeDatabaseImpl
import com.nadosunbae_android.data.datasource.database.dao.MajorListDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : NadoSunbaeDatabase{
        return Room.databaseBuilder(
            context,
            NadoSunbaeDatabaseImpl::class.java,
            "bbang-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Reusable
    @Provides
    fun provideMajorListDao(
        database: NadoSunbaeDatabase
    ) : MajorListDao{
        return database.majorListDao()
    }


}