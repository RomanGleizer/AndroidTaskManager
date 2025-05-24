package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.university.taskmanager.viewmodel.TaskEditUiEvent
import ru.university.taskmanager.viewmodel.TaskEditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    viewModel: TaskEditViewModel,
    projectId: String,
    taskId: String?,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val backgroundColor = Color(0xFF1E1E1E)
    val accentYellow = Color(0xFFFFC107)
    val textColor = Color(0xFFE0E0E0)

    LaunchedEffect(Unit) {
        viewModel.initialize(taskId, projectId)
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is TaskEditUiEvent.NavigateBack -> {
                    if (!taskId.isNullOrBlank()) {
                        navController.navigate("task_detail/$projectId/$taskId") {
                            popUpTo("task_edit/$taskId/$projectId") { inclusive = true }
                        }
                    } else {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (taskId == null) "Добавить задачу" else "Изменить задачу",
                        color = accentYellow
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = accentYellow
                )
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Заголовок", color = accentYellow) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = textColor)
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Описание", color = accentYellow) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                textStyle = LocalTextStyle.current.copy(color = textColor)
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.assignedToId,
                onValueChange = viewModel::onAssignToChange,
                label = { Text("Исполнитель (ID)", color = accentYellow) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = textColor)
            )
            Spacer(Modifier.height(16.dp))
            Row {
                Button(
                    onClick = viewModel::onSave,
                    colors = ButtonDefaults.buttonColors(containerColor = accentYellow)
                ) {
                    Text("Сохранить", color = backgroundColor)
                }
                Spacer(Modifier.width(8.dp))
                OutlinedButton(onClick = { navController.popBackStack() }) {
                    Text("Отмена", color = accentYellow)
                }
            }
        }
    }
}
