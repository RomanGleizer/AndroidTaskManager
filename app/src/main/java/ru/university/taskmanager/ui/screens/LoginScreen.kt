package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ru.university.taskmanager.viewmodel.LoginUiEvent
import ru.university.taskmanager.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.Success -> onLoginSuccess()
                is LoginUiEvent.NavigateToSignUp -> onSignUpClick()
            }
        }
    }

    val backgroundColor = Color(0xFF1E1E1E)
    val accentYellow = Color(0xFFFFC107)
    val textColor = Color(0xFFE0E0E0)

    Scaffold(containerColor = backgroundColor) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email", color = accentYellow) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = textColor)
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Пароль", color = accentYellow) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = textColor)
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = viewModel::onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = accentYellow)
            ) {
                Text("Войти", color = backgroundColor)
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = viewModel::onSignUpClick) {
                Text("Регистрация", color = accentYellow)
            }
            uiState.error?.let { errorMsg ->
                Spacer(Modifier.height(16.dp))
                Text(text = errorMsg, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
