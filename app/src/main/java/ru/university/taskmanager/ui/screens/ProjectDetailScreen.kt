package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.project?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEB3B),
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTask,
                containerColor = Color(0xFFFFEB3B),
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить задачу")
            }
        },
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
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Участники", style = MaterialTheme.typography.titleMedium)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        LazyColumn {
                            items(users) { user ->
                                Text(
                                    text = "${user.name} (Invite Id: ${user.inviteId})",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Задачи", style = MaterialTheme.typography.titleMedium)
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                    ) {
                        LazyColumn {
                            items(uiState.tasks) { task ->
                                ListItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onTaskClick(task.id) },
                                    headlineContent = { Text(task.title) },
                                    supportingContent = { Text(task.status.name) }
                                )
                            }
                        }
                    }
                }

                uiState.error?.let { errorMsg ->
                    Text(text = "Ошибка: $errorMsg", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    )
}
