package com.nadosunbae_android.app.di

import com.nadosunbae_android.data.api.classroom.ClassRoomService
import com.nadosunbae_android.data.api.community.CommunityService
import com.nadosunbae_android.data.api.like.LikeService
import com.nadosunbae_android.data.api.main.MainService
import com.nadosunbae_android.data.api.mypage.MyPageService
import com.nadosunbae_android.data.api.notification.NotificationService
import com.nadosunbae_android.data.api.review.ReviewService
import com.nadosunbae_android.data.api.sign.SignService
import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.community.CommunityDataSource
import com.nadosunbae_android.data.datasource.remote.community.CommunityDataSourceImpl
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
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Reusable
    fun classRoomDataSource(
        classRoomService: ClassRoomService
    ) : ClassRoomDataSource{
        return ClassRoomDataSourceImpl(classRoomService)
    }

    @Provides
    @Reusable
    fun likeDataSource(
        likeService: LikeService
    ) : LikeDataSource{
        return LikeDataSourceImpl(likeService)
    }

    @Provides
    @Reusable
    fun mainDataSource(
        mainService: MainService
    ) : MainDataSource{
        return MainDataSourceImpl(mainService)
    }

    @Provides
    @Reusable
    fun myPageDataSource(
        myPageService: MyPageService
    ) : MyPageDataSource{
        return MyPageDataSourceImpl(myPageService)
    }

    @Provides
    @Reusable
    fun notificationDataSource(
        notificationService: NotificationService
    ) : NotificationDataSource{
        return NotificationDataSourceImpl(notificationService)
    }

    @Provides
    @Reusable
    fun reviewDataSource(
        reviewService: ReviewService
    ) : ReviewDataSource{
        return ReviewDataSourceImpl(reviewService)
    }

    @Provides
    @Reusable
    fun signDataSource(
        signService: SignService
    ) : SignDataSource{
        return SignDataSourceImpl(signService)
    }

    @Provides
    @Reusable
    fun communityDataSource(
        communityService: CommunityService
    ) : CommunityDataSource{
        return CommunityDataSourceImpl(communityService)
    }

}