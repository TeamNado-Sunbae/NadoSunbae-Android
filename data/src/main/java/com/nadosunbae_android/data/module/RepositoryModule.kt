package com.nadosunbae_android.data.module

import com.nadosunbae_android.data.repositoryimpl.app.AppRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.classroom.ClassRoomRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.comment.CommentRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.favorites.FavoritesRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.home.HomeRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.like.LikeRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.main.MainRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.major.MajorRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.mypage.MyPageRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.notification.NotificationRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.post.PostRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.report.ReportRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.review.ReviewRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.sign.SignRepositoryImpl
import com.nadosunbae_android.data.repositoryimpl.user.UserRepositoryImpl
import com.nadosunbae_android.domain.repository.app.AppRepository
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.domain.repository.comment.CommentRepository
import com.nadosunbae_android.domain.repository.favorites.FavoritesRepository
import com.nadosunbae_android.domain.repository.home.HomeRepository
import com.nadosunbae_android.domain.repository.like.LikeRepository
import com.nadosunbae_android.domain.repository.main.MainRepository
import com.nadosunbae_android.domain.repository.major.MajorRepository
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import com.nadosunbae_android.domain.repository.notification.NotificationRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import com.nadosunbae_android.domain.repository.report.ReportRepository
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import com.nadosunbae_android.domain.repository.sign.SignRepository
import com.nadosunbae_android.domain.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Reusable
    @Provides
    fun provideClassRoomRepository(
        repository: ClassRoomRepositoryImpl
    ): ClassRoomRepository = repository

    @Reusable
    @Provides
    fun provideLikeRepository(
        repository: LikeRepositoryImpl
    ): LikeRepository = repository

    @Reusable
    @Provides
    fun provideMainRepository(
        repository: MainRepositoryImpl
    ): MainRepository = repository

    @Reusable
    @Provides
    fun provideMyPageRepository(
        repository: MyPageRepositoryImpl
    ): MyPageRepository = repository

    @Reusable
    @Provides
    fun provideSignRepository(
        repository: SignRepositoryImpl
    ): SignRepository = repository

    @Reusable
    @Provides
    fun provideNotificationRepository(
        repository: NotificationRepositoryImpl
    ): NotificationRepository = repository

    @Reusable
    @Provides
    fun provideReviewRepository(
        repository: ReviewRepositoryImpl
    ): ReviewRepository = repository

    @Reusable
    @Provides
    fun provideHomeRepository(
        repository: HomeRepositoryImpl
    ): HomeRepository = repository

    @Reusable
    @Provides
    fun providePostRepository(
        repository: PostRepositoryImpl
    ): PostRepository = repository

    @Reusable
    @Provides
    fun provideMajorRepository(
        repository: MajorRepositoryImpl
    ): MajorRepository = repository

    @Reusable
    @Provides
    fun provideUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository = repository

    @Reusable
    @Provides
    fun provideCommentRepository(
        repository: CommentRepositoryImpl
    ): CommentRepository = repository

    @Reusable
    @Provides
    fun provideAppRepository(
        repository: AppRepositoryImpl
    ): AppRepository = repository

    @Reusable
    @Provides
    fun provideReportRepository(
        repository: ReportRepositoryImpl
    ): ReportRepository = repository

    @Reusable
    @Provides
    fun provideFavoritesRepository(
        repository: FavoritesRepositoryImpl
    ): FavoritesRepository = repository
}
