package com.nadosunbae_android.data.module

import com.nadosunbae_android.data.datasource.remote.app.AppDataSource
import com.nadosunbae_android.data.datasource.remote.app.AppDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.comment.CommentDataSource
import com.nadosunbae_android.data.datasource.remote.comment.CommentDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.favorites.FavoritesDataSource
import com.nadosunbae_android.data.datasource.remote.favorites.FavoritesDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.home.HomeDataSource
import com.nadosunbae_android.data.datasource.remote.home.HomeDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.like.LikeDataSource
import com.nadosunbae_android.data.datasource.remote.like.LikeDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.main.MainDataSource
import com.nadosunbae_android.data.datasource.remote.main.MainDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.major.MajorDataSource
import com.nadosunbae_android.data.datasource.remote.major.MajorDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.post.PostDataSource
import com.nadosunbae_android.data.datasource.remote.post.PostDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.report.ReportDataSource
import com.nadosunbae_android.data.datasource.remote.report.ReportDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.data.datasource.remote.sign.SignDataSourceImpl
import com.nadosunbae_android.data.datasource.remote.user.UserDataSource
import com.nadosunbae_android.data.datasource.remote.user.UserDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Reusable
    fun provideClassRoomDataSource(
        dataSource: ClassRoomDataSourceImpl
    ): ClassRoomDataSource = dataSource

    @Provides
    @Reusable
    fun provideLikeDataSource(
        dataSource: LikeDataSourceImpl
    ): LikeDataSource = dataSource

    @Provides
    @Reusable
    fun provideMainDataSource(
        dataSource: MainDataSourceImpl
    ): MainDataSource = dataSource

    @Provides
    @Reusable
    fun provideMyPageDataSource(
        dataSource: MyPageDataSourceImpl
    ): MyPageDataSource = dataSource

    @Provides
    @Reusable
    fun provideNotificationDataSource(
        dataSource: NotificationDataSourceImpl
    ): NotificationDataSource = dataSource

    @Provides
    @Reusable
    fun provideReviewDataSource(
        dataSource: ReviewDataSourceImpl
    ): ReviewDataSource = dataSource

    @Provides
    @Reusable
    fun provideSignDataSource(
        dataSource: SignDataSourceImpl
    ): SignDataSource = dataSource

    @Provides
    @Reusable
    fun provideHomeDataSource(
        dataSource: HomeDataSourceImpl
    ): HomeDataSource = dataSource


    @Provides
    @Reusable
    fun providePostDataSource(
        dataSource: PostDataSourceImpl
    ): PostDataSource = dataSource

    @Provides
    @Reusable
    fun provideMajorDataSource(
        dataSource: MajorDataSourceImpl
    ): MajorDataSource = dataSource

    @Provides
    @Reusable
    fun provideUserDataSource(
        dataSource: UserDataSourceImpl
    ): UserDataSource = dataSource

    @Provides
    @Reusable
    fun provideCommentDataSource(
        dataSource: CommentDataSourceImpl
    ): CommentDataSource = dataSource

    @Provides
    @Reusable
    fun provideAppDataSource(
        dataSource: AppDataSourceImpl
    ): AppDataSource = dataSource

    @Provides
    @Reusable
    fun provideReportDataSource(
        dataSource: ReportDataSourceImpl
    ): ReportDataSource = dataSource

    @Provides
    @Reusable
    fun provideFavoritesDataSource(
        dataSource: FavoritesDataSourceImpl
    ): FavoritesDataSource = dataSource
}