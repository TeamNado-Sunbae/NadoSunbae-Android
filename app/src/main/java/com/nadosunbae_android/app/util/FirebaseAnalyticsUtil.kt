package com.nadosunbae_android.app.util

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.nadosunbae_android.app.BuildConfig
import com.nadosunbae_android.domain.model.sign.SignInData

object FirebaseAnalyticsUtil {

    // User Property
    private const val FIRST_MAJOR = "first_major"
    private const val SECOND_MAJOR = "second_major"
    private const val SELECTED_MAJOR = "selected_major"
    private const val USER_ID = "user_id"
    private const val UNIVERSITY_ID = "university_id"
    private const val USER_REVIEWED = "is_reviewed"
    private const val USER_EMAIL_VERIFIED = "is_email_verified"
    private const val USER_REVIEW_INAPPROPRIATE = "is_review_inappropriate"
    private const val USER_REPORTED = "is_user_reported"

    // Event Name
    private const val ACTIVE_USER = "user_active"
    private const val USER_PERSONAL = "user_post"
    private const val QUESTION = "user_question"
    private const val TAB_SELECT = "tab_select"

    // Parameter name
    private const val AU_DIVISION = "au_type"
    private const val POST_TYPE = "post_type"
    private const val QUESTION_TYPE = "question_type"
    private const val TAB_NAME = "tab_name"

    // Parameter value
    object Tab {
        const val REVIEW = "Review Tab"
        const val CLASSROOM_QUESTION = "ClassRoom_Question Tab"
        const val NOTIFICATION = "Notification Tab"
        const val MYPAGE = "MyPage Tab"
        const val COMMUNITY = "Community Tab"
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

    private val firebaseAnalytics: FirebaseAnalytics?
        get() = Firebase.analytics


    //탭 클릭
    fun selectTab(tab: String) = firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
        param(FirebaseAnalytics.Param.SCREEN_NAME, tab)
    }

    //dau,wau,mau
    fun auDivision(division : String) {
        firebaseAnalytics?.logEvent(ACTIVE_USER) {
            param(AU_DIVISION, division)
        }
    }


    fun userPost(value: String = "other") = firebaseAnalytics?.logEvent(USER_PERSONAL) {
        param(POST_TYPE, value)
    }

    fun question(value: String = "other") = firebaseAnalytics?.logEvent(QUESTION) {
        param(QUESTION_TYPE, value)
    }
    fun clickLike(){
        firebaseAnalytics?.logEvent("like_click"){
            param("","like_on")
        }
    }

    fun setUserProperty(user: SignInData.User) {
        firebaseAnalytics?.run {
            setUserProperty(UNIVERSITY_ID, "${user.universityId}")
            setUserProperty(FIRST_MAJOR, user.firstMajorName)
            setUserProperty(SECOND_MAJOR, user.secondMajorName)
            setUserProperty(USER_ID, "${user.userId}")
            setUserProperty(USER_REVIEWED, "${user.isReviewed}")
            setUserProperty(USER_EMAIL_VERIFIED, "${user.isEmailVerified}")
            setUserProperty(USER_REVIEW_INAPPROPRIATE, "${user.isReviewInappropriate}")
            setUserProperty(USER_REPORTED, "${user.isUserReported}")
        }
    }

    fun setSelectedMajor(majorName: String) =
        firebaseAnalytics?.setUserProperty(SELECTED_MAJOR, majorName)

    // 커스텀 이벤트 로그 (단일 파라미터)
    fun firebaseLog(event: String, paramKey: String, paramVal: String) {
        firebaseAnalytics?.logEvent(event) {
            param(paramKey, paramVal)
        }
    }
    // 커스텀 이벤트 로그(복수 파라미터)
    fun firebaseLogs(event: String, paramKey : String, paramVal : List<String>){
        val bundle = Bundle()
        for(i in paramVal){
            bundle.putString(paramKey, i)
        }
        firebaseAnalytics?.logEvent(event,bundle)
    }



}