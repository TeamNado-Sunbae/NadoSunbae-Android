package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.data.model.sign.NicknameDuplicationCheck
import com.nadosunbae_android.data.model.sign.NicknameDuplicationData
import com.nadosunbae_android.domain.repository.sign.SignRepository

class PostSignNicknameUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(nicknameDuplicationData: NicknameDuplicationData) : NicknameDuplicationCheck{
        return repository.postSignNickname(nicknameDuplicationData)
    }
}