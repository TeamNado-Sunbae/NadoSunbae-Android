package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.model.sign.EmailDuplicationCheck
import com.nadosunbae_android.model.sign.EmailDuplicationData
import com.nadosunbae_android.model.sign.NicknameDuplicationData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.repository.sign.SignRepository

class PostSignEmailUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(emailDuplicationData: EmailDuplicationData) : EmailDuplicationCheck{
        return repository.postSignEmail(emailDuplicationData)
    }
}