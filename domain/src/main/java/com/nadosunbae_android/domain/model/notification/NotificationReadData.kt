package com.nadosunbae_android.domain.model.notification


//알림 읽은 경우
data class NotificationReadData(
    val id : Int,
    val isRead : Boolean
){
    companion object{
        val DEFAULT = NotificationReadData(
            0,false
        )
    }
}
