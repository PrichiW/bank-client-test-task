package com.testtask.bankclient.ui.screen.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.testtask.bankclient.ui.common.FourDigitCardTransformation
import com.testtask.bankclient.ui.common.IconOutlinedButton
import com.testtask.bankclient.ui.common.SpacerLarge

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect {
            navController.popBackStack()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2E2B3F), Color(0xFF1C1B29))
                )
            ),
        containerColor = Color.Transparent
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            IconOutlinedButton(
                text = "Назад",
                onClick = { navController.popBackStack() }
            )

            SpacerLarge()

            Text(
                text = "Регистрация для клиентов банка",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            SpacerLarge()

            RegistrationTextField(
                fieldState = state.fields.getValue(FieldType.PARTICIPANT_NUMBER),
                onValueChange = { newValue ->
                    val digitsOnly = newValue.filter { it.isDigit() }

                    val trimmedValue = digitsOnly.take(16)

                    viewModel.onValueChanged(FieldType.PARTICIPANT_NUMBER, trimmedValue)
                },
                placeholder = "Номер участника",
                supportingText = "Номер из 16 цифр, который вы получили от банка",
                keyboardType = KeyboardType.Number,
                onBlurred = { viewModel.onFieldTouched(FieldType.PARTICIPANT_NUMBER) },
                visualTransformation = FourDigitCardTransformation()
            )

            RegistrationTextField(
                fieldState = state.fields[FieldType.CODE]!!,
                onValueChange = { viewModel.onValueChanged(FieldType.CODE, it) },
                placeholder = "Код",
                supportingText = "Код, который вы получили от банка",
                onBlurred = { viewModel.onFieldTouched(FieldType.CODE) }
            )

            RegistrationTextField(
                fieldState = state.fields[FieldType.FIRST_NAME]!!,
                onValueChange = { viewModel.onValueChanged(FieldType.FIRST_NAME, it) },
                placeholder = "Имя",
                supportingText = "Имя (на латинице, как в загранпаспорте)",
                onBlurred = { viewModel.onFieldTouched(FieldType.FIRST_NAME) }
            )

            RegistrationTextField(
                fieldState = state.fields[FieldType.LAST_NAME]!!,
                onValueChange = { viewModel.onValueChanged(FieldType.LAST_NAME, it) },
                placeholder = "Фамилия",
                supportingText = "Фамилия (на латинице, как в загранпаспорте)",
                onBlurred = { viewModel.onFieldTouched(FieldType.LAST_NAME) }
            )

            Spacer(Modifier.weight(1f))

            val annotatedString = buildAnnotatedString {
                append("Нажимая на кнопку продолжить, вы соглашаетесь ")

                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("с условиями участия")
                }
            }

            Text(
                text = annotatedString,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable {}
            )

            Button(
                onClick = viewModel::onSubmit,
                enabled = state.isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
                )
            ) {
                Text("Продолжить", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun RegistrationTextField(
    fieldState: FieldState,
    onValueChange: (String) -> Unit,
    placeholder: String,
    supportingText: String,
    errorText: String = "Некорректные данные",
    keyboardType: KeyboardType = KeyboardType.Text,
    onBlurred: () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color(0xFF3C3756),
        unfocusedContainerColor = Color(0xFF3C3756),

        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = MaterialTheme.colorScheme.error,

        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        cursorColor = MaterialTheme.colorScheme.primary,

        focusedPlaceholderColor = Color.LightGray,
        unfocusedPlaceholderColor = Color.LightGray,
        errorLabelColor = MaterialTheme.colorScheme.error,

        focusedSupportingTextColor = Color.Gray,
        unfocusedSupportingTextColor = Color.Gray,
        errorSupportingTextColor = MaterialTheme.colorScheme.error
    )

    var hasBeenFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = fieldState.value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    hasBeenFocused = true
                }

                if (hasBeenFocused && !focusState.isFocused) {
                    onBlurred()
                }
            },

        placeholder = { Text(placeholder) },
        supportingText = {
            val textToShow = if (fieldState.isError) errorText else supportingText
            Text(text = textToShow)
        },
        isError = fieldState.isError,
        colors = textFieldColors,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        visualTransformation = visualTransformation
    )
}