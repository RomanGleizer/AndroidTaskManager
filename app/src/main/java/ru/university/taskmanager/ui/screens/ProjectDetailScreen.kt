package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.university.taskmanager.viewmodel.ProjectDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    projectId: String,
    viewModel: ProjectDetailViewModel,
    onTaskClick: (String) -> Unit,
    onAddTask: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val users by viewModel.users.collectAsState()

    LaunchedEffect(projectId) {
        viewModel.loadAll(projectId)
        viewModel.loadProjectUsers(projectId)
    }

    val backgroundColor = Color(0xFF1E1E1E)
    val accentYellow = Color(0xFFFFC107)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.project?.title ?: "", color = accentYellow) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = accentYellow
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = accentYellow,
                    navigationIconContentColor = accentYellow
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTask,
                containerColor = accentYellow,
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить задачу")
            }
        },
        containerColor = backgroundColor,
        content = { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                uiState.project?.let { project ->
                    Text(
                        text = project.description ?: "Описание отсутствует",
                        style = MaterialTheme.typography.bodyMedium,
                        color = accentYellow,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        "Участники",
                        style = MaterialTheme.typography.titleMedium.copy(color = accentYellow)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF867777)),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) {
                            items(users) { user ->
                                ListItem(
                                    headlineContent = {
                                        Text(
                                            text = user.name,
                                            color = Color(0xFF151010),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    },
                                    supportingContent = {
                                        Text(
                                            text = "Invite Id: ${user.inviteId ?: "нет"}",
                                            color = Color(0xFF181313),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Задачи",
                        style = MaterialTheme.typography.titleMedium.copy(color = accentYellow)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                            items(uiState.tasks) { task ->
                                ListItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onTaskClick(task.id) },
                                    headlineContent = {
                                        Text(task.title, color = accentYellow)
                                    },
                                    supportingContent = {
                                        Text(
                                            task.status.name,
                                            color = accentYellow.copy(alpha = 0.8f)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                uiState.error?.let { errorMsg ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Ошибка: $errorMsg", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    )
}
