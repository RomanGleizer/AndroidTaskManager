package ru.university.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.university.taskmanager.viewmodel.AuthViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskerApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskerApp() {
    val navController = rememberNavController()
    val authVm: AuthViewModel = hiltViewModel()
    val isLoggedIn by authVm.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(Screen.ProjectsList.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(0)
            }
        }
    }

    Scaffold(
        topBar = {
            if (isLoggedIn) {
                TopAppBar(
                    title = { Text("Задачник") },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Профиль"
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        TaskerNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
