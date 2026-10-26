package com.example.bai_tap_3.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UTHLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "UTH",
            fontSize = 38.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF136C6C),
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "UNIVERSITY\nOF TRANSPORT\nHOCHIMINH CITY",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD32F2F),
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
            letterSpacing = 0.2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "SmartTasks",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF29B6F6)
        )
    }
}
