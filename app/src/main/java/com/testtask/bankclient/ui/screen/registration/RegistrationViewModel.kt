package com.testtask.bankclient.ui.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.bankclient.domain.model.RegistrationData
import com.testtask.bankclient.domain.usecase.SaveRegistrationDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FieldType {
    CODE, FIRST_NAME, LAST_NAME, PARTICIPANT_NUMBER
}

data class FieldState(
    val value: String = "",
    val isTouched: Boolean = false,
    val isError: Boolean = false
)

data class RegistrationUiState(
    val fields: Map<FieldType, FieldState> = mapOf(),
    val isFormValid: Boolean = false
)

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val saveRegistrationData: SaveRegistrationDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(getInitialState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<Unit>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val latinLettersRegex = Regex("^[a-zA-Z]+$")

    fun onValueChanged(fieldType: FieldType, value: String) {
        _uiState.update { currentState ->
            val updatedFields = currentState.fields.toMutableMap()
            updatedFields[fieldType] = updatedFields[fieldType]!!.copy(value = value)
            validate(currentState.copy(fields = updatedFields))
        }
    }

    fun onFieldTouched(fieldType: FieldType) {
        _uiState.update { currentState ->
            val updatedFields = currentState.fields.toMutableMap()
            if (updatedFields[fieldType]?.isTouched == false) {
                updatedFields[fieldType] = updatedFields[fieldType]!!.copy(isTouched = true)
                validate(currentState.copy(fields = updatedFields))
            } else {
                currentState
            }
        }
    }

    private fun validate(state: RegistrationUiState): RegistrationUiState {
        val isCodeValid = state.fields.getValue(FieldType.CODE).value.isNotBlank()
        val isFirstNameValid = state.fields.getValue(FieldType.FIRST_NAME).value.matches(latinLettersRegex)
        val isLastNameValid = state.fields.getValue(FieldType.LAST_NAME).value.matches(latinLettersRegex)
        val isParticipantNumberValid = state.fields.getValue(FieldType.PARTICIPANT_NUMBER).value.let {
            it.length == PARTICIPANT_NUMBER_LENGTH && it.all(Char::isDigit)
        }

        val isFormValid = isCodeValid && isFirstNameValid && isLastNameValid && isParticipantNumberValid

        val updatedFields = state.fields.mapValues { (type, fieldState) ->
            val isValid = when (type) {
                FieldType.CODE -> isCodeValid
                FieldType.FIRST_NAME -> isFirstNameValid
                FieldType.LAST_NAME -> isLastNameValid
                FieldType.PARTICIPANT_NUMBER -> isParticipantNumberValid
            }
            fieldState.copy(isError = fieldState.isTouched && !isValid)
        }

        return state.copy(fields = updatedFields, isFormValid = isFormValid)
    }

    fun onSubmit() {
        val touchedState = _uiState.value.let { currentState ->
            val updatedFields = currentState.fields.mapValues { it.value.copy(isTouched = true) }
            currentState.copy(fields = updatedFields)
        }

        val finalState = validate(touchedState)
        _uiState.value = finalState

        if (!finalState.isFormValid) return

        viewModelScope.launch {
            saveRegistrationData(
                RegistrationData(
                    code = finalState.fields.getValue(FieldType.CODE).value,
                    firstName = finalState.fields.getValue(FieldType.FIRST_NAME).value,
                    lastName = finalState.fields.getValue(FieldType.LAST_NAME).value,
                    participantNumber = finalState.fields.getValue(FieldType.PARTICIPANT_NUMBER).value
                )
            )
            _navigationEvent.send(Unit)
        }
    }

    private fun getInitialState(): RegistrationUiState {
        val fields = FieldType.entries.associateWith { FieldState() }
        return RegistrationUiState(fields = fields)
    }

    companion object {
        private const val PARTICIPANT_NUMBER_LENGTH = 16
    }
}