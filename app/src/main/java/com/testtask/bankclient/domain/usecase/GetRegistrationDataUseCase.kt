package com.testtask.bankclient.domain.usecase

import com.testtask.bankclient.domain.repository.RegistrationRepository

class GetRegistrationDataUseCase(private val repository: RegistrationRepository) {
    operator fun invoke() = repository.registrationData
}