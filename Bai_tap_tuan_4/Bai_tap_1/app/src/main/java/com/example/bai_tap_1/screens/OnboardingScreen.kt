package com.example.bai_tap_1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_tap_1.model.*
import com.example.bai_tap_1.ui.theme.*
import com.example.bai_tap_1.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onFinish: () -> Unit
) {
    val step = viewModel.currentStep

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = UthBgLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    UthTodoLogoIcon()
                    UthTodoTitle()
                }
                Text(
                    text = "Bỏ qua",
                    color = UthSubText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { viewModel.skipOnboarding(onFinish) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .width(230.dp)
                            .height(260.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = UthCardBg),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            IllustrationComposable(illustration = step.illustration)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = step.title,
                    color = UthDarkText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = step.description,
                    color = UthSubText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    repeat(viewModel.totalSteps) { index ->
                        val isSelected = index == viewModel.currentStepIndex
                        Box(
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .height(6.dp)
                                .width(if (isSelected) 24.dp else 6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    color = if (isSelected) UthPrimaryBlue else UthBorderColor
                                )
                                .clickable { viewModel.goToStep(index) }
                        )
                    }
                }

                Button(
                    onClick = { viewModel.onNextClicked(onFinish) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = UthPrimaryBlue)
                ) {
                    Text(
                        text = step.buttonText,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun UthTodoLogoIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(UthPrimaryBlue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = UthPrimaryBlue,
                        modifier = Modifier.size(7.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .height(3.5.dp)
                        .width(14.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.4f))
                )
                Box(
                    modifier = Modifier
                        .height(3.5.dp)
                        .width(10.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f))
                )
            }
        }
    }
}

@Composable
fun UthTodoTitle(fontSize: TextUnit = 18.sp) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = UthDarkText, fontWeight = FontWeight.Bold)) {
                append("UTH ")
            }
            withStyle(style = SpanStyle(color = UthPrimaryBlue, fontWeight = FontWeight.Medium)) {
                append("Todo")
            }
        },
        fontSize = fontSize
    )
}

@Composable
fun IllustrationComposable(illustration: IllustrationContent) {
    when (illustration) {
        is ChecklistIllustration -> {
            ChecklistIllustrationComposable(illustration)
        }
        is CategoryIllustration -> {
            CategoryIllustrationComposable(illustration)
        }
        is NotificationIllustration -> {
            NotificationIllustrationComposable(illustration)
        }
    }
}

@Composable
fun ChecklistIllustrationComposable(data: ChecklistIllustration) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        data.items.forEachIndexed { index, item ->
            val lineWidth = when (index) {
                0 -> 95.dp
                1 -> 65.dp
                else -> 80.dp
            }

            Card(
                modifier = Modifier
                    .width(180.dp)
                    .shadow(4.dp, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (item.isCompleted) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(UthPrimaryBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(11.dp)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(1.5.dp, UthBorderColor, CircleShape)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(7.dp)
                            .width(lineWidth)
                            .clip(RoundedCornerShape(3.5.dp))
                            .background(Color(0xFFE2E8F0))
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryIllustrationComposable(data: CategoryIllustration) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        data.categories.forEach { category ->
            val isPrimary = category.isPrimary
            val pillBgColor = if (isPrimary) UthTagBlueBg else UthTagOrangeBg
            val textColor = if (isPrimary) UthPrimaryBlue else UthTagOrangeText
            val lineBarWidth = if (isPrimary) 100.dp else 75.dp

            Card(
                modifier = Modifier
                    .width(180.dp)
                    .shadow(4.dp, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(pillBgColor)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = category.name,
                            color = textColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(7.dp)
                            .width(lineBarWidth)
                            .clip(RoundedCornerShape(3.5.dp))
                            .background(Color(0xFFE2E8F0))
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationIllustrationComposable(data: NotificationIllustration) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = UthPrimaryBlue,
                modifier = Modifier.size(36.dp)
            )
        }

        Card(
            modifier = Modifier
                .wrapContentSize()
                .shadow(6.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = data.reminder.title,
                color = UthDarkText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
            )
        }
    }
}
