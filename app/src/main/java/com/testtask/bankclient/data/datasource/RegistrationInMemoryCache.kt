package com.testtask.bankclient.data.datasource

import com.testtask.bankclient.domain.model.RegistrationData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegistrationInMemoryCache @Inject constructor() {
    private val _data = MutableStateFlow(RegistrationData())
    val data: Flow<RegistrationData> = _data.asStateFlow()

    fun save(registrationData: RegistrationData) {
        _data.value = registrationData
    }
}