package ru.university.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    fun loadAll(projectId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val project: Project = getProjectUseCase(projectId)
                val tasks: List<Task> = getTasksUseCase(projectId)
                _uiState.value = ProjectDetailUiState(project, tasks, false, null)
            } catch (e: Exception) {
                _uiState.value = ProjectDetailUiState(error = e.message ?: "Ошибка загрузки")
            }
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
}

data class ProjectDetailUiState(
    val project: Project? = null,
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
