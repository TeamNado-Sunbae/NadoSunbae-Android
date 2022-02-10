package com.nadosunbae_android.di

import com.nadosunbae_android.api.classroom.ClassRoomService
import com.nadosunbae_android.api.like.LikeService
import com.nadosunbae_android.api.main.MainService
import com.nadosunbae_android.api.mypage.MyPageService
import com.nadosunbae_android.api.notification.NotificationService
import com.nadosunbae_android.api.review.ReviewService
import com.nadosunbae_android.api.sign.SignService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module{

    single<ClassRoomService>{
        get<Retrofit>().create(ClassRoomService::class.java)
    }
    single<LikeService>{
        get<Retrofit>().create(LikeService::class.java)
    }
    single<MainService>{
        get<Retrofit>().create(MainService::class.java)
    }
    single<MyPageService>{
        get<Retrofit>().create(MyPageService::class.java)
    }
    single<NotificationService>{
        get<Retrofit>().create(NotificationService::class.java)
    }
    single<ReviewService>{
        get<Retrofit>().create(ReviewService::class.java)
    }
    single<SignService>{
        get<Retrofit>().create(SignService::class.java)
    }

}