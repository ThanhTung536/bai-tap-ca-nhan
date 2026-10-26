package com.example.bai_tap_1.navigation

sealed class Screen(val route: String) {
    data object Start : Screen("start_screen")
    data object Onboarding : Screen("onboarding_screen")
    data object Home : Screen("home_screen")
}
