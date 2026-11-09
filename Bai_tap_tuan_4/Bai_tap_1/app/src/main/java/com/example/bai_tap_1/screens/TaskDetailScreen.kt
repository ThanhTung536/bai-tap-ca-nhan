package com.example.bai_tap_1.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_1.ui.theme.*
import com.example.bai_tap_1.viewmodel.AuthViewModel

@Composable
fun TaskDetailScreen(
    taskId: String,
    viewModel: AuthViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val task = viewModel.tasks.find { it.id == taskId } ?: viewModel.tasks.firstOrNull()
    var newSubtaskInput by remember { mutableStateOf("") }

    var editingSubtaskIndex by remember { mutableStateOf<Int?>(null) }
    var editingSubtaskText by remember { mutableStateOf("") }

    if (task == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Không tìm thấy công việc", color = UthSubText)
        }
        return
    }

    val completedSubtasksCount = task.subtasks.count { it.isDone }
    val totalSubtasksCount = task.subtasks.size

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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onBack() }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = UthPrimaryBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Danh sách",
                                color = UthPrimaryBlue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Text(
                            text = "Sửa",
                            color = UthPrimaryBlue,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    Toast.makeText(context, "Mở chế độ chỉnh sửa!", Toast.LENGTH_SHORT).show()
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (task.category == "Học tập") UthTagBlueBg else UthTagOrangeBg)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = task.category,
                                color = if (task.category == "Học tập") UthPrimaryBlue else UthTagOrangeText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFF1F5F9))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Ưu tiên ${task.priority}",
                                color = UthSubText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = task.title,
                        color = UthDarkText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarToday,
                                        contentDescription = null,
                                        tint = UthSubText,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text("Hạn chót", color = UthDarkText, fontSize = 14.sp)
                                }
                                Text("Hôm nay, ${task.time}", color = UthDarkText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
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
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text("Nhắc nhở", color = UthDarkText, fontSize = 14.sp)
                                }
                                Text(task.remindText, color = UthDarkText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Ghi chú", color = UthSubText, fontSize = 12.sp)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Text(
                                text = task.note.ifBlank { "Chưa có ghi chú" },
                                color = UthDarkText,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(14.dp),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Việc nhỏ", color = UthDarkText, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text("$completedSubtasksCount/$totalSubtasksCount", color = UthSubText, fontSize = 13.sp)
                        }

                        val progressFraction = if (totalSubtasksCount > 0) completedSubtasksCount.toFloat() / totalSubtasksCount else 0f
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = UthPrimaryBlue,
                            trackColor = Color(0xFFE2E8F0)
                        )
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column {
                            task.subtasks.forEachIndexed { idx, subtask ->
                                val isEditingThis = editingSubtaskIndex == idx

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp)
                                        .padding(horizontal = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (subtask.isDone) UthPrimaryBlue else Color.White)
                                            .border(
                                                width = if (subtask.isDone) 0.dp else 1.5.dp,
                                                color = if (subtask.isDone) Color.Transparent else UthBorderColor,
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                            .clickable { viewModel.toggleSubtask(task.id, idx) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (subtask.isDone) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }

                                    if (isEditingThis) {
                                        TextField(
                                            value = editingSubtaskText,
                                            onValueChange = { editingSubtaskText = it },
                                            modifier = Modifier.weight(1f),
                                            colors = TextFieldDefaults.colors(
                                                focusedContainerColor = Color.Transparent,
                                                unfocusedContainerColor = Color.Transparent,
                                                focusedIndicatorColor = UthPrimaryBlue,
                                                unfocusedIndicatorColor = Color.Transparent
                                            ),
                                            singleLine = true
                                        )

                                        TextButton(
                                            onClick = {
                                                if (editingSubtaskText.isNotBlank()) {
                                                    viewModel.updateSubtaskTitle(task.id, idx, editingSubtaskText)
                                                }
                                                editingSubtaskIndex = null
                                            }
                                        ) {
                                            Text("Lưu", color = UthPrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        }
                                    } else {
                                        Text(
                                            text = subtask.title,
                                            color = if (subtask.isDone) Color(0xFF94A3B8) else UthDarkText,
                                            fontSize = 13.sp,
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable {
                                                    editingSubtaskIndex = idx
                                                    editingSubtaskText = subtask.title
                                                },
                                            textDecoration = if (subtask.isDone) TextDecoration.LineThrough else TextDecoration.None
                                        )

                                        IconButton(
                                            onClick = {
                                                editingSubtaskIndex = idx
                                                editingSubtaskText = subtask.title
                                            },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Default.Edit, contentDescription = null, tint = UthSubText, modifier = Modifier.size(16.dp))
                                        }

                                        IconButton(
                                            onClick = { viewModel.deleteSubtask(task.id, idx) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Default.DeleteOutline, contentDescription = null, tint = Color(0xFFE11D48), modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }

                                HorizontalDivider(color = UthBorderColor.copy(alpha = 0.3f))
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .padding(horizontal = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = UthPrimaryBlue,
                                    modifier = Modifier.size(20.dp)
                                )

                                TextField(
                                    value = newSubtaskInput,
                                    onValueChange = { newSubtaskInput = it },
                                    placeholder = { Text("Thêm việc nhỏ", color = UthSubText, fontSize = 14.sp) },
                                    modifier = Modifier.weight(1f),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    singleLine = true
                                )

                                TextButton(
                                    onClick = {
                                        if (newSubtaskInput.isNotBlank()) {
                                            viewModel.addSubtaskToTask(task.id, newSubtaskInput)
                                            newSubtaskInput = ""
                                        }
                                    }
                                ) {
                                    Text("Thêm", color = UthSubText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModel.toggleTaskCompletion(task.id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = UthPrimaryBlue)
                ) {
                    Text(
                        text = if (task.isCompleted) "Đã hoàn thành" else "Đánh dấu hoàn thành",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Xóa công việc",
                    color = Color(0xFFE11D48),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            viewModel.deleteTask(task.id) {
                                Toast.makeText(context, "Đã xóa công việc!", Toast.LENGTH_SHORT).show()
                                onBack()
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskDetailScreenPreview() {
    val dummyVm = remember { AuthViewModel() }
    TaskDetailScreen(taskId = "1", viewModel = dummyVm, onBack = {})
}
