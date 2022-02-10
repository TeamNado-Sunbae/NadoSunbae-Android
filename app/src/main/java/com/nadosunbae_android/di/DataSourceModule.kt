package com.nadosunbae_android.di

import com.nadosunbae_android.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_android.datasource.remote.like.LikeDataSource
import com.nadosunbae_android.datasource.remote.like.LikeDataSourceImpl
import com.nadosunbae_android.datasource.remote.main.MainDataSource
import com.nadosunbae_android.datasource.remote.main.MainDataSourceImpl
import com.nadosunbae_android.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.datasource.remote.mypage.MyPageDataSourceImpl
import com.nadosunbae_android.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.datasource.remote.notification.NotificationDataSourceImpl
import com.nadosunbae_android.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.datasource.remote.review.ReviewDataSourceImpl
import com.nadosunbae_android.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.datasource.remote.sign.SignDataSourceImpl
import org.koin.core.scope.get
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