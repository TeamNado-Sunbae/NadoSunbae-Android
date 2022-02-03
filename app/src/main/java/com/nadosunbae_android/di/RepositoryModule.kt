package com.nadosunbae_android.di

import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.repository.like.LikeRepository
import com.nadosunbae_android.repository.main.MainRepository
import com.nadosunbae_android.repository.mypage.MyPageRepository
import com.nadosunbae_android.repository.notification.NotificationRepository
import com.nadosunbae_android.repository.review.ReviewRepository
import com.nadosunbae_android.repository.sign.SignRepository
import com.nadosunbae_android.repositoryimpl.classroom.ClassRoomRepository
import com.nadosunbae_android.repositoryimpl.classroom.ClassRoomRepositoryImpl
import com.nadosunbae_android.repositoryimpl.like.LikeRepository
import com.nadosunbae_android.repositoryimpl.like.LikeRepositoryImpl
import com.nadosunbae_android.repositoryimpl.main.MainRepository
import com.nadosunbae_android.repositoryimpl.main.MainRepositoryImpl
import com.nadosunbae_android.repositoryimpl.mypage.MyPageRepository
import com.nadosunbae_android.repositoryimpl.mypage.MyPageRepositoryImpl
import com.nadosunbae_android.repositoryimpl.notification.NotificationRepository
import com.nadosunbae_android.repositoryimpl.notification.NotificationRepositoryImpl
import com.nadosunbae_android.repositoryimpl.review.ReviewRepository
import com.nadosunbae_android.repositoryimpl.review.ReviewRepositoryImpl
import com.nadosunbae_android.repositoryimpl.sign.SignRepository
import com.nadosunbae_android.repositoryimpl.sign.SignRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module{
    single<ClassRoomRepository> { ClassRoomRepositoryImpl(get()) }
    single<LikeRepository> { LikeRepositoryImpl() }
    single<MainRepository> { MainRepositoryImpl() }
    single<MyPageRepository> { MyPageRepositoryImpl() }
    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
    single<ReviewRepository>{ ReviewRepositoryImpl() }
    single<SignRepository>{ SignRepositoryImpl() }
}
