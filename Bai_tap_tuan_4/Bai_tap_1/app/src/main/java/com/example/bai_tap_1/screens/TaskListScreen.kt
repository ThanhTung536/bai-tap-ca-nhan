package com.example.bai_tap_1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_1.ui.theme.*
import com.example.bai_tap_1.viewmodel.AuthViewModel
import com.example.bai_tap_1.viewmodel.TaskItem

@Composable
fun TaskListScreen(
    viewModel: AuthViewModel,
    initialCategory: String = "Tất cả",
    onNavigateToHome: () -> Unit = {},
    onNavigateToAddTask: () -> Unit = {},
    onNavigateToNotification: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToTaskDetail: (String) -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf(initialCategory) }

    val filteredTasks = viewModel.tasks.filter { task ->
        val matchesSearch = task.title.contains(searchQuery, ignoreCase = true)
        val matchesCategory = when (selectedCategoryFilter) {
            "Học tập" -> task.category == "Học tập"
            "Cuộc sống" -> task.category == "Cuộc sống"
            else -> true
        }
        matchesSearch && matchesCategory
    }

    val pendingTasks = filteredTasks.filter { !it.isCompleted }
    val completedTasks = filteredTasks.filter { it.isCompleted }

    Scaffold(
        bottomBar = {
            TaskListBottomNavigationBar(
                onNavigateToHome = onNavigateToHome,
                onNavigateToNotification = onNavigateToNotification,
                onNavigateToSettings = onNavigateToSettings,
                onAddNewTaskClick = onNavigateToAddTask
            )
        },
        containerColor = UthBgLight
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Danh sách",
                        color = UthDarkText,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${viewModel.pendingTasksCount} việc chưa xong",
                        color = UthSubText,
                        fontSize = 13.sp
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Tìm công việc", color = UthSubText, fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = UthSubText
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF1F5F9),
                        unfocusedContainerColor = Color(0xFFF1F5F9),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val categories = listOf("Tất cả", "Học tập", "Cuộc sống")
                    categories.forEach { category ->
                        val isSelected = selectedCategoryFilter == category
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) UthDarkText else Color.White)
                                .border(
                                    width = if (isSelected) 0.dp else 1.dp,
                                    color = UthBorderColor,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedCategoryFilter = category }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category,
                                color = if (isSelected) Color.White else UthDarkText,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            if (pendingTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "CẦN LÀM",
                        color = UthSubText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(pendingTasks) { task ->
                    TaskCardInList(
                        task = task,
                        onToggle = { viewModel.toggleTaskCompletion(task.id) },
                        onTaskClick = { onNavigateToTaskDetail(task.id) }
                    )
                }
            }

            if (completedTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "ĐÃ XONG",
                        color = UthSubText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                items(completedTasks) { task ->
                    TaskCardInList(
                        task = task,
                        onToggle = { viewModel.toggleTaskCompletion(task.id) },
                        onTaskClick = { onNavigateToTaskDetail(task.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCardInList(
    task: TaskItem,
    onToggle: () -> Unit,
    onTaskClick: () -> Unit = {}
) {
    val tagBgColor = if (task.category == "Học tập") UthTagBlueBg else UthTagOrangeBg
    val tagTextColor = if (task.category == "Học tập") UthPrimaryBlue else UthTagOrangeText
    val isPriorityHigh = task.title.contains("đồ án", ignoreCase = true) || task.title.contains("màn hình", ignoreCase = true)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(14.dp))
            .clickable { onTaskClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (task.isCompleted) UthPrimaryBlue else Color.White)
                    .border(
                        width = if (task.isCompleted) 0.dp else 1.5.dp,
                        color = if (task.isCompleted) Color.Transparent else UthBorderColor,
                        shape = CircleShape
                    )
                    .clickable { onToggle() },
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    color = if (task.isCompleted) Color(0xFF94A3B8) else UthDarkText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(tagBgColor)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = task.category,
                            color = tagTextColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "Hôm nay, ${task.time}",
                        color = UthSubText,
                        fontSize = 11.sp
                    )

                    if (isPriorityHigh && !task.isCompleted) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFFFE4E6))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Gấp",
                                color = Color(0xFFE11D48),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskListBottomNavigationBar(
    onNavigateToHome: () -> Unit,
    onNavigateToNotification: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onAddNewTaskClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp
        ) {
            NavigationBarItem(
                selected = false,
                onClick = { onNavigateToHome() },
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text("Trang chủ", fontSize = 11.sp) }
            )
            NavigationBarItem(
                selected = true,
                onClick = {},
                icon = { Icon(Icons.Default.FormatListNumbered, contentDescription = null) },
                label = { Text("Danh sách", fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = UthPrimaryBlue)
            )

            NavigationBarItem(
                selected = false,
                onClick = { onAddNewTaskClick() },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(UthPrimaryBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                    }
                },
                label = { Text("") }
            )

            NavigationBarItem(
                selected = false,
                onClick = { onNavigateToNotification() },
                icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                label = { Text("Thông báo", fontSize = 11.sp) }
            )

            NavigationBarItem(
                selected = false,
                onClick = { onNavigateToSettings() },
                icon = { Icon(Icons.Default.Tune, contentDescription = null) },
                label = { Text("Cài đặt", fontSize = 11.sp) }
            )
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskListScreenPreview() {
    val dummyVm = remember { AuthViewModel() }
    TaskListScreen(viewModel = dummyVm, onNavigateToHome = {}, onNavigateToProfile = {})
}
