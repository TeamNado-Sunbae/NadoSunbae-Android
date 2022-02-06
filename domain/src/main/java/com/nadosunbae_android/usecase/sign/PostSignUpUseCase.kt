package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.model.sign.SignUpData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.repository.sign.SignRepository

class PostSignUpUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(signUpData: SignUpData) : SignUpData{
        return repository.postSignUp(signUpData)
    }
}