package com.testtask.bankclient.ui.screen.home

import com.testtask.bankclient.domain.model.RegistrationData
import javax.inject.Inject

class RegistrationDataToHomeUiStateMapper @Inject constructor() {
    fun map(data: RegistrationData): HomeUiState {
        return if (data.firstName.isBlank() && data.lastName.isBlank()) {
            HomeUiState(firstName = "Имя", lastName = "Фамилия")
        } else {
            HomeUiState(firstName = data.firstName, lastName = data.lastName)
        }
    }
}