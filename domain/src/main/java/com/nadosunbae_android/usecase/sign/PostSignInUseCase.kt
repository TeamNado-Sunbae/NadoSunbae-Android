package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class PostSignInUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(signInData: SignInData) : SignInData{
        return repository.postSignIn(signInData)
    }
}