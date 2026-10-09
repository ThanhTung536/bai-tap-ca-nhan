package com.example.bai_tap_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_2.ui.theme.Bai_tap_2Theme

// Tạo data class Student
data class Student(
    val name: String,
    val email: String?,
    val phone: String?
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bai_tap_2Theme {
                StudentScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen() {
    val studentList = listOf(
        Student("Nguyễn Văn An", "an@sv.edu.vn", "0901 234 567"),
        Student("Trần Thị Bình", null, "0912 888 999"),
        Student("Lê Minh Chi", "chi@sv.edu.vn", null),
        Student("Phạm Quốc Dũng", null, null),
        Student("Võ Thanh Em", "em@sv.edu.vn", "0933 111 222"),
    )

    // Dùng vòng lặp for để đếm số sinh viên thiếu email
    var missingEmailCount = 0
    for (student in studentList) {
        if (student.email == null) {
            missingEmailCount++
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Danh sách sinh viên", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Danh sách các thẻ sinh viên dùng vòng lặp for
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (student in studentList) {
                    ContainerCard(student = student)
                }
            }

            // Hiển thị tổng kết ở cuối danh sách
            ContainerSummary(total = studentList.size, missingEmail = missingEmailCount)
        }
    }
}

@Composable
fun ContainerCard(student: Student) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = student.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Xử lý email bị null bằng toán tử ?:
            val displayEmail = student.email ?: "Chưa cập nhật"
            Row {
                Text(text = "Email: ", fontSize = 14.sp, color = Color.Gray)
                Text(
                    text = displayEmail,
                    fontSize = 14.sp,
                    color = if (student.email == null) Color.Red else Color.Black
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Xử lý số điện thoại bị null bằng toán tử ?:
            val displayPhone = student.phone ?: "Chưa cập nhật"
            Row {
                Text(text = "SĐT: ", fontSize = 14.sp, color = Color.Gray)
                Text(
                    text = displayPhone,
                    fontSize = 14.sp,
                    color = if (student.phone == null) Color.Red else Color.Black
                )
            }
        }
    }
}

@Composable
fun ContainerSummary(total: Int, missingEmail: Int) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFE5E7EB),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tổng: $total sinh viên",
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Row {
                Text(text = "Thiếu email: ", color = Color.Black)
                Text(
                    text = "$missingEmail",
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Bai_tap_2Theme {
        StudentScreen()
    }
}