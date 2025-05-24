// SetupNavGraph.kt
package ru.university.taskmanager

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.university.taskmanager.ui.screens.*
import ru.university.taskmanager.viewmodel.*

@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login,
        modifier = modifier
    ) {
        composable(Screen.Login) {
            val vm: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = vm,
                onLoginSuccess = {
                    navController.navigate(Screen.ProjectsList) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Screen.SignUp)
                }
            )
        }

        composable(Screen.SignUp) {
            val vm: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                viewModel = vm,
                onSignUpSuccess = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ProjectsList) {
            val vm: ProjectsListViewModel = hiltViewModel()
            ProjectsListScreen(
                viewModel = vm,
                onProjectClick = { projectId ->
                    navController.navigate(Screen.projectDetailRoute(projectId))
                },
                onAddProject = {
                    navController.navigate(Screen.CreateProject)
                }
            )
        }

        composable(Screen.CreateProject) {
            val vm: ProjectsListViewModel = hiltViewModel()
            CreateProjectScreen(
                viewModel = vm,
                onProjectCreated = {
                    navController.popBackStack()
                    vm.loadProjects()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ProjectDetail,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
            val vm: ProjectDetailViewModel = hiltViewModel()
            ProjectDetailScreen(
                projectId = projectId,
                viewModel = vm,
                onTaskClick = { taskId ->
                    navController.navigate(Screen.taskDetailRoute(projectId, taskId))
                },
                onAddTask = {
                    // TODO: navigate to create task screen if needed
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TaskDetail,
            arguments = listOf(
                navArgument("projectId") { type = NavType.StringType },
                navArgument("taskId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val vm: TaskDetailViewModel = hiltViewModel()
            TaskDetailScreen(
                projectId = projectId,
                taskId = taskId,
                viewModel = vm,
                onEdit = { projId ->
                    navController.navigate(Screen.taskEditRoute(taskId, projId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile) {
            val vm: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TaskEdit,
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType },
                navArgument("projectId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
            val vm: TaskEditViewModel = hiltViewModel()
            TaskEditScreen(
                taskId = taskId,
                projectId = projectId,
                viewModel = vm,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
