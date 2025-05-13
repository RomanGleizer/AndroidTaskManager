// app/src/main/java/ru/university/taskmanager/ui/screens/ProjectDetailScreen.kt
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

    LaunchedEffect(projectId) { viewModel.loadTasks(projectId) }

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
            items(uiState.tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onTaskClick(task.id) }
                ) {
                    Text(task.title, Modifier.padding(16.dp))
                }
            }
        }
    }
}
