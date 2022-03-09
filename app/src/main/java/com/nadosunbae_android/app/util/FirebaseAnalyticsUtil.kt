package com.nadosunbae_android.app.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object FirebaseAnalyticsUtil {

    private lateinit var _firebaseAnalytics: FirebaseAnalytics
    val firebaseAnalytics: FirebaseAnalytics
        get() = _firebaseAnalytics

    fun initFirebaseAnalytics(firebaseAnalytics: FirebaseAnalytics) {
        this._firebaseAnalytics = Firebase.analytics
    }

    fun get() = _firebaseAnalytics

    fun signup() = _firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP) {
        param(FirebaseAnalytics.Param.METHOD, "email")
    }

    fun login() =
        _firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            param(FirebaseAnalytics.Param.METHOD, "manual")
        }

    fun autoLogin() = _firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
        param(FirebaseAnalytics.Param.METHOD, "auto")
    }



    // 커스텀 이벤트 로그 (단일 파라미터)
    fun firebaseLog(event: String, paramKey: String, paramVal: String) {
        firebaseAnalytics.logEvent(event) {
            param(paramKey, paramVal)
        }
    }

}