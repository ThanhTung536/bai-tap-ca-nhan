package com.example.bai_tap_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bai_tap_3.navigation.Screen
import com.example.bai_tap_3.screens.ConfirmScreen
import com.example.bai_tap_3.screens.ForgotPasswordScreen
import com.example.bai_tap_3.screens.ResetPasswordScreen
import com.example.bai_tap_3.screens.VerificationScreen
import com.example.bai_tap_3.ui.theme.Bai_tap_3Theme
import com.example.bai_tap_3.viewmodel.ForgotPasswordViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bai_tap_3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SmartTasksApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SmartTasksApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    // ViewModel quản lý state chung (OOP - Shared ViewModel)
    val viewModel: ForgotPasswordViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.ForgotPassword.route,
        modifier = modifier
    ) {
        // Màn hình 1
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                viewModel = viewModel,
                onNext = {
                    navController.navigate(Screen.Verification.route)
                }
            )
        }

        // Màn hình 2
        composable(Screen.Verification.route) {
            VerificationScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = {
                    navController.navigate(Screen.ResetPassword.route)
                }
            )
        }

        // Màn hình 3
        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = {
                    navController.navigate(Screen.Confirm.route)
                }
            )
        }

        // Màn hình 4
        composable(Screen.Confirm.route) {
            ConfirmScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSubmit = {
                    // Trở về Màn hình 1 để xem dữ liệu vừa Submit
                    navController.popBackStack(
                        Screen.ForgotPassword.route,
                        inclusive = false
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Interactive App Flow")
@Composable
fun SmartTasksAppPreview() {
    Bai_tap_3Theme {
        SmartTasksApp()
    }
}
