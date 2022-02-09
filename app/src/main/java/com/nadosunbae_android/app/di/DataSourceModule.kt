package com.nadosunbae_android.app.di

import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.like.LikeDataSource
import com.nadosunbae_android.data.datasource.remote.like.LikeDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.main.MainDataSource
import com.nadosunbae_android.data.datasource.remote.main.MainDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.data.datasource.remote.sign.SignDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module{
    single<ClassRoomDataSource>  { ClassRoomDataSourceImpl(get()) }
    single<LikeDataSource> {LikeDataSourceImpl(get())}
    single<MainDataSource> {MainDataSourceImpl(get())}
    single<MyPageDataSource> {MyPageDataSourceImpl(get())}
    single<NotificationDataSource> {NotificationDataSourceImpl(get())}
    single<ReviewDataSource>{ReviewDataSourceImpl(get())}
    single<SignDataSource>{SignDataSourceImpl(get())}
}