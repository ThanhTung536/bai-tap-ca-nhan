package com.example.studentprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentProfileScreen()
        }
    }
}

@Composable
fun StudentProfileScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp
                ),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(12.dp)
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "←",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    modifier = Modifier.offset(y = (-5).dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(12.dp)
                    ),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Chỉnh sửa",
                    tint = Color(0xFF0D9488),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 250.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.avatar
                ),

                contentDescription = "Ảnh đại diện",

                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),

                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Hoàng Thanh Tùng",

                fontSize = 21.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF171717)
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "031206000267",

                fontSize = 15.sp,

                color = Color(0xFF777777)
            )
        }
    }
}