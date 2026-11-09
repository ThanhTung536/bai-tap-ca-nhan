package com.example.bai_tap_1.navigation

sealed class Screen(val route: String) {
    data object Start : Screen("start_screen")
    data object Onboarding : Screen("onboarding_screen")
    data object Login : Screen("login_screen")
    data object Home : Screen("home_screen")
    data object TaskList : Screen("task_list_screen?category={category}") {
        fun createRoute(category: String = "Tất cả") = "task_list_screen?category=$category"
    }
    data object AddTask : Screen("add_task_screen")
    data object TaskDetail : Screen("task_detail_screen/{taskId}") {
        fun createRoute(taskId: String) = "task_detail_screen/$taskId"
    }
    data object Notification : Screen("notification_screen")
    data object Settings : Screen("settings_screen")
    data object Profile : Screen("profile_screen")
}
