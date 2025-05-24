package ru.university.taskmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.university.taskmanager.viewmodel.CreateTaskViewModel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    projectId: String,
    viewModel: CreateTaskViewModel,
    onTaskCreated: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var assignedTo by remember { mutableStateOf("") }

    var dueDate by remember { mutableStateOf<LocalDateTime?>(null) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                val time = dueDate?.toLocalTime() ?: LocalTime.MIDNIGHT
                dueDate = LocalDateTime.of(selectedDate, time)
                showDatePicker = false
                showTimePicker = true
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    if (showTimePicker) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val date = dueDate?.toLocalDate() ?: LocalDate.now()
                val selectedTime = LocalTime.of(hourOfDay, minute)
                dueDate = LocalDateTime.of(date, selectedTime)
                showTimePicker = false
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Создать задачу") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Отмена")
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Название задачи") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = assignedTo,
                onValueChange = { assignedTo = it },
                label = { Text("Ответственный") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            Button(onClick = { showDatePicker = true }) {
                Text(text = dueDate?.toString() ?: "Выбрать срок выполнения")
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.createTask(
                        projectId = projectId,
                        title = title,
                        description = description,
                        assignedTo = assignedTo,
                        dueDate = dueDate
                    )
                    onTaskCreated()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Создать")
            }
        }
    }
}
