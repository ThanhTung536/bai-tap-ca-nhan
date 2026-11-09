package com.example.bai_tap_1.service

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.bai_tap_1.viewmodel.TaskItem
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.appindexing.Action
import com.google.firebase.appindexing.FirebaseAppIndex
import com.google.firebase.appindexing.FirebaseUserActions
import com.google.firebase.appindexing.Indexable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.storage.FirebaseStorage

class FirebaseManager private constructor(context: Context) {

    private val appContext = context.applicationContext

    val auth: FirebaseAuth?
        get() = try { FirebaseAuth.getInstance() } catch (_: Throwable) { null }

    val firestore: FirebaseFirestore?
        get() = try { FirebaseFirestore.getInstance() } catch (_: Throwable) { null }

    val databaseUrl = "https://uth-todo-889f9-default-rtdb.asia-southeast1.firebasedatabase.app"

    val realtimeDb: FirebaseDatabase?
        get() = try {
            FirebaseDatabase.getInstance(databaseUrl)
        } catch (_: Throwable) {
            try { FirebaseDatabase.getInstance() } catch (_: Throwable) { null }
        }

    val storage: FirebaseStorage?
        get() = try { FirebaseStorage.getInstance() } catch (_: Throwable) { null }

    val remoteConfig: FirebaseRemoteConfig?
        get() = try { FirebaseRemoteConfig.getInstance() } catch (_: Throwable) { null }

    val messaging: FirebaseMessaging?
        get() = try { FirebaseMessaging.getInstance() } catch (_: Throwable) { null }

    val analytics: FirebaseAnalytics?
        get() = try { FirebaseAnalytics.getInstance(appContext) } catch (_: Throwable) { null }

    private var taskListenerRegistration: ListenerRegistration? = null

    init {
        try {
            if (FirebaseApp.getApps(appContext).isEmpty()) {
                FirebaseApp.initializeApp(appContext)
            }
            analytics?.setAnalyticsCollectionEnabled(true)
            logAnalyticsEvent("app_launched")
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "FirebaseApp init error: ${t.message}")
        }
        setupRemoteConfig()
        fetchFcmToken()
    }

    private fun setupRemoteConfig() {
        val rc = remoteConfig ?: return
        try {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            rc.setConfigSettingsAsync(configSettings)

            val defaults = mapOf(
                "welcome_message" to "Chào mừng bạn đến với UTH Todo!",
                "welcome_banner_message" to "Chào mừng bạn đến với UTH Todo!",
                "enable_feature_categories" to true,
                "max_tasks_per_day" to 20
            )
            rc.setDefaultsAsync(defaults)
            rc.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FirebaseManager", "Remote Config fetched & activated: ${getRemoteBannerMessage()}")
                    }
                }
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "Remote Config init error: ${t.message}")
        }
    }

    private fun fetchFcmToken() {
        val msg = messaging ?: return
        try {
            msg.token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FirebaseManager", "FCM Token: $token")
                }
            }
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "FCM token fetch error: ${t.message}")
        }
    }

    fun logAnalyticsEvent(eventName: String, params: Bundle? = null) {
        try {
            analytics?.logEvent(eventName, params)
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "Analytics log error: ${t.message}")
        }
    }

    fun getRemoteBannerMessage(): String {
        return try {
            val rc = remoteConfig ?: return "Chào mừng bạn đến với UTH Todo!"
            val msg1 = rc.getString("welcome_message")
            val msg2 = rc.getString("welcome_banner_message")
            when {
                msg1.isNotBlank() -> msg1
                msg2.isNotBlank() -> msg2
                else -> "Chào mừng bạn đến với UTH Todo!"
            }
        } catch (t: Throwable) {
            "Chào mừng bạn đến với UTH Todo!"
        }
    }

    fun listenToTasksRealtime(onTasksUpdated: (List<TaskItem>) -> Unit) {
        val db = firestore ?: return
        val userId = auth?.currentUser?.uid ?: "guest_user"
        taskListenerRegistration?.remove()

        try {
            taskListenerRegistration = db.collection("users")
                .document(userId)
                .collection("tasks")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e("FirebaseManager", "Listen error: ${error.message}")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        val tasksList = snapshot.documents.mapNotNull { doc ->
                            val id = doc.id
                            val title = doc.getString("title") ?: return@mapNotNull null
                            val category = doc.getString("category") ?: "Học tập"
                            val time = doc.getString("time") ?: "00:00"
                            val isCompleted = doc.getBoolean("isCompleted") ?: false
                            TaskItem(id, title, category, time, isCompleted)
                        }
                        onTasksUpdated(tasksList)
                    }
                }
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "Firestore listener failed: ${t.message}")
        }
    }

    fun indexTaskContent(taskTitle: String, taskCategory: String, taskUrl: String = "uthtodo://task/item") {
        try {
            val indexable = Indexable.Builder()
                .setName(taskTitle)
                .setUrl(taskUrl)
                .setDescription("Công việc UTH Todo: $taskCategory")
                .build()

            FirebaseAppIndex.getInstance(appContext).update(indexable)

            val action = Action.Builder(Action.Builder.VIEW_ACTION)
                .setObject(taskTitle, taskUrl)
                .setActionStatus(Action.Builder.STATUS_TYPE_COMPLETED)
                .build()

            FirebaseUserActions.getInstance(appContext).end(action)
            Log.d("FirebaseManager", "Firebase App Indexing updated: $taskTitle")
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "App Indexing error: ${t.message}")
        }
    }

    fun saveTaskToFirestore(task: TaskItem, onComplete: (Boolean) -> Unit) {
        val db = firestore
        val userId = auth?.currentUser?.uid ?: "guest_user"
        val taskData = hashMapOf(
            "title" to task.title,
            "category" to task.category,
            "time" to task.time,
            "isCompleted" to task.isCompleted
        )

        saveTaskToRealtimeDb(task)

        if (db == null) {
            onComplete(false)
            return
        }

        try {
            db.collection("users")
                .document(userId)
                .collection("tasks")
                .document(task.id)
                .set(taskData)
                .addOnSuccessListener {
                    logAnalyticsEvent("task_saved")
                    indexTaskContent(task.title, task.category, "uthtodo://task/${task.id}")
                    onComplete(true)
                }
                .addOnFailureListener {
                    onComplete(false)
                }
        } catch (t: Throwable) {
            onComplete(false)
        }
    }

    fun saveTaskToRealtimeDb(task: TaskItem) {
        val rtdb = realtimeDb ?: return
        val userId = auth?.currentUser?.uid ?: "guest_user"
        try {
            val taskMap = hashMapOf(
                "id" to task.id,
                "title" to task.title,
                "category" to task.category,
                "time" to task.time,
                "isCompleted" to task.isCompleted
            )
            rtdb.getReference("users").child(userId).child("tasks").child(task.id).setValue(taskMap)
                .addOnSuccessListener {
                    Log.d("FirebaseManager", "Task synced to Realtime Database: ${task.title}")
                }
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "Realtime Database save error: ${t.message}")
        }
    }

    fun toggleTaskInFirestore(taskId: String, newCompletedState: Boolean) {
        val userId = auth?.currentUser?.uid ?: "guest_user"
        toggleTaskInRealtimeDb(taskId, newCompletedState)

        val db = firestore ?: return
        try {
            db.collection("users")
                .document(userId)
                .collection("tasks")
                .document(taskId)
                .update("isCompleted", newCompletedState)
                .addOnSuccessListener {
                    if (newCompletedState) {
                        logAnalyticsEvent("task_completed")
                    }
                }
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "Firestore update failed: ${t.message}")
        }
    }

    fun toggleTaskInRealtimeDb(taskId: String, newCompletedState: Boolean) {
        val rtdb = realtimeDb ?: return
        val userId = auth?.currentUser?.uid ?: "guest_user"
        try {
            rtdb.getReference("users").child(userId).child("tasks").child(taskId).child("isCompleted")
                .setValue(newCompletedState)
        } catch (t: Throwable) {
            Log.e("FirebaseManager", "Realtime Database update error: ${t.message}")
        }
    }

    companion object {
        @Volatile
        private var instance: FirebaseManager? = null

        fun getInstance(context: Context): FirebaseManager {
            return instance ?: synchronized(this) {
                instance ?: FirebaseManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
