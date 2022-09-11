package com.nadosunbae_android.data.datasource.database

import com.nadosunbae_android.data.datasource.database.dao.impl.MajorListDaoImpl

interface NadoSunbaeDatabase {
    fun majorListDao() : MajorListDaoImpl
}