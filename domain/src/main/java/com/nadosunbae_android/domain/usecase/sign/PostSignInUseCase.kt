package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.data.model.sign.SignInData
import com.nadosunbae_android.data.model.sign.SignInItem
import com.nadosunbae_android.domain.repository.sign.SignRepository

class PostSignInUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(signInData: SignInData) : SignInItem{
        return repository.postSignIn(signInData)
    }
}