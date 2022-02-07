package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.sign.SignInData
import com.nadosunbae_android.model.sign.SignInItem
import com.nadosunbae_android.repository.sign.SignRepository

class PostSignInUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(signInData: SignInData) : SignInItem{
        return repository.postSignIn(signInData)
    }
}