package com.example.bai_tap_1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_1.ui.theme.*
import com.example.bai_tap_1.viewmodel.AuthViewModel

@Composable
fun SettingsScreen(
    viewModel: AuthViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToTaskList: () -> Unit,
    onNavigateToAddTask: () -> Unit,
    onNavigateToNotification: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val userProfile = viewModel.userProfile

    val selectedThemeMode = viewModel.appThemeMode
    val selectedPrimaryColor = viewModel.appPrimaryColor
    val isDark = viewModel.isDarkMode

    val bgColor = if (isDark) Color(0xFF000000) else UthBgLight
    val cardBgColor = if (isDark) Color(0xFF181C28) else Color.White
    val textColor = if (isDark) Color.White else UthDarkText
    val subTextColor = if (isDark) Color(0xFF94A3B8) else UthSubText

    var isTaskReminderEnabled by remember { mutableStateOf(true) }
    var isSoundEnabled by remember { mutableStateOf(true) }
    var isDailySummaryEnabled by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            SettingsBottomNavigationBar(
                viewModel = viewModel,
                onNavigateToHome = onNavigateToHome,
                onNavigateToTaskList = onNavigateToTaskList,
                onNavigateToAddTask = onNavigateToAddTask,
                onNavigateToNotification = onNavigateToNotification
            )
        },
        containerColor = bgColor
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
                        text = "Cài đặt",
                        color = textColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(if (isDark) Color(0xFF1E293B) else Color(0xFFD3E2FF))
                            .clickable { onNavigateToProfile() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (userProfile.name.contains(" ")) {
                                userProfile.name.split(" ").takeLast(2).mapNotNull { it.firstOrNull()?.uppercase() }.joinToString("")
                            } else {
                                userProfile.name.take(2).uppercase()
                            },
                            color = selectedPrimaryColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "GIAO DIỆN",
                        color = subTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBgColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                val isLightSel = selectedThemeMode == "Sáng"
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(68.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFFF8FAFC))
                                            .border(
                                                width = if (isLightSel) 2.dp else 1.dp,
                                                color = if (isLightSel) selectedPrimaryColor else UthBorderColor,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { viewModel.updateThemeMode("Sáng") }
                                            .padding(8.dp)
                                    ) {
                                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Box(modifier = Modifier.width(36.dp).height(8.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFFCBD5E1)))
                                            Box(modifier = Modifier.fillMaxWidth().height(16.dp).clip(RoundedCornerShape(4.dp)).background(Color.White))
                                            Box(modifier = Modifier.fillMaxWidth().height(16.dp).clip(RoundedCornerShape(4.dp)).background(Color.White))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Sáng",
                                        color = if (isLightSel) selectedPrimaryColor else subTextColor,
                                        fontSize = 13.sp,
                                        fontWeight = if (isLightSel) FontWeight.Bold else FontWeight.Medium
                                    )
                                }

                                val isDarkSel = selectedThemeMode == "Tối"
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(68.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFF0F172A))
                                            .border(
                                                width = if (isDarkSel) 2.dp else 1.dp,
                                                color = if (isDarkSel) selectedPrimaryColor else UthBorderColor,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { viewModel.updateThemeMode("Tối") }
                                            .padding(8.dp)
                                    ) {
                                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Box(modifier = Modifier.width(36.dp).height(8.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFF334155)))
                                            Box(modifier = Modifier.fillMaxWidth().height(16.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFF1E293B)))
                                            Box(modifier = Modifier.fillMaxWidth().height(16.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFF1E293B)))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Tối",
                                        color = if (isDarkSel) selectedPrimaryColor else subTextColor,
                                        fontSize = 13.sp,
                                        fontWeight = if (isDarkSel) FontWeight.Bold else FontWeight.Medium
                                    )
                                }

                                val isAutoSel = selectedThemeMode == "Tự động"
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(68.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFFF8FAFC))
                                            .border(
                                                width = if (isAutoSel) 2.dp else 1.dp,
                                                color = if (isAutoSel) selectedPrimaryColor else UthBorderColor,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { viewModel.updateThemeMode("Tự động") }
                                    ) {
                                        Row(modifier = Modifier.fillMaxSize()) {
                                            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color.White))
                                            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFF0F172A)))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Tự động",
                                        color = if (isAutoSel) selectedPrimaryColor else subTextColor,
                                        fontSize = 13.sp,
                                        fontWeight = if (isAutoSel) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }

                            HorizontalDivider(color = UthBorderColor.copy(alpha = 0.3f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Màu chủ đạo", color = textColor, fontSize = 14.sp)

                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    val colorsList = listOf(
                                        Color(0xFF3865F6),
                                        Color(0xFF059669),
                                        Color(0xFFD97706),
                                        Color(0xFF8B5CF6)
                                    )

                                    colorsList.forEach { color ->
                                        val isSelected = selectedPrimaryColor == color
                                        Box(
                                            modifier = Modifier
                                                .size(30.dp)
                                                .then(
                                                    if (isSelected) {
                                                        Modifier
                                                            .border(2.dp, color, CircleShape)
                                                            .padding(3.dp)
                                                    } else {
                                                        Modifier
                                                    }
                                                )
                                                .clip(CircleShape)
                                                .background(color)
                                                .clickable { viewModel.updatePrimaryColor(color) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "THÔNG BÁO",
                        color = subTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBgColor)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Nhắc việc", color = textColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                    Text("Báo trước hạn chót 15 phút", color = subTextColor, fontSize = 11.sp)
                                }
                                Switch(
                                    checked = isTaskReminderEnabled,
                                    onCheckedChange = { isTaskReminderEnabled = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF22C55E)
                                    )
                                )
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = UthBorderColor.copy(alpha = 0.3f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Âm thanh", color = textColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                    Text("Phát âm khi có thông báo", color = subTextColor, fontSize = 11.sp)
                                }
                                Switch(
                                    checked = isSoundEnabled,
                                    onCheckedChange = { isSoundEnabled = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF22C55E)
                                    )
                                )
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = UthBorderColor.copy(alpha = 0.3f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Tổng kết cuối ngày", color = textColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                    Text("Gửi lúc 21:00 mỗi tối", color = subTextColor, fontSize = 11.sp)
                                }
                                Switch(
                                    checked = isDailySummaryEnabled,
                                    onCheckedChange = { isDailySummaryEnabled = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF22C55E)
                                    )
                                )
                            }
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "CHUNG",
                        color = subTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBgColor)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Ngôn ngữ", color = textColor, fontSize = 14.sp)
                                Text("Tiếng Việt", color = subTextColor, fontSize = 13.sp)
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = UthBorderColor.copy(alpha = 0.3f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Giới thiệu UTH Todo", color = textColor, fontSize = 14.sp)
                                Text("Phiên bản 1.0", color = subTextColor, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsBottomNavigationBar(
    viewModel: AuthViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToTaskList: () -> Unit,
    onNavigateToAddTask: () -> Unit,
    onNavigateToNotification: () -> Unit
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
                selected = true,
                onClick = {},
                icon = { Icon(Icons.Default.Tune, contentDescription = null) },
                label = { Text("Cài đặt", fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = activeColor, selectedTextColor = activeColor)
            )
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
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
