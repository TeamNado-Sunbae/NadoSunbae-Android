package com.nadosunbae_android.app.di

import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.domain.repository.like.LikeRepository
import com.nadosunbae_android.domain.repository.main.MainRepository
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import com.nadosunbae_android.domain.repository.sign.SignRepository
import com.nadosunbae_android.data.repositoryimpl.classroom.ClassRoomRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.like.LikeRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.main.MainRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.mypage.MyPageRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.review.ReviewRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.sign.SignRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module{
    single<ClassRoomRepository> { ClassRoomRepositoryImpl(get()) }
    single<LikeRepository> { LikeRepositoryImpl(get()) }
    single<MainRepository> { MainRepositoryImpl(get()) }
    single<MyPageRepository> { MyPageRepositoryImpl(get()) }
    single<SignRepository>{ SignRepositoryImpl(get()) }
    single<ReviewRepository>{ ReviewRepositoryImpl(get()) }

}
