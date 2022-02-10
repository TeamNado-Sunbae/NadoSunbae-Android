package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.model.sign.NicknameDuplicationCheck
import com.nadosunbae_android.model.sign.NicknameDuplicationData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.repository.sign.SignRepository

class PostSignNicknameUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(nicknameDuplicationData: NicknameDuplicationData) : NicknameDuplicationCheck{
        return repository.postSignNickname(nicknameDuplicationData)
    }
}