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
import ru.university.domain.model.Project
import ru.university.domain.model.Task
import ru.university.domain.usecase.AddMemberUseCase
import ru.university.domain.usecase.GetProjectUseCase
import ru.university.domain.usecase.GetTasksUseCase
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val addMemberUseCase: AddMemberUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProjectDetailUiState())
    val uiState: StateFlow<ProjectDetailUiState> = _uiState.asStateFlow()

    fun loadTasks(projectId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val tasks: List<Task> = getTasksUseCase(projectId)
                _uiState.value = ProjectDetailUiState(tasks = tasks)
            } catch (e: Exception) {
                _uiState.value = ProjectDetailUiState(error = e.message)
            }
        }
    }

    fun onTaskClick(task: Task) {
        viewModelScope.launch {
            _events.emit(ProjectDetailUiEvent.NavigateToTask(task.id))
        }
    }

    fun onAddTask(projectId: String) {
        viewModelScope.launch {
            _events.emit(ProjectDetailUiEvent.NavigateToTaskEdit(null, projectId))
        }
    }

    fun onAddMember(projectId: String, userId: String) {
        viewModelScope.launch {
            try {
                addMemberUseCase(projectId, userId)
                loadAll(projectId)
            } catch (_: Exception) {
            }
        }
    }

    fun loadAll(projectId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val project = getProjectUseCase(projectId)
                val tasks = getTasksUseCase(projectId)
                _uiState.value = ProjectDetailUiState(
                    project   = project,
                    tasks     = tasks,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = ProjectDetailUiState(error = e.message)
            }
        }
    }

    private val _events = MutableSharedFlow<ProjectDetailUiEvent>()
    val events: SharedFlow<ProjectDetailUiEvent> = _events
}

data class ProjectDetailUiState(
    val project: Project? = null,
    val tasks:   List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error:     String? = null
)

sealed class ProjectDetailUiEvent {
    data class NavigateToTask(val taskId: String) : ProjectDetailUiEvent()
    data class NavigateToTaskEdit(val taskId: String?, val projectId: String) :
        ProjectDetailUiEvent()
}
