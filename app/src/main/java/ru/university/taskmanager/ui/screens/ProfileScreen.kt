package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import ru.university.taskmanager.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val user by viewModel.user.collectAsState()

    val clipboard: ClipboardManager = LocalClipboardManager.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Профиль") }) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (user == null) {
                CircularProgressIndicator()
            } else {
                Text(
                    "ID: ${user!!.id}", modifier = Modifier
                        .clickable {
                            clipboard.setText(androidx.compose.ui.text.AnnotatedString(user!!.id))
                        }
                )
                Spacer(Modifier.height(8.dp))
                Text("Имя: ${user!!.name}")
                Spacer(Modifier.height(8.dp))
                Text("Email: ${user!!.email}")
                Spacer(Modifier.height(16.dp))
                Text("Нажмите на ID, чтобы скопировать", style = MaterialTheme.typography.bodySmall)

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.logout() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Выйти из аккаунта")
                }
            }
        }
    }
}
