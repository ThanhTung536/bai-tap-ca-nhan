package com.example.bai_tap_1

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging

class UthApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this)
            }
            val analytics = FirebaseAnalytics.getInstance(this)
            analytics.setAnalyticsCollectionEnabled(true)
            analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)

            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.e("FCM_TOKEN", "========================================")
                    Log.e("FCM_TOKEN", "FCM REGISTRATION TOKEN:\n$token")
                    Log.e("FCM_TOKEN", "========================================")
                } else {
                    Log.e("FCM_TOKEN", "Fetching FCM registration token failed: ${task.exception?.message}")
                }
            }
        } catch (t: Throwable) {
            Log.e("UthApplication", "FirebaseApp initializeApp error: ${t.message}")
        }
    }
}
