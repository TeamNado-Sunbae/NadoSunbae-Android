package com.nadosunbae_android.app.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object FirebaseAnalyticsUtil {

    private const val ACTIVE_USER = "user_active"
    private const val USER_PERSONAL = "user_post"
    private const val AU_DIVISION = "au_type"
    private const val POST_TYPE = "post_type"
    private const val QUESTION = "user_question"
    private const val QUESTION_TYPE ="question_type"

    object Tab {
        const val REVIEW = "Review Tab"
        const val CLASSROOM_QUESTION = "ClassRoom_Question Tab"
        const val CLASSROOM_INFO = "ClassRoom_Info Tab"
        const val NOTIFICATION = "Notification Tab"
        const val MYPAGE = "MyPage Tab"
    }

    object Post {
        const val REVIEW_NEW = "review_new"
        const val REVIEW_ADD = "review_additional"
        const val INFORMATION = "classroom_information"
        const val QUESTION_ALL = "classroom_question_all"
        const val QUESTION_PERSONAL = "classroom_question_personal"
    }

    object Question {
        const val QUESTION_START = "question_start"
        const val QUESTION_REPLY = "question_reply"
    }


    val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun get() = firebaseAnalytics

    fun signup() = firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP) {
        param(FirebaseAnalytics.Param.METHOD, "email")
    }

    fun login() = firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            param(FirebaseAnalytics.Param.METHOD, "manual")
        }


    fun autoLogin() = firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
        param(FirebaseAnalytics.Param.METHOD, "auto")
    }

    fun selectTab(tab: String) = firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
        param(FirebaseAnalytics.Param.SCREEN_NAME, tab)
    }


    fun dau() {
        firebaseAnalytics.logEvent(ACTIVE_USER) {
            param(AU_DIVISION, "DAU")
        }
    }

    fun wau() {
        firebaseAnalytics.logEvent(ACTIVE_USER) {
            param(AU_DIVISION, "WAU")
        }
    }

    fun mau() {
        firebaseAnalytics.logEvent(ACTIVE_USER) {
            param(AU_DIVISION, "MAU")
        }
    }

    fun userPost(value: String = "other") = firebaseAnalytics.logEvent(USER_PERSONAL) {
        param(POST_TYPE, value)
    }

    fun question(value: String = "other") = firebaseAnalytics.logEvent(QUESTION) {
        param(QUESTION_TYPE, value)
    }

    // 커스텀 이벤트 로그 (단일 파라미터)
    fun firebaseLog(event: String, paramKey: String, paramVal: String) {
        firebaseAnalytics.logEvent(event) {
            param(paramKey, paramVal)
        }
    }

}