package com.nadosunbae_android.app.di

import com.nadosunbae_android.data.api.classroom.ClassRoomService
import com.nadosunbae_android.data.api.home.HomeService
import com.nadosunbae_android.data.api.like.LikeService
import com.nadosunbae_android.data.api.main.MainService
import com.nadosunbae_android.data.api.mypage.MyPageService
import com.nadosunbae_android.data.api.notification.NotificationService
import com.nadosunbae_android.data.api.post.PostService
import com.nadosunbae_android.data.api.review.ReviewService
import com.nadosunbae_android.data.api.sign.SignService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    @Singleton
    @Provides
    fun classRoomService(retrofit : Retrofit) : ClassRoomService{
        return retrofit.create(ClassRoomService::class.java)
    }
    @Singleton
    @Provides
    fun likeService(retrofit : Retrofit) : LikeService{
        return retrofit.create(LikeService::class.java)
    }

    @Singleton
    @Provides
    fun mainService(retrofit : Retrofit) : MainService{
        return retrofit.create(MainService::class.java)
    }

    @Singleton
    @Provides
    fun myPageService(retrofit : Retrofit) : MyPageService{
        return retrofit.create(MyPageService::class.java)
    }

    @Singleton
    @Provides
    fun notificationService(retrofit : Retrofit) : NotificationService{
        return retrofit.create(NotificationService::class.java)
    }
    @Singleton
    @Provides
    fun reviewService(retrofit : Retrofit) : ReviewService{
        return retrofit.create(ReviewService::class.java)
    }

    @Singleton
    @Provides
    fun signService(retrofit : Retrofit) : SignService{
        return retrofit.create(SignService::class.java)
    }


    @Singleton
    @Provides
    fun homeService(retrofit: Retrofit) : HomeService{
        return retrofit.create(HomeService::class.java)
    }

    @Singleton
    @Provides
    fun postService(retrofit : Retrofit) : PostService{
        return retrofit.create(PostService::class.java)
    }
}