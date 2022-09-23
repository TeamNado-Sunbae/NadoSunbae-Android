package com.nadosunbae_android.domain.model.community

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommunityWriteUpdateData(
    val content : String,
    val title : String,
    val major : String,
    val category : String?,
    val postId : String
) : Parcelable{
    companion object{
        val DEFAULT = CommunityWriteUpdateData(
            "","","","",""
        )
    }
}
