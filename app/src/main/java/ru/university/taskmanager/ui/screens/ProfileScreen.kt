package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.university.taskmanager.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val userState = viewModel.user.collectAsState()
    val user = userState.value
    val clipboard = LocalClipboardManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFFFEB3B),
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            if (user == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val initials =
                        user.name.split(' ').mapNotNull { it.firstOrNull()?.toString() }.take(2)
                            .joinToString("")
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .background(Color(0xFFFFEB3B), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    InfoRow(
                        label = "Id для приглашения в проект:",
                        value = user.inviteId ?: "Не указано"
                    ) {
                        clipboard.setText(
                            androidx.compose.ui.text.AnnotatedString(
                                user.inviteId ?: ""
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(label = "Имя:", value = user.name)

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow(label = "Email:", value = user.email)

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = { viewModel.logout() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEB3B))
                    ) {
                        Text("Выйти из аккаунта", color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, onValueClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onValueClick != null) Modifier.clickable { onValueClick() }
                else Modifier
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (onValueClick != null) Color(0xFF1976D2) else Color.Black
        )
    }
}
