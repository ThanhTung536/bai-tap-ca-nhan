package com.example.bai_tap_3

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
import com.example.bai_tap_3.ui.theme.Bai_tap_3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bai_tap_3Theme {
                GradeScreen()
            }
        }
    }
}

// Hàm nhận điểm và trả về xếp loại
fun getClassification(score: Double): String {
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
fun GradeScreen() {
    val mainScore = 8.5
    val mainClassification = getClassification(mainScore)
    val mainColor = getClassificationColor(mainClassification)
    val testScores = listOf(9.0, 7.0, 5.5, 3.0, 11.0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Xếp loại học lực", color = Color.White) },
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
            // Thẻ hiển thị điểm chính (8.5)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Điểm trung bình", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = mainScore.toString(),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(mainColor.copy(alpha = 0.12f))
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mainClassification,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = mainColor
                        )
                    }
                }
            }

            // phần Kiểm tra thêm
            Text(
                text = "KIỂM TRA THÊM",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            // Danh sách các mục kiểm tra thêm
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
                    for (score in testScores) {
                        val classification = getClassification(score)
                        val color = getClassificationColor(classification)
                        val scoreText = if (score % 1.0 == 0.0) "Điểm ${score.toInt()}" else "Điểm $score"

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = scoreText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )

                            Box(
                                modifier = Modifier
                                    .background(color.copy(alpha = 0.1f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = classification,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = color
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
fun GreetingPreview() {
    Bai_tap_3Theme {
        GradeScreen()
    }
}