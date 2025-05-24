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
import ru.university.domain.usecase.GetTaskByIdUseCase
import ru.university.domain.usecase.UpdateTaskUseCase
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState: StateFlow<TaskEditUiState> = _uiState.asStateFlow()

    fun initialize(taskId: String?, projectId: String) {
        viewModelScope.launch {
            if (!taskId.isNullOrBlank()) {
                try {
                    val task = getTaskByIdUseCase(projectId, taskId)
                    _uiState.value = TaskEditUiState(
                        projectId = projectId,
                        taskId = taskId,
                        title = task.title,
                        description = task.description ?: "",
                        assignedToId = task.assignedTo,
                        dueDate = task.dueDate?.let { kotlinx.datetime.LocalDateTime.parse(it.toString()) },
                        status = task.status.name
                    )
                } catch (e: Exception) {
                    _uiState.value =
                        TaskEditUiState(projectId = projectId, taskId = taskId, error = e.message)
                }
            } else {
                _uiState.value = TaskEditUiState(projectId = projectId, taskId = null)
            }
        }
    }

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onAssignToChange(userId: String) {
        _uiState.value = _uiState.value.copy(assignedToId = userId)
    }

    fun onDueDateChange(dueDate: kotlinx.datetime.LocalDateTime?) {
        _uiState.value = _uiState.value.copy(dueDate = dueDate)
    }

    fun onSave() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val state = _uiState.value
            try {
                if (state.taskId.isNullOrBlank()) {
                    val javaDueDate = state.dueDate?.toJavaLocalDateTime()
                    createTaskUseCase(
                        projectId = state.projectId,
                        title = state.title,
                        description = state.description,
                        assignedTo = state.assignedToId,
                        dueDate = javaDueDate
                    )
                } else {
                    val javaDueDate = state.dueDate?.toJavaLocalDateTime()
                    updateTaskUseCase(
                        projectId = state.projectId,
                        taskId = state.taskId,
                        title = state.title,
                        description = state.description,
                        assignedTo = state.assignedToId,
                        dueDate = javaDueDate,
                        status = TaskStatus.valueOf(state.status)
                    )
                }
                _events.emit(TaskEditUiEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.value = state.copy(isLoading = false, error = e.message)
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
    val assignedToId: String = "",
    val dueDate: kotlinx.datetime.LocalDateTime? = null,
    val status: String = TaskStatus.TODO.name,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TaskEditUiEvent {
    object NavigateBack : TaskEditUiEvent()
}
