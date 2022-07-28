package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.sign.SignInItem
import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class PostSignInUseCase @Inject constructor(private val repository : SignRepository) {
    suspend operator fun invoke(signInItem: SignInItem) : SignInData{
        return repository.postSignIn(signInItem)
    }
}