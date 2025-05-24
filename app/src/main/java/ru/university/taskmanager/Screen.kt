object Screen {
    const val Login = "login"
    const val SignUp = "signup"
    const val ProjectsList = "projects_list"
    const val CreateProject = "create_project"
    const val ProjectDetail = "project_detail/{projectId}"
    const val TaskDetail = "task_detail/{projectId}/{taskId}"
    const val Profile = "profile"
    const val TaskEdit = "task_edit/{taskId}/{projectId}"
    const val CreateTask = "create_task/{projectId}"

    fun projectDetailRoute(projectId: String) = "project_detail/$projectId"
    fun taskDetailRoute(projectId: String, taskId: String) = "task_detail/$projectId/$taskId"
    fun taskEditRoute(taskId: String, projectId: String) = "task_edit/$taskId/$projectId"
    fun createTaskRoute(projectId: String) = "create_task/$projectId"
}
