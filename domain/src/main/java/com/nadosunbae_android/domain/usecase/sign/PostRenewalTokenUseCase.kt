package com.nadosunbae_android.domain.usecase.sign

import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.repository.sign.SignRepository

class PostRenewalTokenUseCase(private val repository: SignRepository) {
    suspend operator fun invoke(refreshToken: String): SignInData {
        return repository.postRenewalToken(refreshToken)
    }
}