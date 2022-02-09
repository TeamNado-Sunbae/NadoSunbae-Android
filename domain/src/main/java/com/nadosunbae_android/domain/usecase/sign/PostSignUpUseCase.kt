package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.data.model.sign.SignUpData
import com.nadosunbae_android.data.model.sign.SignUpItem
import com.nadosunbae_android.domain.repository.sign.SignRepository

class PostSignUpUseCase(val repository : SignRepository) {
    suspend operator fun invoke(signUpData: SignUpData) : SignUpItem{
        return repository.postSignUp(signUpData)
    }
}