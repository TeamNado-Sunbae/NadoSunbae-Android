package com.nadosunbae_android.app.di

import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.data.datasource.remote.like.LikeDataSource
import com.nadosunbae_android.data.datasource.remote.main.MainDataSource
import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.data.datasource.remote.post.PostDataSource
import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.data.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.data.repositoryimpl.classroom.ClassRoomRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.like.LikeRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.main.MainRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.mypage.MyPageRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.notification.NotificationRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.post.PostRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.review.ReviewRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.sign.SignRepositoryImpl
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.domain.repository.like.LikeRepository
import com.nadosunbae_android.domain.repository.main.MainRepository
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import com.nadosunbae_android.domain.repository.notification.NotificationRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import com.nadosunbae_android.domain.repository.sign.SignRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Reusable
    @Provides
    fun classRoomRepository(
        classRoomDataSource : ClassRoomDataSource
    ) : ClassRoomRepository{
        return ClassRoomRepositoryImpl(classRoomDataSource)
    }
    @Reusable
    @Provides
    fun likeRepository(
        likeDataSource: LikeDataSource
    ) : LikeRepository{
        return LikeRepositoryImpl(likeDataSource)
    }
    @Reusable
    @Provides
    fun mainRepository(
        mainDataSource: MainDataSource
    ) : MainRepository{
        return MainRepositoryImpl(mainDataSource)
    }

    @Reusable
    @Provides
    fun myPageRepository(
        myPageDataSource: MyPageDataSource
    ) : MyPageRepository{
        return MyPageRepositoryImpl(myPageDataSource)
    }

    @Reusable
    @Provides
    fun signRepository(
        signDataSource : SignDataSource
    ) : SignRepository{
        return SignRepositoryImpl(signDataSource)
    }

    @Reusable
    @Provides
    fun notificationRepository(
        notificationDataSource: NotificationDataSource
    ) : NotificationRepository{
        return NotificationRepositoryImpl(notificationDataSource)
    }

    @Reusable
    @Provides
    fun reviewRepository(
        reviewDataSource: ReviewDataSource
    ) : ReviewRepository{
        return ReviewRepositoryImpl(reviewDataSource)
    }

    @Reusable
    @Provides
    fun postRepository(
        postDataSource: PostDataSource
    ) : PostRepository{
        return PostRepositoryImpl(postDataSource)
    }
}
