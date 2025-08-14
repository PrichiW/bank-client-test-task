package com.testtask.bankclient.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.bankclient.domain.usecase.GetRegistrationDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(
    val firstName: String = "Имя",
    val lastName: String = "Фамилия",
    val phoneNumber: String = "+7 924 301 43 34",
    val email: String = "kursantik341@gmail.com",
    val emailVerified: Boolean = false,
    val isBiometricEnabled: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    getRegistrationData: GetRegistrationDataUseCase,
    private val mapper: RegistrationDataToHomeUiStateMapper
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> =
        getRegistrationData()
            .map(mapper::map)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())
}