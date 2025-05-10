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
import kotlinx.datetime.toJavaLocalDateTime
import ru.university.domain.model.TaskStatus
import ru.university.domain.usecase.CreateTaskUseCase
import ru.university.domain.usecase.UpdateTaskStatusUseCase
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState: StateFlow<TaskEditUiState> = _uiState.asStateFlow()

    fun initialize(taskId: String?, projectId: String) {
        _uiState.value = TaskEditUiState(projectId = projectId, taskId = taskId)
    }

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onAssignToChange(userId: String) {
        _uiState.value = _uiState.value.copy(assignedTo = userId)
    }

    fun onDueDateChange(dueDate: kotlinx.datetime.LocalDateTime?) {
        _uiState.value = _uiState.value.copy(dueDate = dueDate)
    }

    fun onSave() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                if (_uiState.value.taskId == null) {
                    val javaDueDate = _uiState.value.dueDate?.toJavaLocalDateTime()
                    createTaskUseCase(
                        projectId = _uiState.value.projectId,
                        title = _uiState.value.title,
                        description = _uiState.value.description,
                        assignedTo = _uiState.value.assignedTo,
                        dueDate = javaDueDate
                    )
                } else {
                    updateTaskStatusUseCase(
                        taskId = _uiState.value.taskId!!,
                        status = TaskStatus.valueOf(_uiState.value.status)
                    )
                }
                _events.emit(TaskEditUiEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    private val _events = MutableSharedFlow<TaskEditUiEvent>()
    val events: SharedFlow<TaskEditUiEvent> = _events
}

data class TaskEditUiState(
    val projectId: String = "",
    val taskId: String? = null,
    val title: String = "",
    val description: String = "",
    val assignedTo: String = "",
    val dueDate: kotlinx.datetime.LocalDateTime? = null,
    val status: String = TaskStatus.TODO.name,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TaskEditUiEvent {
    object NavigateBack : TaskEditUiEvent()
}
