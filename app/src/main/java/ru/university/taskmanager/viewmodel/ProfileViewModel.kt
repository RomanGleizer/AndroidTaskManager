package ru.university.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.university.domain.model.User
import ru.university.domain.usecase.GetProfileUseCase
import ru.university.domain.usecase.SignOutUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfile: GetProfileUseCase,
    private val signOut: SignOutUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        viewModelScope.launch {
            _user.value = getProfile()
        }
    }

    fun logout() {
        viewModelScope.launch {
            signOut()
        }
    }
}
