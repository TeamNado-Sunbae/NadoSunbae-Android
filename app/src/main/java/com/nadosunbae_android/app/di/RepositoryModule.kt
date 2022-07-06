package com.nadosunbae_android.app.di

import com.nadosunbae_android.data.api.classroom.ClassRoomService
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
import com.nadosunbae_android.data.repositoryimpl.notification.NotificationRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.review.ReviewRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.sign.SignRepositoryImpl
import com.nadosunbae_android.domain.repository.notification.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Singleton
    @Provides
    fun classRoomRepository(

    ) : ClassRoomRepository{
        return ClassRoomRepositoryImpl()
    }
    single<ClassRoomRepository> { ClassRoomRepositoryImpl(get()) }
    single<LikeRepository> { LikeRepositoryImpl(get()) }
    single<MainRepository> { MainRepositoryImpl(get()) }
    single<MyPageRepository> { MyPageRepositoryImpl(get()) }
    single<SignRepository>{ SignRepositoryImpl(get()) }
    single<ReviewRepository>{ ReviewRepositoryImpl(get()) }
    single<NotificationRepository>{NotificationRepositoryImpl(get())}

}
