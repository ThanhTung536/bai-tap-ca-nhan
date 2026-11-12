package com.example.bai_tap_1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_1.ui.theme.*
import com.example.bai_tap_1.viewmodel.AuthViewModel
import com.example.bai_tap_1.viewmodel.NotificationType

@Composable
fun NotificationScreen(
    viewModel: AuthViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToTaskList: () -> Unit,
    onNavigateToAddTask: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onNotificationScreenShown()
    }

    val notifications = viewModel.notifications

    Scaffold(
        bottomBar = {
            NotificationBottomNavigationBar(
                onNavigateToHome = onNavigateToHome,
                onNavigateToTaskList = onNavigateToTaskList,
                onNavigateToAddTask = onNavigateToAddTask,
                onNavigateToSettings = onNavigateToSettings
            )
        },
        containerColor = UthBgLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Thông báo",
                    color = UthDarkText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                if (notifications.isNotEmpty()) {
                    Text(
                        text = "Đánh dấu đã đọc",
                        color = UthPrimaryBlue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.markAllNotificationsAsRead()
                            }
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }

            if (notifications.isEmpty()) {
                NotificationEmptyView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onAddTaskClick = onNavigateToAddTask,
                    onSettingsClick = onNavigateToSettings
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(2.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column {
                                notifications.forEachIndexed { idx, item ->
                                    val iconBgColor = when (item.type) {
                                        NotificationType.UPCOMING, NotificationType.REMINDER -> Color(0xFFEFF4FF)
                                        NotificationType.OVERDUE -> Color(0xFFFFE4E6)
                                        NotificationType.SUCCESS -> Color(0xFFE6F4EA)
                                    }

                                    val iconTintColor = when (item.type) {
                                        NotificationType.UPCOMING, NotificationType.REMINDER -> UthPrimaryBlue
                                        NotificationType.OVERDUE -> Color(0xFFE11D48)
                                        NotificationType.SUCCESS -> Color(0xFF137333)
                                    }

                                    val rowBgColor = if (!item.isRead) Color(0xFFF4F8FF) else Color.White

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(rowBgColor)
                                            .clickable {
                                                viewModel.markNotificationAsRead(item.id)
                                            }
                                            .padding(horizontal = 14.dp, vertical = 14.dp),
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(iconBgColor),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = when (item.type) {
                                                    NotificationType.UPCOMING, NotificationType.REMINDER -> Icons.Default.Alarm
                                                    NotificationType.OVERDUE -> Icons.Default.WarningAmber
                                                    NotificationType.SUCCESS -> Icons.Default.Check
                                                },
                                                contentDescription = null,
                                                tint = iconTintColor,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Column(
                                            modifier = Modifier.weight(1f),
                                            verticalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = item.title,
                                                    color = UthDarkText,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold
                                                )

                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Text(
                                                        text = item.time,
                                                        color = UthSubText,
                                                        fontSize = 12.sp
                                                    )

                                                    if (!item.isRead) {
                                                        Box(
                                                            modifier = Modifier
                                                                .size(8.dp)
                                                                .clip(CircleShape)
                                                                .background(UthPrimaryBlue)
                                                        )
                                                    }
                                                }
                                            }

                                            Text(
                                                text = item.description,
                                                color = UthSubText,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp
                                            )
                                        }
                                    }

                                    if (idx < notifications.lastIndex) {
                                        HorizontalDivider(color = UthBorderColor.copy(alpha = 0.3f))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationEmptyView(
    modifier: Modifier = Modifier,
    onAddTaskClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8EEFF)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(82.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(78.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = null,
                        tint = UthPrimaryBlue,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(Color(0xFF34A853)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Chưa có thông báo nào",
            color = UthDarkText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Khi có việc sắp đến hạn, quá hạn hoặc\nlời nhắc, bạn sẽ thấy chúng ở đây.",
            color = UthSubText,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddTaskClick,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = UthPrimaryBlue),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "+ Thêm việc cần làm",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Cài đặt nhắc việc",
            color = UthPrimaryBlue,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onSettingsClick() }
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun NotificationBottomNavigationBar(
    onNavigateToHome: () -> Unit,
    onNavigateToTaskList: () -> Unit,
    onNavigateToAddTask: () -> Unit,
    onNavigateToSettings: () -> Unit
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
                selected = false,
                onClick = { onNavigateToTaskList() },
                icon = { Icon(Icons.Default.FormatListNumbered, contentDescription = null) },
                label = { Text("Danh sách", fontSize = 11.sp) }
            )

            NavigationBarItem(
                selected = false,
                onClick = { onNavigateToAddTask() },
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
                selected = true,
                onClick = {},
                icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                label = { Text("Thông báo", fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = UthPrimaryBlue)
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
@Preview(showBackground = true, showSystemUi = true, name = "Notifications List")
@Composable
fun NotificationScreenPreview() {
    val dummyVm = remember { AuthViewModel() }
    NotificationScreen(
        viewModel = dummyVm,
        onNavigateToHome = {},
        onNavigateToTaskList = {},
        onNavigateToAddTask = {},
        onNavigateToSettings = {}
    )
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "Notifications EmptyView")
@Composable
fun NotificationEmptyScreenPreview() {
    val dummyVm = remember {
        AuthViewModel().apply {
            onNotificationScreenShown()
            notifications.clear()
        }
    }
    NotificationScreen(
        viewModel = dummyVm,
        onNavigateToHome = {},
        onNavigateToTaskList = {},
        onNavigateToAddTask = {},
        onNavigateToSettings = {}
    )
}

