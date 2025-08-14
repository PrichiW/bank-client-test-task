package com.testtask.bankclient.data.repository

import com.testtask.bankclient.data.datasource.RegistrationInMemoryCache
import com.testtask.bankclient.domain.model.RegistrationData
import com.testtask.bankclient.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val cache: RegistrationInMemoryCache
) : RegistrationRepository {

    override val registrationData: Flow<RegistrationData>
        get() = cache.data

    override suspend fun saveRegistrationData(data: RegistrationData) {
        cache.save(data)
    }
}