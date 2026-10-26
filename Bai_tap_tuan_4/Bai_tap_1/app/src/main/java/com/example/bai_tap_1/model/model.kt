package com.example.bai_tap_1.model

sealed interface IllustrationContent
data class ChecklistItem(
    val id: String,
    val title: String,
    val isCompleted: Boolean
)

data class ChecklistIllustration(
    val items: List<ChecklistItem>
) : IllustrationContent

data class CategoryTag(
    val id: String,
    val name: String,
    val isPrimary: Boolean = false
)

data class CategoryIllustration(
    val categories: List<CategoryTag>
) : IllustrationContent

data class NotificationReminder(
    val title: String,
    val time: String
)

data class NotificationIllustration(
    val reminder: NotificationReminder
) : IllustrationContent


data class OnboardingStep(
    val stepIndex: Int,
    val title: String,
    val description: String,
    val buttonText: String,
    val illustration: IllustrationContent
)
