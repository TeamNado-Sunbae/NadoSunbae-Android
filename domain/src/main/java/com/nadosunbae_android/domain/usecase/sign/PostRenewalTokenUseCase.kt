package com.nadosunbae_android.domain.usecase.sign

import com.nadosunbae_android.domain.model.sign.RenewalTokenData
import com.nadosunbae_android.domain.repository.sign.SignRepository

class PostRenewalTokenUseCase(val repository: SignRepository) {
    suspend operator fun invoke(refreshToken: String) : RenewalTokenData {
        return repository.postRenewalToken(refreshToken)
    }
}