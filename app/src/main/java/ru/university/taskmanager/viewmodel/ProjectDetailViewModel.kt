package ru.university.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.university.domain.model.Project
import ru.university.domain.model.Task
import ru.university.domain.model.User
import ru.university.domain.usecase.AddMemberUseCase
import ru.university.domain.usecase.GetProjectUseCase
import ru.university.domain.usecase.GetProjectUsersUseCase
import ru.university.domain.usecase.GetTasksUseCase
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getProjectUsersUseCase: GetProjectUsersUseCase,
    private val addMemberUseCase: AddMemberUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectDetailUiState())
    val uiState: StateFlow<ProjectDetailUiState> = _uiState.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    fun loadAll(projectId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val project = getProjectUseCase(projectId)
                val tasks = getTasksUseCase(projectId)
                _uiState.value = ProjectDetailUiState(project, tasks, false, null)
            } catch (e: Exception) {
                _uiState.value = ProjectDetailUiState(error = e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun loadProjectUsers(projectId: String) {
        viewModelScope.launch {
            try {
                val usersList = getProjectUsersUseCase(projectId)
                _users.value = usersList
            } catch (e: Exception) {
                _uiState.value = ProjectDetailUiState(error = e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun onAddMember(projectId: String, inviteId: String) {
        viewModelScope.launch {
            try {
                addMemberUseCase(projectId, inviteId)
                loadAll(projectId)
                loadProjectUsers(projectId)
            } catch (e: Exception) {
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
