package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.university.taskmanager.viewmodel.TaskDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    viewModel: TaskDetailViewModel,
    projectId: String,
    taskId: String,
    onEdit: (String) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val backgroundColor = Color(0xFF1E1E1E)
    val accentYellow = Color(0xFFFFC107)
    val textColor = Color(0xFFE0E0E0)

    var expanded by remember { mutableStateOf(false) }
    val statusList = viewModel.getAvailableStatuses()
    val currentStatus = uiState.task?.status ?: statusList.first()

    LaunchedEffect(taskId) {
        viewModel.loadTask(projectId, taskId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Задача", color = accentYellow) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                onClick = { onEdit(taskId) },
                containerColor = accentYellow,
                contentColor = Color.Black
            ) {
                Text("Изменить")
            }
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        uiState.task?.let { task ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    "Название:",
                    style = MaterialTheme.typography.titleMedium,
                    color = accentYellow
                )
                Text(
                    task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(
                    "Описание:",
                    style = MaterialTheme.typography.titleMedium,
                    color = accentYellow
                )
                Text(
                    task.description ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(
                    "Исполнитель:",
                    style = MaterialTheme.typography.titleMedium,
                    color = accentYellow
                )
                Text(
                    task.assignedTo,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text("Статус:", style = MaterialTheme.typography.titleMedium, color = accentYellow)
                Box {
                    Text(
                        text = currentStatus.name,
                        color = accentYellow,
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(8.dp)
                            .background(Color(0xFF2A2A2A), MaterialTheme.shapes.small)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color(0xFF2A2A2A))
                    ) {
                        statusList.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.name, color = accentYellow) },
                                onClick = {
                                    viewModel.updateStatus(projectId, taskId, status)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
