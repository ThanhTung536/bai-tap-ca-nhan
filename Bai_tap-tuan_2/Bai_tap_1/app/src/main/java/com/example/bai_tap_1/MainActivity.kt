package com.example.bai_tap_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_1.ui.theme.Bai_tap_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bai_tap_1Theme {
                homescreen()
            }
        }
    }
}

@Composable
fun homescreen() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var selectedOperator by remember { mutableStateOf<String?>(null) }

    // Tính toán kết quả
    val result = remember(num1, num2, selectedOperator) {
        val n1 = num1.toDoubleOrNull()
        val n2 = num2.toDoubleOrNull()

        if (n1 != null && n2 != null && selectedOperator != null) {
            when (selectedOperator) {
                "+" -> (n1 + n2).toString().removeSuffix(".0")
                "-" -> (n1 - n2).toString().removeSuffix(".0")
                "*" -> (n1 * n2).toString().removeSuffix(".0")
                "/" -> if (n2 != 0.0) (n1 / n2).toString().removeSuffix(".0") else "Lỗi chia cho 0"
                else -> ""
            }
        } else {
            ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        // Tiêu đề
        Text(
            text = "Thực hành 03",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        // Ô nhập số thứ nhất
        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Nhập số thứ 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hàng nút chọn phép tính (+, -, *, /)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CalculatorButton(symbol = "+", bgColor = Color.Red, isSelect = selectedOperator == "+") {
                selectedOperator = "+"
            }
            CalculatorButton(symbol = "-", bgColor = Color(0xFFFFB74D), isSelect = selectedOperator == "-") {
                selectedOperator = "-"
            }
            CalculatorButton(symbol = "*", bgColor = Color(0xFF7986CB), isSelect = selectedOperator == "*") {
                selectedOperator = "*"
            }
            CalculatorButton(symbol = "/", bgColor = Color.Black, isSelect = selectedOperator == "/") {
                selectedOperator = "/"
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ô nhập số thứ hai
        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Nhập số thứ 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị kết quả
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Kết quả: $result",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Đưa hàm CalculatorButton ra ngoài hẳn các hàm khác (Nằm ngang hàng với homescreen)
@Composable
fun CalculatorButton(
    symbol: String,
    bgColor: Color,
    isSelect: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(8.dp)) // Đã thêm dấu chấm ở đây
            .background(bgColor)
            .then(if (isSelect) Modifier.border(2.dp, Color.Black, RoundedCornerShape(8.dp)) else Modifier)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Bai_tap_1Theme {
        homescreen()
    }
}