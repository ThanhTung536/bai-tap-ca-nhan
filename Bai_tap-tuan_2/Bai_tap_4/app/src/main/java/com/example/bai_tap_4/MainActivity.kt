package com.example.bai_tap_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_4.ui.theme.Bai_tap_4Theme

// Tạo class Student
data class Student(
    val name: String,
    val score: Double?
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bai_tap_4Theme {
                ClassGradeScreen()
            }
        }
    }
}

// Hàm xếp loại học lực từ điểm số
fun getClassification(score: Double?): String {
    if (score == null) return "Chưa có điểm"
    return if (score < 0.0 || score > 10.0) {
        "Không hợp lệ"
    } else if (score >= 8.0) {
        "Giỏi"
    } else if (score >= 6.5) {
        "Khá"
    } else if (score >= 5.0) {
        "Trung bình"
    } else {
        "Yếu"
    }
}

// Hàm chọn màu chữ cho xếp loại
fun getClassificationColor(classification: String): Color {
    return when (classification) {
        "Giỏi" -> Color(0xFF2E7D32)
        "Khá" -> Color(0xFF1565C0)
        "Trung bình" -> Color(0xFFEF6C00)
        "Yếu" -> Color(0xFFC62828)
        else -> Color(0xFF757575)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassGradeScreen() {
    // Dữ liệu mẫu
    val studentList = listOf(
        Student("Nguyễn Văn An", 9.0),
        Student("Trần Thị Bình", 7.2),
        Student("Lê Minh Chi", null),
        Student("Phạm Quốc Dũng", 4.5),
        Student("Võ Thanh Em", 5.8)
    )

    var totalScore = 0.0
    var countHasScore = 0
    var excellentCount = 0
    var missingScoreCount = 0
    var maxScore = -1.0

    for (student in studentList) {
        if (student.score == null) {
            missingScoreCount++
        } else {
            val s = student.score
            totalScore += s
            countHasScore++

            // Đếm số sinh viên Giỏi
            if (s >= 8.0) {
                excellentCount++
            }
            // Tìm điểm cao nhất
            if (s > maxScore) {
                maxScore = s
            }
        }
    }

    // Tính điểm trung bình lớp (bỏ qua người chưa có điểm)
    val classAverage = if (countHasScore > 0) totalScore / countHasScore else 0.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Bảng điểm lớp", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E3A8A))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Phần đầu trang
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E3A8A))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Điểm trung bình lớp", fontSize = 12.sp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(4.dp))

                    // Hiển thị điểm trung bình làm tròn 2 chữ số thập phân
                    Text(
                        text = String.format("%.2f", classAverage),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Giỏi", fontSize = 12.sp, color = Color.LightGray)
                            Text(text = "$excellentCount", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Column {
                            Text(text = "Chưa có điểm", fontSize = 12.sp, color = Color.LightGray)
                            Text(text = "$missingScoreCount", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Column {
                            Text(text = "Cao nhất", fontSize = 12.sp, color = Color.LightGray)
                            Text(text = if (maxScore >= 0) "$maxScore" else "-", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            // Tiêu đề danh sách sinh viên
            Text(
                text = "DANH SÁCH - ${studentList.size} SINH VIÊN",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            // Ds các thẻ sv
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (student in studentList) {
                        val classification = getClassification(student.score)
                        val color = getClassificationColor(classification)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFAFAFA))
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = student.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = classification,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = color
                                    )
                                }

                                // Hiển thị điểm số hoặc dấu gạch ngang nếu chưa có điểm
                                Text(
                                    text = if (student.score != null) "${student.score}" else "—",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (student.score != null) Color.Black else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassGradePreview() {
    Bai_tap_4Theme {
        ClassGradeScreen()
    }
}