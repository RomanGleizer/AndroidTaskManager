package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.university.taskmanager.ui.components.AddMemberDialog
import ru.university.taskmanager.viewmodel.ProjectDetailViewModel
import ru.university.domain.model.Task
import ru.university.domain.model.Project

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    viewModel: ProjectDetailViewModel,
    projectId: String,
    onTaskClick: (String) -> Unit,
    onAddTask: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddMember by remember { mutableStateOf(false) }

    LaunchedEffect(projectId) {
        viewModel.loadAll(projectId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали проекта") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddMember = true }) {
                        Icon(Icons.Default.Face, contentDescription = "Добавить участника")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Новая задача")
            }
        }
    ) { padding ->
        if (showAddMember) {
            AddMemberDialog(
                onAdd = { userId ->
                    viewModel.onAddMember(projectId, userId)
                    showAddMember = false
                },
                onDismiss = { showAddMember = false }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            uiState.project?.let { project: Project ->
                item {
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = project.description.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Участники:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(4.dp))
                }

                items(project.members) { memberId: String ->
                    Text(
                        text = "- $memberId",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 4.dp)
                    )
                }

                item { Spacer(Modifier.height(24.dp)) }
            }

            items(uiState.tasks) { task: Task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onTaskClick(task.id) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Исполнитель: ${task.assignedTo}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
