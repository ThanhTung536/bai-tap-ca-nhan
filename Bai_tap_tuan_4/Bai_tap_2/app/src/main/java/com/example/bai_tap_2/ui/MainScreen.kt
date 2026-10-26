package com.example.bai_tap_2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.bai_tap_2.model.LibraryViewModel
import com.example.bai_tap_2.ui.components.BottomNavBar
import com.example.bai_tap_2.ui.components.NavTab
import com.example.bai_tap_2.ui.screens.BookListScreen
import com.example.bai_tap_2.ui.screens.ManagementScreen
import com.example.bai_tap_2.ui.screens.StudentListScreen

@Composable
fun MainScreen(libraryViewModel: LibraryViewModel = remember { LibraryViewModel() }) {
    var currentTab by remember { mutableStateOf(NavTab.MANAGEMENT) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentTab = currentTab,
                onTabSelected = { selectedTab ->
                    currentTab = selectedTab
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentTab) {
                NavTab.MANAGEMENT -> ManagementScreen(viewModel = libraryViewModel)
                NavTab.BOOK_LIST -> BookListScreen(viewModel = libraryViewModel)
                NavTab.STUDENT_LIST -> StudentListScreen(viewModel = libraryViewModel)
            }
        }
    }
}
