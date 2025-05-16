package ru.university.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.university.data.preference.UserPreferencesDataStore
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    prefs: UserPreferencesDataStore
) : ViewModel() {
    val isLoggedIn: StateFlow<Boolean> =
        prefs.authTokenFlow
            .map { !it.isNullOrBlank() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)
}