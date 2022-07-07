package com.nadosunbae_android.domain.usecase.sign

import com.nadosunbae_android.domain.model.sign.CertificationEmailData
import com.nadosunbae_android.domain.model.sign.CertificationEmailItem
import com.nadosunbae_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class PostCertificationEmailUseCase @Inject constructor(private val repository : SignRepository) {
    suspend operator fun invoke(certificationEmailData: CertificationEmailData) : CertificationEmailItem {
        return repository.postCertificationEmail(certificationEmailData)
    }
}