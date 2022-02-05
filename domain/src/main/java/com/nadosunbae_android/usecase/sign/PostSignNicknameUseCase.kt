package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class PostSignNicknameUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(signNickname: SignNickname) : SignNickname{
        return repository.postSignNickname(signNickname)
    }
}