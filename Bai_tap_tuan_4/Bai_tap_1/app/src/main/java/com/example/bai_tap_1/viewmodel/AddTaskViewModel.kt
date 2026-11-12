package com.example.bai_tap_1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddTaskViewModel : ViewModel() {

    var taskTitle by mutableStateOf("")
        private set

    var taskNote by mutableStateOf("")
        private set

    var selectedCategory by mutableStateOf("Học tập")
        private set

    var selectedDate by mutableStateOf("Hôm nay")
        private set

    var taskTime by mutableStateOf("19:00")
        private set

    var selectedHour by mutableStateOf("19")
        private set

    var selectedMinute by mutableStateOf("00")
        private set

    var isTimePopupExpanded by mutableStateOf(false)
        private set

    var isReminderEnabled by mutableStateOf(true)
        private set

    var selectedPriority by mutableStateOf("Vừa")
        private set

    var isSubmitting by mutableStateOf(false)
        private set

    val isFormValid: Boolean
        get() = taskTitle.isNotBlank() && !isSubmitting

    fun onTitleChange(newTitle: String) {
        taskTitle = newTitle
    }

    fun onNoteChange(newNote: String) {
        taskNote = newNote
    }

    fun onCategoryChange(newCategory: String) {
        selectedCategory = newCategory
    }

    fun onDateChange(newDate: String) {
        selectedDate = newDate
    }

    fun toggleTimePopup(expanded: Boolean = !isTimePopupExpanded) {
        isTimePopupExpanded = expanded
    }

    fun onHourSelected(hour: String) {
        selectedHour = hour
        taskTime = "$selectedHour:$selectedMinute"
    }

    fun onMinuteSelected(minute: String) {
        selectedMinute = minute
        taskTime = "$selectedHour:$selectedMinute"
    }

    fun onReminderChange(enabled: Boolean) {
        isReminderEnabled = enabled
    }

    fun onPriorityChange(newPriority: String) {
        selectedPriority = newPriority
    }

    fun resetForm() {
        taskTitle = ""
        taskNote = ""
        selectedCategory = "Học tập"
        selectedDate = "Hôm nay"
        selectedHour = "19"
        selectedMinute = "00"
        taskTime = "19:00"
        isTimePopupExpanded = false
        isReminderEnabled = true
        selectedPriority = "Vừa"
        isSubmitting = false
    }

    fun submitNewTask(
        authViewModel: AuthViewModel,
        onComplete: () -> Unit
    ) {
        if (taskTitle.isBlank() || isSubmitting) return

        val title = taskTitle.trim()
        val note = taskNote.trim()
        val category = selectedCategory
        val date = selectedDate
        val time = taskTime
        val priority = selectedPriority
        val remind = isReminderEnabled

        isSubmitting = true

        authViewModel.addNewTask(
            title = title,
            category = category,
            time = time,
            note = note,
            date = date,
            priority = priority.lowercase(),
            isRemindEnabled = remind
        )

        authViewModel.showTopNotification("Đã thêm công việc mới thành công!")
        resetForm()
        onComplete()
    }
}
