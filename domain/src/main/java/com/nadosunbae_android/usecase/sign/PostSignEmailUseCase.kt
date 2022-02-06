package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.repository.sign.SignRepository

class PostSignEmailUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(signEmail: SignEmail) : SignEmail{
        return repository.postSignEmail(signEmail)
    }
}