package com.nadosunbae_andorid.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BIndingAdapter {

    /* 참고
     @JvmStatic
    @BindingAdapter("profileBind")
    fun setProfileImage(imageView: ImageView, imageUri : Uri){
        Glide.with(imageView.context)
            .load(imageUri)
            .transform(RoundedCorners(20.dpToPx))
            .centerCrop()
            .into(imageView)
    }
     */

    @JvmStatic
    @BindingAdapter("selectBottomSheetImg")
    fun setBottomSheetImg(imageView: ImageView, data : Boolean){
            imageView.isSelected = data
    }
}