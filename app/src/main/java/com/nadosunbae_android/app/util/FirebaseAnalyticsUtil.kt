package com.nadosunbae_android.app.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.nadosunbae_android.app.di.NadoSunBaeApplication

object FirebaseAnalyticsUtil {

    private const val ACTIVE_USER = "user_active"
    private const val USER_PERSONAL = "user_post"
    private const val AU_DIVISION = "au_type"
    private const val POST_TYPE = "post_type"
    private const val QUESTION = "user_question"
    private const val QUESTION_TYPE ="question_type"

    object Post {
        const val REVIEW_NEW = "review_new"
        const val REVIEW_ADD = "review_additional"
        const val INFORMATION = "classroom_information"
        const val QUESTION_ALL = "classromm_question_all"
        const val QUESTION_PERSONAL = "classroom_question_personal"
    }

    object Question {
        const val QUESTION_START = "question_start"
        const val QUESTION_REPLY = "question_reply"
    }

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

    fun login() = _firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            param(FirebaseAnalytics.Param.METHOD, "manual")
        }


    fun autoLogin() = _firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
        param(FirebaseAnalytics.Param.METHOD, "auto")
    }

    fun dau() {
        val context = NadoSunBaeApplication.context()
        if (!NadoSunBaeSharedPreference.getFlag(context, NadoSunBaeSharedPreference.DAU_FLAG)) {
            firebaseAnalytics.logEvent(ACTIVE_USER) {
                param(AU_DIVISION, "DAU")
            }
            NadoSunBaeSharedPreference.setFlag(context, NadoSunBaeSharedPreference.DAU_FLAG, true)
        }
    }

    fun wau() {
        val context = NadoSunBaeApplication.context()
        if (!NadoSunBaeSharedPreference.getFlag(context, NadoSunBaeSharedPreference.WAU_FLAG)) {
            firebaseAnalytics.logEvent(ACTIVE_USER) {
                param(AU_DIVISION, "WAU")
            }
            NadoSunBaeSharedPreference.setFlag(context, NadoSunBaeSharedPreference.WAU_FLAG, true)
        }
    }

    fun mau() {
        val context = NadoSunBaeApplication.context()
        if (!NadoSunBaeSharedPreference.getFlag(context, NadoSunBaeSharedPreference.MAU_FLAG)) {
            firebaseAnalytics.logEvent(ACTIVE_USER) {
                param(AU_DIVISION, "MAU")
            }
            NadoSunBaeSharedPreference.setFlag(context, NadoSunBaeSharedPreference.MAU_FLAG, true)
        }
    }

    fun userPost(value: String = "other") = _firebaseAnalytics.logEvent(USER_PERSONAL) {
        param(POST_TYPE, value)
    }

    fun question(value: String = "other") = _firebaseAnalytics.logEvent(QUESTION) {
        param(QUESTION_TYPE, value)
    }

    // 커스텀 이벤트 로그 (단일 파라미터)
    fun firebaseLog(event: String, paramKey: String, paramVal: String) {
        firebaseAnalytics.logEvent(event) {
            param(paramKey, paramVal)
        }
    }

}