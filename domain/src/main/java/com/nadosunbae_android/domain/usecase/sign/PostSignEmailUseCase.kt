package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.sign.EmailDuplicationCheck
import com.nadosunbae_android.domain.model.sign.EmailDuplicationData
import com.nadosunbae_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class PostSignEmailUseCase @Inject constructor(private val repository : SignRepository) {
    suspend operator fun invoke(emailDuplicationData: EmailDuplicationData) : EmailDuplicationCheck{
        return repository.postSignEmail(emailDuplicationData)
    }
}