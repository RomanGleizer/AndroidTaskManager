package ru.university.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.university.domain.model.Task
import ru.university.domain.model.TaskStatus
import ru.university.domain.usecase.GetTaskUseCase
import ru.university.domain.usecase.UpdateTaskStatusUseCase
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    fun loadTask(projectId: String, taskId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val task = getTaskByIdUseCase(projectId, taskId)
                _uiState.value = TaskDetailUiState(task, false, null)
            } catch (e: Exception) {
                _uiState.value = TaskDetailUiState(error = e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun onChangeStatus(projectId: String, taskId: String, status: TaskStatus) {
        viewModelScope.launch {
            updateTaskStatusUseCase(taskId, status)
            loadTask(projectId, taskId)
        }
    }
}

data class TaskDetailUiState(
    val task: Task? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
