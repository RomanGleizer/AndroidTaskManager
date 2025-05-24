package ru.university.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    init {
        loadProjects()
    }

    fun loadProjects() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val projects = getProjectsUseCase()
                _uiState.value = ProjectsListUiState(projects, false, null)
            } catch (e: Exception) {
                _uiState.value = ProjectsListUiState(error = e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun onAddProjectClick(title: String, description: String?) {
        viewModelScope.launch {
            try {
                createProjectUseCase(title, description)
                loadProjects()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Ошибка создания проекта")
            }
        }
    }
}

data class ProjectsListUiState(
    val projects: List<Project> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
