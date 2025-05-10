package ru.university.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow
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

    fun loadTask(taskId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val task = getTaskByIdUseCase(taskId)
                _uiState.value = TaskDetailUiState(task = task)
            } catch (e: Exception) {
                _uiState.value = TaskDetailUiState(error = e.message)
            }
        }
    }

    fun onChangeStatus(taskId: String, status: TaskStatus) {
        viewModelScope.launch {
            updateTaskStatusUseCase(taskId, status)
            loadTask(taskId)
        }
    }

    fun onEdit(task: Task) {
        viewModelScope.launch {
            _events.emit(TaskDetailUiEvent.NavigateToEdit(task.id, task.projectId))
        }
    }

    private val _events = MutableSharedFlow<TaskDetailUiEvent>()
    val events: SharedFlow<TaskDetailUiEvent> = _events
}

data class TaskDetailUiState(
    val task: Task? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TaskDetailUiEvent {
    data class NavigateToEdit(val taskId: String, val projectId: String) : TaskDetailUiEvent()
}
