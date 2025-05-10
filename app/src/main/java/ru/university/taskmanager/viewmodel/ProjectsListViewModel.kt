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
import ru.university.domain.usecase.CreateProjectUseCase
import ru.university.domain.usecase.GetProjectsUseCase
import javax.inject.Inject


@HiltViewModel
class ProjectsListViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val createProjectUseCase: CreateProjectUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProjectsListUiState())
    val uiState: StateFlow<ProjectsListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProjectsListUiEvent>()
    val events: SharedFlow<ProjectsListUiEvent> = _events

    init {
        loadProjects()
    }

    fun loadProjects() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val projects: List<Project> = getProjectsUseCase()
                _uiState.value = ProjectsListUiState(projects = projects)
            } catch (e: Exception) {
                _uiState.value = ProjectsListUiState(error = e.message)
            }
        }
    }

    fun onProjectClick(project: Project) {
        viewModelScope.launch {
            _events.emit(ProjectsListUiEvent.NavigateToProject(project.id))
        }
    }

    fun onAddProjectClick(title: String) {
        viewModelScope.launch {
            createProjectUseCase(title, null)
            loadProjects()
        }
    }
}

data class ProjectsListUiState(
    val projects: List<Project> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ProjectsListUiEvent {
    data class NavigateToProject(val projectId: String) : ProjectsListUiEvent()
}
