package com.example.bai_tap_2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_2.model.LibraryViewModel
import com.example.bai_tap_2.model.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementScreen(viewModel: LibraryViewModel) {
    val context = LocalContext.current
    val currentStudent = viewModel.selectedStudent
    val allBooks = viewModel.books
    val allStudents = viewModel.students

    // State chọn sách trong giao diện mượn
    val selectedBookIds = remember(currentStudent.id) {
        mutableStateListOf<String>().apply {
            addAll(currentStudent.borrowedBookIds)
        }
    }

    var showChangeStudentDialog by remember { mutableStateOf(false) }
    var studentNameInput by remember { mutableStateOf(currentStudent.name) }

    LaunchedEffect(currentStudent.name) {
        studentNameInput = currentStudent.name
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hệ thống\nQuản lý Thư viện",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Sinh viên",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = currentStudent.name,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { showChangeStudentDialog = true },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2B52B8)
                    ),
                    modifier = Modifier.height(52.dp)
                ) {
                    Text(
                        text = "Thay đổi",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Danh sách sách",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color(0xFFDCDCDC), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(allBooks) { book ->
                        val isChecked = selectedBookIds.contains(book.id)

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(2.dp, shape = RoundedCornerShape(16.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFEEEEEE),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    if (isChecked) {
                                        selectedBookIds.remove(book.id)
                                    } else {
                                        selectedBookIds.add(book.id)
                                    }
                                },
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { checked ->
                                        if (checked == true) {
                                            selectedBookIds.add(book.id)
                                        } else {
                                            selectedBookIds.remove(book.id)
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFFA52A2A),
                                        uncheckedColor = Color.Gray
                                    )
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = book.title,
                                    fontSize = 16.sp,
                                    color = Color(0xFF333333),
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                viewModel.updateBorrowState(selectedBookIds)
                Toast.makeText(
                    context,
                    "Đã lưu danh sách mượn sách cho ${currentStudent.name}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2B52B8)
            ),
            modifier = Modifier
                .width(160.dp)
                .height(48.dp)
        ) {
            Text(
                text = "Thêm",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    if (showChangeStudentDialog) {
        AlertDialog(
            onDismissRequest = { showChangeStudentDialog = false },
            title = {
                Text(
                    text = "Chọn hoặc sửa tên Sinh viên",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "Chọn sinh viên có sẵn:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                            .fillMaxWidth()
                    ) {
                        items(allStudents) { student ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.selectStudent(student)
                                        studentNameInput = student.name
                                    }
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (student.id == currentStudent.id),
                                    onClick = {
                                        viewModel.selectStudent(student)
                                        studentNameInput = student.name
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "${student.name} (${student.id})")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Hoặc đổi tên cho sinh viên hiện tại:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = studentNameInput,
                        onValueChange = { studentNameInput = it },
                        label = { Text("Tên sinh viên") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (studentNameInput.isNotBlank()) {
                            viewModel.updateSelectedStudentName(studentNameInput)
                        }
                        showChangeStudentDialog = false
                    }
                ) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showChangeStudentDialog = false }
                ) {
                    Text("Hủy")
                }
            }
        )
    }
}
