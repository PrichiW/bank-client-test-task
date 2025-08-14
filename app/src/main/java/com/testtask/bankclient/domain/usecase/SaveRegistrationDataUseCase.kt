package com.testtask.bankclient.domain.usecase

import com.testtask.bankclient.domain.model.RegistrationData
import com.testtask.bankclient.domain.repository.RegistrationRepository

class SaveRegistrationDataUseCase(private val repository: RegistrationRepository) {
    suspend operator fun invoke(data: RegistrationData) {
        repository.saveRegistrationData(data)
    }
}