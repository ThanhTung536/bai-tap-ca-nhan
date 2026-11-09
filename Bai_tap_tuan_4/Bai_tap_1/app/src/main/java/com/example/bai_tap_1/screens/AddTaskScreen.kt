package com.example.bai_tap_1.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.NotificationsNone
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.bai_tap_1.ui.theme.*
import com.example.bai_tap_1.viewmodel.AuthViewModel
import java.util.Locale

@Composable
fun AddTaskScreen(
    viewModel: AuthViewModel,
    onBack: () -> Unit
) {
    var taskTitle by remember { mutableStateOf("") }
    var taskNote by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Học tập") }
    var selectedDate by remember { mutableStateOf("Hôm nay") }

    var isTimePopupExpanded by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf("07") }
    var selectedMinute by remember { mutableStateOf("00") }
    var selectedPeriod by remember { mutableStateOf("CH") }
    var taskTime by remember { mutableStateOf("07:00 CH") }

    var isReminderEnabled by remember { mutableStateOf(true) }
    var selectedPriority by remember { mutableStateOf("Vừa") }

    val isFormValid = taskTitle.isNotBlank()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = UthBgLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hủy",
                        color = UthPrimaryBlue,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onBack() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    Text(
                        text = "Việc mới",
                        color = UthDarkText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Lưu",
                        color = if (isFormValid) UthPrimaryBlue else UthSubText.copy(alpha = 0.5f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(enabled = isFormValid) {
                                viewModel.addNewTask(
                                    title = taskTitle,
                                    category = selectedCategory,
                                    time = taskTime
                                )
                                viewModel.showTopNotification("Đã thêm công việc mới thành công!")
                                onBack()
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column {
                            Text("Tên công việc", color = UthSubText, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            TextField(
                                value = taskTitle,
                                onValueChange = { taskTitle = it },
                                placeholder = { Text("Ví dụ: Ôn chương 4 – Giải thuật", color = UthSubText.copy(alpha = 0.6f), fontSize = 14.sp) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }

                        HorizontalDivider(color = UthBorderColor.copy(alpha = 0.4f))

                        Column {
                            Text("Ghi chú", color = UthSubText, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            TextField(
                                value = taskNote,
                                onValueChange = { taskNote = it },
                                placeholder = { Text("Thêm ghi chú (không bắt buộc)", color = UthSubText.copy(alpha = 0.6f), fontSize = 14.sp) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Nhóm", color = UthSubText, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val categories = listOf("Học tập", "Cuộc sống")
                        categories.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) Color(0xFFEFF4FF) else Color.White)
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) UthPrimaryBlue else UthBorderColor,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { selectedCategory = cat },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cat,
                                    color = if (isSelected) UthPrimaryBlue else UthDarkText,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Ngày", color = UthSubText, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val dates = listOf("Hôm nay", "Ngày mai", "Tuần này")
                        dates.forEach { dt ->
                            val isSelected = selectedDate == dt
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) Color(0xFFEFF4FF) else Color.White)
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) UthPrimaryBlue else UthBorderColor,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { selectedDate = dt },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dt,
                                    color = if (isSelected) UthPrimaryBlue else UthDarkText,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = null,
                                    tint = UthSubText,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text("Giờ", color = UthDarkText, fontSize = 14.sp)
                            }

                            Box {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .clickable { isTimePopupExpanded = !isTimePopupExpanded }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(taskTime, color = UthDarkText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                        Icon(Icons.Default.AccessTime, contentDescription = null, tint = UthSubText, modifier = Modifier.size(14.dp))
                                    }
                                }

                                if (isTimePopupExpanded) {
                                    Popup(
                                        alignment = Alignment.TopEnd,
                                        offset = IntOffset(0, 110),
                                        onDismissRequest = { isTimePopupExpanded = false }
                                    ) {
                                        Surface(
                                            modifier = Modifier
                                                .width(180.dp)
                                                .height(250.dp),
                                            shape = RoundedCornerShape(8.dp),
                                            color = Color.White,
                                            shadowElevation = 8.dp,
                                            border = BorderStroke(1.dp, Color(0xFFCBD5E1))
                                        ) {
                                            Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .height(32.dp)
                                                            .background(Color(0xFF007AFF)),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(selectedHour, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                                    }
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .height(32.dp)
                                                            .background(Color(0xFF007AFF)),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(selectedMinute, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                                    }
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .height(32.dp)
                                                            .background(Color(0xFF007AFF)),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(selectedPeriod, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                                    }
                                                }

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(1f)
                                                ) {
                                                    LazyColumn(
                                                        modifier = Modifier.weight(1f),
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        val hours = (1..12).map { String.format(
                                                            Locale.US, "%02d", it) }
                                                        items(hours) { h ->
                                                            Text(
                                                                text = h,
                                                                fontSize = 12.sp,
                                                                fontWeight = if (h == selectedHour) FontWeight.Bold else FontWeight.Normal,
                                                                color = if (h == selectedHour) Color(0xFF007AFF) else UthDarkText,
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .clickable {
                                                                        selectedHour = h
                                                                        taskTime = "$selectedHour:$selectedMinute $selectedPeriod"
                                                                    }
                                                                    .padding(vertical = 4.dp),
                                                                textAlign = TextAlign.Center
                                                            )
                                                        }
                                                    }

                                                    LazyColumn(
                                                        modifier = Modifier.weight(1f),
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        val minutes = (0..59).map { String.format(
                                                            Locale.US, "%02d", it) }
                                                        items(minutes) { m ->
                                                            Text(
                                                                text = m,
                                                                fontSize = 12.sp,
                                                                fontWeight = if (m == selectedMinute) FontWeight.Bold else FontWeight.Normal,
                                                                color = if (m == selectedMinute) Color(0xFF007AFF) else UthDarkText,
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .clickable {
                                                                        selectedMinute = m
                                                                        taskTime = "$selectedHour:$selectedMinute $selectedPeriod"
                                                                    }
                                                                    .padding(vertical = 4.dp),
                                                                textAlign = TextAlign.Center
                                                            )
                                                        }
                                                    }

                                                    Column(
                                                        modifier = Modifier.weight(1f),
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        listOf("CH", "SA").forEach { p ->
                                                            Text(
                                                                text = p,
                                                                fontSize = 12.sp,
                                                                fontWeight = if (p == selectedPeriod) FontWeight.Bold else FontWeight.Normal,
                                                                color = if (p == selectedPeriod) Color(0xFF007AFF) else UthDarkText,
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .clickable {
                                                                        selectedPeriod = p
                                                                        taskTime = "$selectedHour:$selectedMinute $selectedPeriod"
                                                                        isTimePopupExpanded = false
                                                                    }
                                                                    .padding(vertical = 6.dp),
                                                                textAlign = TextAlign.Center
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = UthBorderColor.copy(alpha = 0.4f)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NotificationsNone,
                                    contentDescription = null,
                                    tint = UthSubText,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column {
                                    Text("Nhắc tôi", color = UthDarkText, fontSize = 14.sp)
                                    Text("Trước 15 phút", color = UthSubText, fontSize = 11.sp)
                                }
                            }

                            Switch(
                                checked = isReminderEnabled,
                                onCheckedChange = { isReminderEnabled = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF22C55E)
                                )
                            )
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Mức ưu tiên", color = UthSubText, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF1F5F9))
                            .padding(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val priorities = listOf("Thấp", "Vừa", "Cao")
                            priorities.forEach { prio ->
                                val isSelected = selectedPriority == prio
                                Surface(
                                    onClick = { selectedPriority = prio },
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) Color.White else Color.Transparent,
                                    shadowElevation = if (isSelected) 2.dp else 0.dp
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = prio,
                                            color = if (isSelected) UthDarkText else UthSubText,
                                            fontSize = 13.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isFormValid) {
                        viewModel.addNewTask(
                            title = taskTitle,
                            category = selectedCategory,
                            time = taskTime
                        )
                        viewModel.showTopNotification("Đã thêm công việc mới thành công!")
                        onBack()
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = UthPrimaryBlue,
                    disabledContainerColor = Color(0xFFE2E8F0)
                )
            ) {
                Text(
                    text = "Lưu công việc",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isFormValid) Color.White else UthSubText
                )
            }
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTaskScreenPreview() {
    val dummyVm = remember { AuthViewModel() }
    AddTaskScreen(viewModel = dummyVm, onBack = {})
}
