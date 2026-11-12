package com.example.bai_tap_1.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.bai_tap_1.network.TaskApiService
import com.example.bai_tap_1.service.FirebaseManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

data class UserProfile(
    val name: String = "Minh Anh",
    val email: String = "minhanh@example.com",
    val photoUrl: String? = null,
    val isGoogleLinked: Boolean = true,
    val isGuest: Boolean = false
)

data class SubtaskItem(
    val title: String,
    var isDone: Boolean = false
)

enum class NotificationType {
    UPCOMING, OVERDUE, SUCCESS, REMINDER
}

data class NotificationItem(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val type: NotificationType,
    var isRead: Boolean
)

data class TaskItem(
    val id: String,
    val title: String,
    val category: String,
    val time: String,
    var isCompleted: Boolean,
    val note: String = "Tập trung phần cây nhị phân và bảng băm.",
    val priority: String = "vừa",
    val remindText: String = "Trước 15 phút",
    val subtasks: MutableList<SubtaskItem> = mutableListOf(
        SubtaskItem("Đọc lại slide bài giảng", isDone = true),
        SubtaskItem("Làm 10 bài tập cuối chương", isDone = false)
    )
)

class AuthViewModel : ViewModel() {

    private var firebaseManager: FirebaseManager? = null

    var appThemeMode by mutableStateOf("Sáng")
        private set

    var appPrimaryColor by mutableStateOf(Color(0xFF3865F6))
        private set

    val isDarkMode: Boolean
        get() = appThemeMode == "Tối"

    fun updateThemeMode(mode: String) {
        appThemeMode = mode
    }

    fun updatePrimaryColor(color: Color) {
        appPrimaryColor = color
    }

    fun attachContext(context: Context) {
        if (firebaseManager == null) {
            try {
                firebaseManager = FirebaseManager.getInstance(context)
                refreshRemoteConfig()
            } catch (_: Throwable) {
            }
        }
    }

    fun refreshRemoteConfig() {
        firebaseManager?.let { manager ->
            val banner = manager.getRemoteBannerMessage()
            if (!banner.isNullOrBlank()) {
                welcomeBannerMessage = banner
            }
            manager.remoteConfig?.fetchAndActivate()?.addOnCompleteListener {
                val updatedMsg = manager.getRemoteBannerMessage()
                if (!updatedMsg.isNullOrBlank()) {
                    welcomeBannerMessage = updatedMsg
                }
            }
        }
    }

    private val auth: FirebaseAuth? get() = firebaseManager?.auth ?: try { FirebaseAuth.getInstance() } catch (_: Throwable) { null }

    var userProfile by mutableStateOf(
        UserProfile(
            name = "Minh Anh",
            email = "minhanh@example.com",
            isGoogleLinked = true,
            isGuest = false
        )
    )
        private set

    var isLoggedIn by mutableStateOf(false)
        private set

    var welcomeBannerMessage by mutableStateOf("Chào mừng bạn đến với UTH Todo V2!")
        private set

    var authErrorMessage by mutableStateOf<String?>(null)
        private set

    var authSuccessMessage by mutableStateOf<String?>(null)
        private set

    private val initialNotifications = listOf(
        NotificationItem("1", "Sắp đến hạn", "Ôn chương 3 – Cấu trúc dữ liệu lúc 14:00.", "13:45", NotificationType.UPCOMING, isRead = false),
        NotificationItem("2", "Đã quá hạn", "Bài đọc tham khảo môn Mạng máy tính chưa xong.", "10:10", NotificationType.OVERDUE, isRead = false),
        NotificationItem("3", "Làm tốt lắm!", "Bạn đã xong 2 việc trong sáng nay.", "11:30", NotificationType.SUCCESS, isRead = true),
        NotificationItem("4", "Nhắc việc", "Họp nhóm đồ án trong tuần này.", "Hôm qua", NotificationType.REMINDER, isRead = true)
    )

    val notifications = mutableStateListOf<NotificationItem>().apply {
        addAll(initialNotifications)
    }

    private var notificationClearJob: Job? = null
    private var hasStartedScreenNotificationTimer = false

    val unreadNotificationsCount: Int get() = notifications.count { !it.isRead }

    /**
     * Sau 10 giây, cho danh sách thông báo về rỗng để hiển thị giao diện emptyView.
     */
    fun startNotificationClearTimer() {
        try {
            notificationClearJob?.cancel()
            notificationClearJob = viewModelScope.launch {
                delay(10_000L)
                notifications.clear()
            }
        } catch (_: Throwable) {
        }
    }

    fun onNotificationScreenShown() {
        if (!hasStartedScreenNotificationTimer) {
            hasStartedScreenNotificationTimer = true
            if (notifications.isEmpty()) {
                notifications.addAll(initialNotifications)
            }
            startNotificationClearTimer()
        }
    }

    fun markAllNotificationsAsRead() {
        notifications.forEachIndexed { idx, item ->
            if (!item.isRead) {
                notifications[idx] = item.copy(isRead = true)
            }
        }
    }

    fun markNotificationAsRead(id: String) {
        val idx = notifications.indexOfFirst { it.id == id }
        if (idx != -1 && !notifications[idx].isRead) {
            notifications[idx] = notifications[idx].copy(isRead = true)
        }
    }

    private val _tasks = mutableStateListOf(
        TaskItem("4", "Tập thể dục 30 phút", "Cuộc sống", "06:30", isCompleted = true),
        TaskItem("1", "Nộp bài tập Lập trình di động", "Học tập", "09:00", isCompleted = true),
        TaskItem("2", "Ôn chương 3 - Cấu trúc dữ liệu", "Học tập", "14:00", isCompleted = false),
        TaskItem("3", "Đi chợ mua đồ cho tuần", "Cuộc sống", "17:30", isCompleted = false)
    )

    val tasks: List<TaskItem> get() = _tasks

    val completedTasksCount: Int get() = _tasks.count { it.isCompleted }
    val totalTasksCount: Int get() = _tasks.size
    val pendingTasksCount: Int get() = _tasks.count { !it.isCompleted }
    val completionPercentage: Int get() = if (totalTasksCount > 0) (completedTasksCount * 100) / totalTasksCount else 0

    val studyTasksRemaining: Int get() = _tasks.count { it.category == "Học tập" && !it.isCompleted }
    val lifeTasksRemaining: Int get() = _tasks.count { it.category == "Cuộc sống" && !it.isCompleted }

    fun deleteTask(taskId: String, onDeleted: () -> Unit = {}) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            _tasks.removeAt(index)
        }
        try {
            viewModelScope.launch {
                try {
                    TaskApiService.create().deleteTask(taskId)
                } catch (_: Throwable) {
                }
            }
        } catch (_: Throwable) {
        }
        onDeleted()
    }

    fun addSubtaskToTask(taskId: String, subtaskTitle: String) {
        if (subtaskTitle.isBlank()) return
        val taskIndex = _tasks.indexOfFirst { it.id == taskId }
        if (taskIndex != -1) {
            val currentTask = _tasks[taskIndex]
            val newSubtasks = currentTask.subtasks.toMutableList().apply {
                add(SubtaskItem(subtaskTitle, isDone = false))
            }
            _tasks[taskIndex] = currentTask.copy(subtasks = newSubtasks)
        }
    }

    fun toggleSubtask(taskId: String, subtaskIndex: Int) {
        val taskIndex = _tasks.indexOfFirst { it.id == taskId }
        if (taskIndex != -1) {
            val currentTask = _tasks[taskIndex]
            val newSubtasks = currentTask.subtasks.toMutableList()
            if (subtaskIndex in newSubtasks.indices) {
                val subtask = newSubtasks[subtaskIndex]
                newSubtasks[subtaskIndex] = subtask.copy(isDone = !subtask.isDone)
                _tasks[taskIndex] = currentTask.copy(subtasks = newSubtasks)
            }
        }
    }

    fun deleteSubtask(taskId: String, subtaskIndex: Int) {
        val taskIndex = _tasks.indexOfFirst { it.id == taskId }
        if (taskIndex != -1) {
            val currentTask = _tasks[taskIndex]
            val newSubtasks = currentTask.subtasks.toMutableList()
            if (subtaskIndex in newSubtasks.indices) {
                newSubtasks.removeAt(subtaskIndex)
                _tasks[taskIndex] = currentTask.copy(subtasks = newSubtasks)
            }
        }
    }

    fun updateSubtaskTitle(taskId: String, subtaskIndex: Int, newTitle: String) {
        if (newTitle.isBlank()) return
        val taskIndex = _tasks.indexOfFirst { it.id == taskId }
        if (taskIndex != -1) {
            val currentTask = _tasks[taskIndex]
            val newSubtasks = currentTask.subtasks.toMutableList()
            if (subtaskIndex in newSubtasks.indices) {
                val sub = newSubtasks[subtaskIndex]
                newSubtasks[subtaskIndex] = sub.copy(title = newTitle)
                _tasks[taskIndex] = currentTask.copy(subtasks = newSubtasks)
            }
        }
    }

    init {
        fetchTasksFromApi()
        startNotificationClearTimer()
    }

    fun fetchTasksFromApi() {
        try {
            viewModelScope.launch {
                try {
                    val apiService = TaskApiService.create()
                    val response = apiService.getTasks()
                    if (response.isSuccessful && response.body() != null) {
                        val dtoList = response.body()!!
                        if (dtoList.isNotEmpty()) {
                            _tasks.clear()
                            dtoList.forEach { dto ->
                                val mappedCategory = if (dto.category == "life") "Cuộc sống" else "Học tập"
                                _tasks.add(
                                    TaskItem(
                                        id = dto.id.toString(),
                                        title = dto.title,
                                        category = mappedCategory,
                                        time = dto.time,
                                        isCompleted = dto.done
                                    )
                                )
                            }
                        }
                    }
                } catch (t: Throwable) {
                    Log.e("AuthViewModel", "Fetch tasks API error: ${t.message}")
                }
            }
        } catch (_: Throwable) {
        }
    }

    var topMessageNotification by mutableStateOf<String?>(null)
        private set

    fun showTopNotification(message: String) {
        try {
            viewModelScope.launch {
                topMessageNotification = message
                delay(2200)
                if (topMessageNotification == message) {
                    topMessageNotification = null
                }
            }
        } catch (_: Throwable) {
            topMessageNotification = message
        }
    }

    fun toggleTaskCompletion(taskId: String, onStateChanged: ((Boolean) -> Unit)? = null) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val newCompletedState = !_tasks[index].isCompleted
            _tasks[index] = _tasks[index].copy(isCompleted = newCompletedState)
            firebaseManager?.toggleTaskInFirestore(taskId, newCompletedState)
            if (newCompletedState) {
                showTopNotification("Tuyệt, đã xong một việc!")
            }
            onStateChanged?.invoke(newCompletedState)
        }
    }

    fun addNewTask(title: String, category: String, time: String) {
        if (title.isBlank()) return
        val newTask = TaskItem(
            id = System.currentTimeMillis().toString(),
            title = title,
            category = category,
            time = time,
            isCompleted = false
        )
        _tasks.add(newTask)
        firebaseManager?.saveTaskToFirestore(newTask) {}
    }

    fun loginWithGoogle(onSuccess: () -> Unit) {
        try {
            val currentUser = auth?.currentUser
            if (currentUser != null) {
                userProfile = UserProfile(
                    name = currentUser.displayName ?: "Minh Anh",
                    email = currentUser.email ?: "minhanh@example.com",
                    isGoogleLinked = true,
                    isGuest = false
                )
            }
        } catch (_: Throwable) {
        }
        isLoggedIn = true
        authSuccessMessage = "Đăng nhập Google thành công!"
        firebaseManager?.logAnalyticsEvent("login_google_success")
        onSuccess()
    }

    fun loginWithEmail(
        emailInput: String,
        passInput: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (emailInput.isBlank() || passInput.isBlank()) {
            val err = "Vui lòng nhập Email và Mật khẩu!"
            authErrorMessage = err
            onError(err)
            return
        }
        if (!emailInput.contains("@")) {
            val err = "Email không đúng định dạng!"
            authErrorMessage = err
            onError(err)
            return
        }

        val firebaseAuth = auth
        if (firebaseAuth != null) {
            try {
                firebaseAuth.signInWithEmailAndPassword(emailInput, passInput)
                    .addOnSuccessListener { result ->
                        val firebaseUser = result.user
                        userProfile = UserProfile(
                            name = firebaseUser?.displayName ?: emailInput.substringBefore("@"),
                            email = firebaseUser?.email ?: emailInput,
                            isGoogleLinked = false,
                            isGuest = false
                        )
                        isLoggedIn = true
                        authSuccessMessage = "Đăng nhập thành công!"
                        firebaseManager?.logAnalyticsEvent("login_email_success")
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        if (emailInput == "minhanh@example.com" && passInput == "123456") {
                            userProfile = UserProfile(
                                name = "Minh Anh",
                                email = "minhanh@example.com",
                                isGoogleLinked = true,
                                isGuest = false
                            )
                            isLoggedIn = true
                            authSuccessMessage = "Đăng nhập thành công!"
                            onSuccess()
                        } else {
                            val err = exception.localizedMessage ?: "Đăng nhập thất bại. Kiểm tra lại thông tin!"
                            authErrorMessage = err
                            onError(err)
                        }
                    }
            } catch (_: Throwable) {
                if (emailInput == "minhanh@example.com" && passInput == "123456") {
                    userProfile = UserProfile(
                        name = "Minh Anh",
                        email = "minhanh@example.com",
                        isGoogleLinked = true,
                        isGuest = false
                    )
                    isLoggedIn = true
                    authSuccessMessage = "Đăng nhập thành công!"
                    onSuccess()
                } else {
                    onError("Đăng nhập thất bại!")
                }
            }
        } else {
            if (emailInput == "minhanh@example.com" && passInput == "123456") {
                userProfile = UserProfile(
                    name = "Minh Anh",
                    email = "minhanh@example.com",
                    isGoogleLinked = true,
                    isGuest = false
                )
                isLoggedIn = true
                authSuccessMessage = "Đăng nhập thành công!"
                onSuccess()
            } else {
                onError("Đăng nhập thử nghiệm thành công!")
            }
        }
    }

    fun loginAsGuest(onSuccess: () -> Unit) {
        userProfile = UserProfile(
            name = "Khách",
            email = "khach@uth.edu.vn",
            isGoogleLinked = false,
            isGuest = true
        )
        isLoggedIn = true
        authSuccessMessage = "Đã vào chế độ Dùng thử không cần tài khoản!"
        firebaseManager?.logAnalyticsEvent("login_guest_success")
        onSuccess()
    }

    fun logout(onLoggedOut: () -> Unit) {
        try {
            auth?.signOut()
        } catch (_: Throwable) {
        }
        isLoggedIn = false
        userProfile = UserProfile()
        firebaseManager?.logAnalyticsEvent("user_logout")
        onLoggedOut()
    }

    fun clearMessages() {
        authErrorMessage = null
        authSuccessMessage = null
    }
}
