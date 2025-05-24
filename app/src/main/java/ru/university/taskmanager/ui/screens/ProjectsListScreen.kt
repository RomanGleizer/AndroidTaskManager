package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.university.taskmanager.viewmodel.ProjectsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsListScreen(
    viewModel: ProjectsListViewModel,
    onProjectClick: (String) -> Unit,
    onAddProject: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val backgroundColor = Color(0xFF1E1E1E)
    val accentYellow = Color(0xFFFFC107)
    val textSecondary = accentYellow.copy(alpha = 0.6f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Проекты", color = accentYellow) },
                actions = {
                    IconButton(onClick = onAddProject) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Новый проект",
                            tint = accentYellow
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = accentYellow,
                    actionIconContentColor = accentYellow
                )
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(uiState.projects) { project ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onProjectClick(project.id) },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF212121)),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = project.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = accentYellow
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = project.description ?: "Без описания",
                            style = MaterialTheme.typography.bodyMedium,
                            color = textSecondary,
                            maxLines = 2,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
