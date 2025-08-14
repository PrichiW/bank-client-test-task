package com.testtask.bankclient.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.testtask.bankclient.ui.common.IconOutlinedButton
import com.testtask.bankclient.ui.common.SectionTitle
import com.testtask.bankclient.ui.common.SettingCard
import com.testtask.bankclient.ui.common.SpacerLarge
import com.testtask.bankclient.ui.common.SpacerSmall
import com.testtask.bankclient.ui.common.SpacerWidth
import com.testtask.bankclient.ui.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF2E2B3F), Color(0xFF1C1B29))
                )
            ),
        containerColor = Color.Transparent
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            IconOutlinedButton(
                text = "Назад",
                onClick = { navController.popBackStack() }
            )

            SpacerLarge()

            Text(
                text = state.firstName,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = state.lastName,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                SpacerWidth(8.dp)
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Изменить",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            SpacerSmall()
            Text(
                text = state.phoneNumber,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            SpacerLarge()

            SectionTitle("Мои покупки")
            SpacerLarge()
            SettingCard(
                title = "История покупок",
                onClick = { navController.navigate(Screen.Purchases.route) }
            )

            SpacerLarge()
            SectionTitle("Настройки")
            SpacerSmall()

            SettingCard(
                title = "E-mail",
                rightContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(state.email, color = Color.White)
                            if (!state.emailVerified) {
                                Text(
                                    "Необходимо подтвердить",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        SpacerWidth(8.dp)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )

            SpacerLarge()

            SettingCard(
                title = "Вход по биометрии",
                rightContent = {
                    var checked by remember { mutableStateOf(false) }
                    Switch(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            )

            SpacerLarge()
            SettingCard(title = "Сменить 4-х значный код")
            SpacerLarge()
            SettingCard(
                title = "Регистрация для клиентов банка",
                onClick = { navController.navigate(Screen.Registration.route) }
            )

            SpacerLarge()
            SettingCard(
                title = "Язык",
                rightContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("русский", color = Color.White)
                        SpacerWidth(8.dp)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    }
}