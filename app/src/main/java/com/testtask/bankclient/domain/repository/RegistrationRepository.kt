package com.testtask.bankclient.domain.repository

import com.testtask.bankclient.domain.model.RegistrationData
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    val registrationData: Flow<RegistrationData>
    suspend fun saveRegistrationData(data: RegistrationData)
}