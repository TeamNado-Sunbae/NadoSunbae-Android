package com.nadosunbae_android.di

import com.nadosunbae_android.usecase.classroom.GetClassRoomMainDataUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single {GetClassRoomMainDataUseCase(get())}
}