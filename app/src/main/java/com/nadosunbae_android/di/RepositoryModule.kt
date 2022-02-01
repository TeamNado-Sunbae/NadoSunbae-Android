package com.nadosunbae_android.di

import com.nadosunbae_android.repositoryimpl.classroom.ClassRoomRepository
import com.nadosunbae_android.repositoryimpl.like.LikeRepository
import com.nadosunbae_android.repositoryimpl.main.MainRepository
import com.nadosunbae_android.repositoryimpl.mypage.MyPageRepository
import com.nadosunbae_android.repositoryimpl.notification.NotificationRepository
import com.nadosunbae_android.repositoryimpl.review.ReviewRepository
import com.nadosunbae_android.repositoryimpl.sign.SignRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<ClassRoomRepository>  { ClassRoomRepositoryImpl() }
    single<LikeRepository> { LikeRepositoryImpl() }
    single<MainRepository> { MainRepositoryImpl() }
    single<MyPageRepository> { MyPageRepositoryImpl() }
    single<NotificationRepository> { NotificationRepositoryImpl() }
    single<ReviewRepository>{ ReviewRepositoryImpl() }
    single<SignRepository>{ SignRepositoryImpl() }
}
