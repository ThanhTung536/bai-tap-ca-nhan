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
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
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
fun HomeScreen(
    viewModel: AuthViewModel,
    onNavigateToTaskList: (String) -> Unit = {},
    onNavigateToAddTask: () -> Unit = {},
    onNavigateToNotification: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToTaskDetail: (String) -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val userProfile = viewModel.userProfile

    val isDark = viewModel.isDarkMode
    val activePrimaryColor = viewModel.appPrimaryColor
    val bgColor = if (isDark) Color(0xFF000000) else UthBgLight
    val cardBgColor = if (isDark) Color(0xFF181C28) else Color.White
    val textColor = if (isDark) Color.White else UthDarkText
    val subTextColor = if (isDark) Color(0xFF94A3B8) else UthSubText

    LaunchedEffect(Unit) {
        viewModel.refreshRemoteConfig()
    }

    Scaffold(
        bottomBar = {
            HomeBottomNavigationBar(
                viewModel = viewModel,
                onNavigateToTaskList = { onNavigateToTaskList("Tất cả") },
                onNavigateToNotification = onNavigateToNotification,
                onNavigateToSettings = onNavigateToSettings,
                onAddNewTaskClick = onNavigateToAddTask
            )
        },
        containerColor = bgColor
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onNavigateToProfile() }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(if (isDark) Color(0xFF1E293B) else Color(0xFFD3E2FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (userProfile.name.contains(" ")) {
                                    userProfile.name.split(" ").takeLast(2).mapNotNull { it.firstOrNull()?.uppercase() }.joinToString("")
                                } else {
                                    userProfile.name.take(2).uppercase()
                                },
                                color = activePrimaryColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        Column {
                            Text(
                                text = "Thứ Ba, 20 tháng 10",
                                color = subTextColor,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Chào ${userProfile.name}!",
                                color = textColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    IconButton(onClick = { onNavigateToNotification() }) {
                        Box(contentAlignment = Alignment.TopEnd) {
                            Icon(
                                imageVector = Icons.Default.NotificationsNone,
                                contentDescription = null,
                                tint = textColor,
                                modifier = Modifier.size(26.dp)
                            )
                            if (viewModel.unreadNotificationsCount > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(9.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE11D48))
                                        .border(1.dp, Color.White, CircleShape)
                                )
                            }
                        }
                    }
                }
            }

            if (viewModel.welcomeBannerMessage.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, RoundedCornerShape(14.dp)),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1E293B) else Color(0xFFEEF2FE))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(text = "📢", fontSize = 16.sp)
                            Text(
                                text = viewModel.welcomeBannerMessage,
                                color = activePrimaryColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = activePrimaryColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Tiến độ hôm nay",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )
                            Text(
                                text = "${viewModel.completedTasksCount}/${viewModel.totalTasksCount} việc",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            val encouragementMessage = when {
                                viewModel.completedTasksCount == viewModel.totalTasksCount && viewModel.totalTasksCount > 0 -> "Xong hết rồi, nghỉ ngơi thôi!"
                                viewModel.completedTasksCount <= 2 -> "Bắt đầu từ việc nhỏ bé nhất"
                                else -> "Đã hơn nửa chặng, cố lên!"
                            }
                            Text(
                                text = encouragementMessage,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 12.sp
                            )
                        }

                        Box(
                            modifier = Modifier.size(64.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                progress = { (viewModel.completionPercentage / 100f) },
                                modifier = Modifier.fillMaxSize(),
                                color = Color.White,
                                trackColor = Color.White.copy(alpha = 0.3f),
                                strokeWidth = 5.dp
                            )
                            Text(
                                text = "${viewModel.completionPercentage}%",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(4.dp, RoundedCornerShape(16.dp))
                            .clickable { onNavigateToTaskList("Học tập") },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBgColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isDark) activePrimaryColor.copy(alpha = 0.2f) else UthTagBlueBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = activePrimaryColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Học tập",
                                color = textColor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${viewModel.studyTasksRemaining} việc còn lại",
                                color = subTextColor,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(4.dp, RoundedCornerShape(16.dp))
                            .clickable { onNavigateToTaskList("Cuộc sống") },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBgColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isDark) UthTagOrangeText.copy(alpha = 0.2f) else UthTagOrangeBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    tint = UthTagOrangeText,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Cuộc sống",
                                color = textColor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${viewModel.lifeTasksRemaining} việc còn lại",
                                color = subTextColor,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Việc hôm nay",
                        color = textColor,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Xem tất cả",
                        color = activePrimaryColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onNavigateToTaskList("Tất cả") }
                    )
                }
            }

            items(viewModel.tasks) { task ->
                TaskRowCard(
                    task = task,
                    viewModel = viewModel,
                    onToggle = { viewModel.toggleTaskCompletion(task.id) },
                    onTaskClick = { onNavigateToTaskDetail(task.id) }
                )
            }
        }
    }
}

@Composable
fun TaskRowCard(
    task: TaskItem,
    viewModel: AuthViewModel,
    onToggle: () -> Unit,
    onTaskClick: () -> Unit = {}
) {
    val isDark = viewModel.isDarkMode
    val activeColor = viewModel.appPrimaryColor
    val cardBg = if (isDark) Color(0xFF181C28) else Color.White
    val titleColor = if (task.isCompleted) Color(0xFF94A3B8) else (if (isDark) Color.White else UthDarkText)

    val tagBgColor = if (task.category == "Học tập") (if (isDark) activeColor.copy(alpha = 0.2f) else UthTagBlueBg) else (if (isDark) UthTagOrangeText.copy(alpha = 0.2f) else UthTagOrangeBg)
    val tagTextColor = if (task.category == "Học tập") activeColor else UthTagOrangeText

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(14.dp))
            .clickable { onTaskClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
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
                    .background(if (task.isCompleted) activeColor else (if (isDark) Color(0xFF1E293B) else Color.White))
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
                    color = titleColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(tagBgColor)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${task.category}  ${task.time}",
                        color = tagTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun HomeBottomNavigationBar(
    viewModel: AuthViewModel,
    onNavigateToTaskList: () -> Unit = {},
    onNavigateToNotification: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onAddNewTaskClick: () -> Unit = {}
) {
    val isDark = viewModel.isDarkMode
    val activeColor = viewModel.appPrimaryColor
    val bottomBg = if (isDark) Color(0xFF111827) else Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bottomBg)
    ) {
        NavigationBar(
            containerColor = bottomBg,
            tonalElevation = 8.dp
        ) {
            NavigationBarItem(
                selected = true,
                onClick = {},
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text("Trang chủ", fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = activeColor, selectedTextColor = activeColor)
            )
            NavigationBarItem(
                selected = false,
                onClick = { onNavigateToTaskList() },
                icon = { Icon(Icons.Default.FormatListNumbered, contentDescription = null) },
                label = { Text("Danh sách", fontSize = 11.sp) }
            )

            NavigationBarItem(
                selected = false,
                onClick = { onAddNewTaskClick() },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(activeColor),
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
fun HomeScreenPreview() {
    val dummyVm = remember { AuthViewModel() }
    HomeScreen(viewModel = dummyVm, onNavigateToProfile = {})
}
