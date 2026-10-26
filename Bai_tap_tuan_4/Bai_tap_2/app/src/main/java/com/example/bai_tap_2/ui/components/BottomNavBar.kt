package com.example.bai_tap_2.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NavTab(val title: String, val icon: ImageVector) {
    MANAGEMENT("Quản lý", Icons.Default.Home),
    BOOK_LIST("DS Sách", Icons.AutoMirrored.Filled.MenuBook),
    STUDENT_LIST("Sinh viên", Icons.Default.Person)
}

@Composable
fun BottomNavBar(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavTab.entries.forEach { tab ->
            val selected = (tab == currentTab)
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title,
                        tint = if (selected) Color(0xFF2B52B8) else Color(0xFF9E9E9E)
                    )
                },
                label = {
                    Text(
                        text = tab.title,
                        color = if (selected) Color(0xFF2B52B8) else Color(0xFF9E9E9E),
                        fontSize = 12.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFFE8EEFF)
                )
            )
        }
    }
}
