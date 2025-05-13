package ru.university.taskmanager

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.university.taskmanager.ui.screens.CreateProjectScreen
import ru.university.taskmanager.ui.screens.LoginScreen
import ru.university.taskmanager.ui.screens.ProjectDetailScreen
import ru.university.taskmanager.ui.screens.ProjectsListScreen
import ru.university.taskmanager.ui.screens.SignUpScreen
import ru.university.taskmanager.ui.screens.TaskDetailScreen
import ru.university.taskmanager.ui.screens.TaskEditScreen
import ru.university.taskmanager.viewmodel.LoginViewModel
import ru.university.taskmanager.viewmodel.ProjectDetailViewModel
import ru.university.taskmanager.viewmodel.ProjectsListViewModel
import ru.university.taskmanager.viewmodel.SignUpViewModel
import ru.university.taskmanager.viewmodel.TaskDetailViewModel
import ru.university.taskmanager.viewmodel.TaskEditViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ProjectsList : Screen("projects_list")
    object CreateProject : Screen("create_project")
    object ProjectDetail : Screen("project_detail/{projectId}") {
        fun createRoute(id: String) = "project_detail/$id"
    }

    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(id: String) = "task_detail/$id"
    }

    object TaskEdit : Screen("task_edit?taskId={taskId}&projectId={projectId}") {
        fun createRoute(taskId: String?, projectId: String) =
            "task_edit?taskId=${taskId ?: ""}&projectId=$projectId"
    }
}

@Composable
fun TaskerNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val vm: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = vm,
                onLoginSuccess = {
                    navController.navigate(Screen.ProjectsList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
            )
        }
        composable(Screen.SignUp.route) {
            val vm: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                viewModel = vm,
                onSignUpSuccess = {
                    navController.navigate(Screen.ProjectsList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onLoginClick = { navController.popBackStack() }
            )
        }

        composable(Screen.ProjectsList.route) {
            val vm: ProjectsListViewModel = hiltViewModel()
            ProjectsListScreen(
                viewModel = vm,
                onProjectClick = { navController.navigate(Screen.ProjectDetail.createRoute(it)) },
                onAddProject = { navController.navigate(Screen.CreateProject.route) }
            )
        }

        composable(Screen.CreateProject.route) {
            val vm: ProjectsListViewModel = hiltViewModel()
            CreateProjectScreen(
                viewModel = vm,
                onProjectCreated = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ProjectDetail.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments!!.getString("projectId")!!
            val vm: ProjectDetailViewModel = hiltViewModel()
            ProjectDetailScreen(
                viewModel = vm,
                projectId = projectId,
                onTaskClick = { navController.navigate(Screen.TaskDetail.createRoute(it)) },
                onAddTask = {
                    navController.navigate(
                        Screen.TaskEdit.createRoute(
                            null,
                            projectId
                        )
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")!!
            val vm: TaskDetailViewModel = hiltViewModel()
            TaskDetailScreen(
                viewModel = vm,
                taskId = taskId,
                onEdit = { projectId ->
                    navController.navigate(Screen.TaskEdit.createRoute(taskId, projectId))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.TaskEdit.route,
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType; defaultValue = "" },
                navArgument("projectId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskIdArg = backStackEntry.arguments?.getString("taskId")
            val projectId = backStackEntry.arguments?.getString("projectId")!!
            val vm: TaskEditViewModel = hiltViewModel()
            TaskEditScreen(
                viewModel = vm,
                projectId = projectId,
                taskId = taskIdArg,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}