package com.nadosunbae_android.di

import com.nadosunbae_android.usecase.classroom.*
import com.nadosunbae_android.usecase.notification.DeleteNotificationUseCase
import com.nadosunbae_android.usecase.notification.GetNotificationListDataUseCase
import com.nadosunbae_android.usecase.notification.ReadNotificationUseCase
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
}