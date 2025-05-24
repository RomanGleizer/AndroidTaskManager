package ru.university.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.university.domain.usecase.CreateTaskUseCase
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskUiState())
    val uiState: StateFlow<CreateTaskUiState> = _uiState.asStateFlow()

    fun createTask(
        projectId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: java.time.LocalDateTime?
    ) {
        viewModelScope.launch {
            try {
                createTaskUseCase(projectId, title, description, assignedTo, dueDate)
                _uiState.value = CreateTaskUiState(success = true)
            } catch (e: Exception) {
                _uiState.value = CreateTaskUiState(error = e.message ?: "Ошибка создания задачи")
            }
        }
    }
}

data class CreateTaskUiState(
    val success: Boolean = false,
    val error: String? = null
)
