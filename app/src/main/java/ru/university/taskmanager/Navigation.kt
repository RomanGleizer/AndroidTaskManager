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

sealed class Screen(val route: String) {
    object Login         : Screen("login")
    object SignUp        : Screen("signup")
    object ProjectsList  : Screen("projects_list")
    object CreateProject : Screen("create_project")
    object ProjectDetail : Screen("project_detail/{projectId}") {
        fun createRoute(id: String) = "project_detail/$id"
    }
    object TaskDetail    : Screen("task_detail/{taskId}") {
        fun createRoute(id: String) = "task_detail/$id"
    }
    object TaskEdit      : Screen("task_edit?taskId={taskId}&projectId={projectId}") {
        fun createRoute(taskId: String?, projectId: String) =
            "task_edit?taskId=${taskId ?: ""}&projectId=$projectId"
    }
    object Profile       : Screen("profile")
}

@Composable
fun TaskerNavGraph(
    navController : NavHostController,
    modifier      : Modifier = Modifier
) {
    NavHost(
        navController    = navController,
        startDestination = Screen.Login.route,
        modifier         = modifier
    ) {
        composable(Screen.Login.route) {
            val vm: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel      = vm,
                onLoginSuccess = {
                    navController.navigate(Screen.ProjectsList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSignUpClick  = { navController.navigate(Screen.SignUp.route) }
            )
        }

        composable(Screen.SignUp.route) {
            val vm: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                viewModel       = vm,
                onSignUpSuccess = {
                    navController.navigate(Screen.ProjectsList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onLoginClick    = { navController.popBackStack() }
            )
        }

        composable(Screen.ProjectsList.route) {
            val vm: ProjectsListViewModel = hiltViewModel()
            ProjectsListScreen(
                viewModel      = vm,
                onProjectClick = { navController.navigate(Screen.ProjectDetail.createRoute(it)) },
                onAddProject   = { navController.navigate(Screen.CreateProject.route) }
            )
        }

        composable(Screen.CreateProject.route) {
            val vm: ProjectsListViewModel = hiltViewModel()
            CreateProjectScreen(
                viewModel        = vm,
                onProjectCreated = { navController.popBackStack() },
                onCancel         = { navController.popBackStack() }
            )
        }

        composable(
            route     = Screen.ProjectDetail.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments!!.getString("projectId")!!
            val vm: ProjectDetailViewModel = hiltViewModel()
            ProjectDetailScreen(
                viewModel   = vm,
                projectId   = projectId,
                onTaskClick = { navController.navigate(Screen.TaskDetail.createRoute(it)) },
                onAddTask   = {
                    navController.navigate(Screen.TaskEdit.createRoute(null, projectId))
                },
                onBack      = { navController.popBackStack() }
            )
        }

        composable(
            route     = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments!!.getString("taskId")!!
            val vm: TaskDetailViewModel = hiltViewModel()
            TaskDetailScreen(
                viewModel = vm,
                taskId    = taskId,
                onEdit    = { projectId ->
                    navController.navigate(Screen.TaskEdit.createRoute(taskId, projectId))
                },
                onBack    = { navController.popBackStack() }
            )
        }

        composable(
            route     = Screen.TaskEdit.route,
            arguments = listOf(
                navArgument("taskId")    { type = NavType.StringType; defaultValue = "" },
                navArgument("projectId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskIdArg = backStackEntry.arguments!!.getString("taskId")
            val projectId = backStackEntry.arguments!!.getString("projectId")!!
            val vm: TaskEditViewModel = hiltViewModel()
            TaskEditScreen(
                viewModel  = vm,
                projectId  = projectId,
                taskId     = taskIdArg,
                onSave     = { navController.popBackStack() },
                onCancel   = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            val vm: ProfileViewModel = hiltViewModel()
            ProfileScreen(viewModel = vm)
        }
    }
}
