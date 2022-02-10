package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.sign.SignInData
import com.nadosunbae_android.model.sign.SignUpData
import com.nadosunbae_android.model.sign.SignUpItem
import com.nadosunbae_android.repository.sign.SignRepository

class PostSignUpUseCase(val repository : SignRepository) {
    suspend operator fun invoke(signUpData: SignUpData) : SignUpItem{
        return repository.postSignUp(signUpData)
    }
}