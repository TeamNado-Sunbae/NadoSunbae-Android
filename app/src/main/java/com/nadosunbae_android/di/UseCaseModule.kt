package com.nadosunbae_android.di

import com.nadosunbae_android.usecase.classroom.*
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
}