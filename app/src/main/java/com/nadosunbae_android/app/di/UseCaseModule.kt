package com.nadosunbae_android.app.di

import com.nadosunbae_android.domain.usecase.classroom.*
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import com.nadosunbae_android.domain.usecase.main.GetMajorListDataUseCase
import com.nadosunbae_android.domain.usecase.mypage.*
import com.nadosunbae_android.domain.usecase.notification.DeleteNotificationUseCase
import com.nadosunbae_android.domain.usecase.notification.GetNotificationListDataUseCase
import com.nadosunbae_android.domain.usecase.notification.ReadNotificationUseCase
import com.nadosunbae_android.domain.usecase.review.*
import com.nadosunbae_android.domain.usecase.sign.GetSecondDepartmentUseCase
import org.koin.dsl.module

val useCaseModule = module{

    //classRoom
    single {GetClassRoomMainDataUseCase(get())}
    single {GetInformationDetailUseCase(get())}
    single {GetQuestionDetailDataUseCase(get())}
    single {GetQuestionSeniorListDataUseCase(get())}
    single {GetSeniorPersonalDataUseCase(get())}
    single {PostClassRoomWriteUseCase(get())}
    single {PostQuestionCommentWriteUseCase(get())}
    single {GetSeniorDataUseCase(get())}

    //Notification
    single {DeleteNotificationUseCase(get())}
    single {GetNotificationListDataUseCase(get())}
    single {ReadNotificationUseCase(get())}

    //sign
    single {GetFirstDepartmentUseCase(get())}
    single {PostSignEmailUseCase(get())}
    single {PostSignInUseCase(get())}
    single {PostSignNicknameUseCase(get())}
    single {PostSignUpUseCase(get())}
    single { GetSecondDepartmentUseCase(get()) }
    // main
    single {GetMajorListDataUseCase(get())}

    // review
    single {GetReviewListDataUseCase(get())}
    single {GetReviewDetailDataUseCase(get())}
    single {PostReviewDataUseCase(get())}
    single {PutReviewDataUseCase(get())}
    single {DeleteReviewDataUseCase(get())}
    single {GetBackgroundImageListDataUseCase(get())}
    single {GetMajorInfoDataUseCase(get())}
    single { PostLikeDataUseCase(get()) }

    //mypage
    single {GetMyPageMyInfoUseCase(get())}
    single {GetMyPageQuestionUseCase(get())}
    single {PutMyPageModifyUseCase(get())}
    single {GetMyPagePostUseCase(get())}
    single { GetMyPageReplyUseCase(get()) }
    single {GetMyPageVersionUseCase(get())}
    single {PostMyPageLogOutUseCase(get())}


}