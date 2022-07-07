package com.nadosunbae_android.domain.usecase.sign

import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class PostRenewalTokenUseCase @Inject constructor(private val repository: SignRepository) {
    suspend operator fun invoke(): SignInData {
        return repository.postRenewalToken()
    }
}