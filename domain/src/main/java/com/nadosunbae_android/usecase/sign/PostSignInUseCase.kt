package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.model.sign.SignInData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.repository.sign.SignRepository

class PostSignInUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(signInData: SignInData) : SignInData{
        return repository.postSignIn(signInData)
    }
}