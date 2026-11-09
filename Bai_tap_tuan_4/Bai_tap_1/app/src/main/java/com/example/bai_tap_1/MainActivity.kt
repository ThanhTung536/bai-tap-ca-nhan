package com.example.bai_tap_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bai_tap_1.navigation.Screen
import com.example.bai_tap_1.screens.AddTaskScreen
import com.example.bai_tap_1.screens.HomeScreen
import com.example.bai_tap_1.screens.LoginScreen
import com.example.bai_tap_1.screens.NotificationScreen
import com.example.bai_tap_1.screens.OnboardingScreen
import com.example.bai_tap_1.screens.ProfileScreen
import com.example.bai_tap_1.screens.SettingsScreen
import com.example.bai_tap_1.screens.StartScreen
import com.example.bai_tap_1.screens.TaskDetailScreen
import com.example.bai_tap_1.screens.TaskListScreen
import com.example.bai_tap_1.ui.theme.Bai_tap_1Theme
import com.example.bai_tap_1.viewmodel.AuthViewModel
import com.example.bai_tap_1.viewmodel.OnboardingViewModel
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {
    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel.attachContext(this)
        enableEdgeToEdge()
        setContent {
            Bai_tap_1Theme {
                val navController = rememberNavController()

                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Start.route,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(Screen.Start.route) {
                            StartScreen(
                                onNavigateToOnboarding = {
                                    navController.navigate(Screen.Onboarding.route) {
                                        popUpTo(Screen.Start.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Onboarding.route) {
                            OnboardingScreen(
                                viewModel = onboardingViewModel,
                                onFinish = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Login.route) {
                            LoginScreen(
                                viewModel = authViewModel,
                                onNavigateToHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Home.route) {
                            HomeScreen(
                                viewModel = authViewModel,
                                onNavigateToTaskList = { category ->
                                    navController.navigate(Screen.TaskList.createRoute(category))
                                },
                                onNavigateToAddTask = {
                                    navController.navigate(Screen.AddTask.route)
                                },
                                onNavigateToNotification = {
                                    navController.navigate(Screen.Notification.route)
                                },
                                onNavigateToSettings = {
                                    navController.navigate(Screen.Settings.route)
                                },
                                onNavigateToTaskDetail = { taskId ->
                                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                                },
                                onNavigateToProfile = {
                                    navController.navigate(Screen.Profile.route)
                                }
                            )
                        }

                        composable(
                            route = Screen.TaskList.route,
                            arguments = listOf(
                                navArgument("category") {
                                    type = NavType.StringType
                                    defaultValue = "Tất cả"
                                }
                            )
                        ) { backStackEntry ->
                            val category = backStackEntry.arguments?.getString("category") ?: "Tất cả"
                            TaskListScreen(
                                viewModel = authViewModel,
                                initialCategory = category,
                                onNavigateToHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                    }
                                },
                                onNavigateToAddTask = {
                                    navController.navigate(Screen.AddTask.route)
                                },
                                onNavigateToNotification = {
                                    navController.navigate(Screen.Notification.route)
                                },
                                onNavigateToSettings = {
                                    navController.navigate(Screen.Settings.route)
                                },
                                onNavigateToTaskDetail = { taskId ->
                                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                                },
                                onNavigateToProfile = {
                                    navController.navigate(Screen.Profile.route)
                                }
                            )
                        }

                        composable(Screen.AddTask.route) {
                            AddTaskScreen(
                                viewModel = authViewModel,
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(Screen.TaskDetail.route) { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId") ?: "1"
                            TaskDetailScreen(
                                taskId = taskId,
                                viewModel = authViewModel,
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(Screen.Notification.route) {
                            NotificationScreen(
                                viewModel = authViewModel,
                                onNavigateToHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                    }
                                },
                                onNavigateToTaskList = {
                                    navController.navigate(Screen.TaskList.createRoute("Tất cả"))
                                },
                                onNavigateToAddTask = {
                                    navController.navigate(Screen.AddTask.route)
                                },
                                onNavigateToSettings = {
                                    navController.navigate(Screen.Settings.route)
                                }
                            )
                        }

                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                viewModel = authViewModel,
                                onNavigateToHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                    }
                                },
                                onNavigateToTaskList = {
                                    navController.navigate(Screen.TaskList.createRoute("Tất cả"))
                                },
                                onNavigateToAddTask = {
                                    navController.navigate(Screen.AddTask.route)
                                },
                                onNavigateToNotification = {
                                    navController.navigate(Screen.Notification.route)
                                },
                                onNavigateToProfile = {
                                    navController.navigate(Screen.Profile.route)
                                }
                            )
                        }

                        composable(Screen.Profile.route) {
                            ProfileScreen(
                                viewModel = authViewModel,
                                onBackToHome = {
                                    navController.popBackStack()
                                },
                                onLoggedOut = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }

                    val topMessage = authViewModel.topMessageNotification
                    AnimatedVisibility(
                        visible = topMessage != null,
                        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .statusBarsPadding()
                            .padding(top = 16.dp, start = 20.dp, end = 20.dp)
                    ) {
                        if (topMessage != null) {
                            Surface(
                                shape = RoundedCornerShape(24.dp),
                                color = Color(0xFF1E293B),
                                shadowElevation = 8.dp
                            ) {
                                Text(
                                    text = topMessage,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            val analytics = FirebaseAnalytics.getInstance(this)
            analytics.setAnalyticsCollectionEnabled(true)
            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, "MainActivity")
                putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
            }
            analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
            analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
        } catch (_: Throwable) {
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "1. Start Screen Preview")
@Composable
fun StartScreenPreview() {
    Bai_tap_1Theme {
        StartScreen(
            onNavigateToOnboarding = {}
        )
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "2. Login Screen Preview")
@Composable
fun LoginScreenPreview() {
    Bai_tap_1Theme {
        val dummyVm = remember { AuthViewModel() }
        LoginScreen(viewModel = dummyVm, onNavigateToHome = {})
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "3. Home Screen Preview")
@Composable
fun HomeScreenPreview() {
    Bai_tap_1Theme {
        val dummyVm = remember { AuthViewModel() }
        HomeScreen(viewModel = dummyVm, onNavigateToProfile = {})
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "4. TaskList Screen Preview")
@Composable
fun TaskListScreenPreview() {
    Bai_tap_1Theme {
        val dummyVm = remember { AuthViewModel() }
        TaskListScreen(viewModel = dummyVm, onNavigateToHome = {}, onNavigateToProfile = {})
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "5. AddTask Screen Preview")
@Composable
fun AddTaskScreenPreview() {
    Bai_tap_1Theme {
        val dummyVm = remember { AuthViewModel() }
        AddTaskScreen(viewModel = dummyVm, onBack = {})
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "6. TaskDetail Screen Preview")
@Composable
fun TaskDetailScreenPreview() {
    Bai_tap_1Theme {
        val dummyVm = remember { AuthViewModel() }
        TaskDetailScreen(taskId = "1", viewModel = dummyVm, onBack = {})
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "7. Notification Screen Preview")
@Composable
fun NotificationScreenPreview() {
    Bai_tap_1Theme {
        val dummyVm = remember { AuthViewModel() }
        NotificationScreen(
            viewModel = dummyVm,
            onNavigateToHome = {},
            onNavigateToTaskList = {},
            onNavigateToAddTask = {},
            onNavigateToSettings = {}
        )
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "8. Settings Screen Preview")
@Composable
fun SettingsScreenPreview() {
    Bai_tap_1Theme {
        val dummyVm = remember { AuthViewModel() }
        SettingsScreen(
            viewModel = dummyVm,
            onNavigateToHome = {},
            onNavigateToTaskList = {},
            onNavigateToAddTask = {},
            onNavigateToNotification = {},
            onNavigateToProfile = {}
        )
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "9. Profile Screen Preview")
@Composable
fun ProfileScreenPreview() {
    Bai_tap_1Theme {
        val dummyVm = remember { AuthViewModel() }
        ProfileScreen(viewModel = dummyVm, onBackToHome = {}, onLoggedOut = {})
    }
}
