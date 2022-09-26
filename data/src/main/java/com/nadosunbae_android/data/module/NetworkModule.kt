package com.nadosunbae_android.data.module

import com.nadosunbae_android.data.api.app.AppService
import com.nadosunbae_android.data.api.classroom.ClassRoomService
import com.nadosunbae_android.data.api.comment.CommentService
import com.nadosunbae_android.data.api.favorites.FavoritesService
import com.nadosunbae_android.data.api.home.HomeService
import com.nadosunbae_android.data.api.like.LikeService
import com.nadosunbae_android.data.api.main.MainService
import com.nadosunbae_android.data.api.major.MajorService
import com.nadosunbae_android.data.api.mypage.MyPageService
import com.nadosunbae_android.data.api.notification.NotificationService
import com.nadosunbae_android.data.api.post.PostService
import com.nadosunbae_android.data.api.report.ReportService
import com.nadosunbae_android.data.api.review.ReviewService
import com.nadosunbae_android.data.api.sign.SignService
import com.nadosunbae_android.data.api.user.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun classRoomService(retrofit: Retrofit): ClassRoomService {
        return retrofit.create(ClassRoomService::class.java)
    }

    @Singleton
    @Provides
    fun likeService(retrofit: Retrofit): LikeService {
        return retrofit.create(LikeService::class.java)
    }

    @Singleton
    @Provides
    fun mainService(retrofit: Retrofit): MainService {
        return retrofit.create(MainService::class.java)
    }

    @Singleton
    @Provides
    fun myPageService(retrofit: Retrofit): MyPageService {
        return retrofit.create(MyPageService::class.java)
    }

    @Singleton
    @Provides
    fun notificationService(retrofit: Retrofit): NotificationService {
        return retrofit.create(NotificationService::class.java)
    }

    @Singleton
    @Provides
    fun reviewService(retrofit: Retrofit): ReviewService {
        return retrofit.create(ReviewService::class.java)
    }

    @Singleton
    @Provides
    fun signService(retrofit: Retrofit): SignService {
        return retrofit.create(SignService::class.java)
    }


    @Singleton
    @Provides
    fun homeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Singleton
    @Provides
    fun postService(retrofit: Retrofit): PostService {
        return retrofit.create(PostService::class.java)
    }

    @Singleton
    @Provides
    fun majorService(retrofit: Retrofit): MajorService {
        return retrofit.create(MajorService::class.java)
    }

    @Singleton
    @Provides
    fun userService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun commentService(retrofit: Retrofit): CommentService {
        return retrofit.create(CommentService::class.java)
    }

    @Singleton
    @Provides
    fun appService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }

    @Singleton
    @Provides
    fun reportService(retrofit: Retrofit): ReportService {
        return retrofit.create(ReportService::class.java)
    }

    @Singleton
    @Provides
    fun favoritesService(retrofit: Retrofit): FavoritesService {
        return retrofit.create(FavoritesService::class.java)
    }
}
