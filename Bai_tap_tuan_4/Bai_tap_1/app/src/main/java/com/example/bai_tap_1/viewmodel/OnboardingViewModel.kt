package com.example.bai_tap_1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bai_tap_1.model.*

class OnboardingViewModel : ViewModel() {

    private val _steps: List<OnboardingStep> = listOf(
        OnboardingStep(
            stepIndex = 0,
            title = "Mọi việc, gọn trong một chỗ",
            description = "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.",
            buttonText = "Tiếp tục",
            illustration = ChecklistIllustration(
                items = listOf(
                    ChecklistItem("1", "Bài tập Kotlin", isCompleted = true),
                    ChecklistItem("2", "Dọn dẹp phòng học", isCompleted = false),
                    ChecklistItem("3", "Họp nhóm đồ án", isCompleted = false)
                )
            )
        ),
        OnboardingStep(
            stepIndex = 1,
            title = "Chia theo Học tập và\nCuộc sống",
            description = "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì.",
            buttonText = "Tiếp tục",
            illustration = CategoryIllustration(
                categories = listOf(
                    CategoryTag("cat1", "Học tập", isPrimary = true),
                    CategoryTag("cat2", "Cuộc sống", isPrimary = false)
                )
            )
        ),
        OnboardingStep(
            stepIndex = 2,
            title = "Nhắc đúng lúc",
            description = "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.",
            buttonText = "Bắt đầu ngay",
            illustration = NotificationIllustration(
                reminder = NotificationReminder(
                    title = "Ôn bài Toán lúc 19:00",
                    time = "19:00"
                )
            )
        )
    )

    val steps: List<OnboardingStep> get() = _steps

    var currentStepIndex by mutableIntStateOf(0)
        private set

    val currentStep: OnboardingStep
        get() = _steps.getOrElse(currentStepIndex) { _steps[0] }

    val totalSteps: Int
        get() = _steps.size

    fun onNextClicked(onFinished: () -> Unit) {
        if (currentStepIndex < _steps.size - 1) {
            currentStepIndex++
        } else {
            onFinished()
        }
    }

    fun skipOnboarding(onSkipped: () -> Unit) {
        onSkipped()
    }

    fun goToStep(index: Int) {
        if (index in _steps.indices) {
            currentStepIndex = index
        }
    }

    fun reset() {
        currentStepIndex = 0
    }
}
