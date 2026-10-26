package com.example.bai_tap_3.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_3.model.ResetPasswordData

@Composable
fun SubmittedDataCard(
    data: ResetPasswordData,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFDCEEFE),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(vertical = 20.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val displayEmail = data.email.ifBlank { "uth@gmail.com" }
            val displayOtp = data.otp.ifBlank { "123456" }
            val displayPass = data.password.ifBlank { "123456" }

            Text(
                text = displayEmail,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFC62828)
            )

            Text(
                text = displayOtp,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFC62828)
            )

            Text(
                text = displayPass,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFC62828)
            )
        }
    }
}
