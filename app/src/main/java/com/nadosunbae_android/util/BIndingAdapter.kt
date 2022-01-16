package com.nadosunbae_android.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("dateToText")
    fun getDateToText(textView: TextView, date: Date): String {
        val format = SimpleDateFormat("yyyy.MM.dd.")
        return format.format(date).also { textView.text = it }
    }

    @JvmStatic
    @BindingAdapter("intToText")
    fun getIntToText(textView: TextView, int: Int): String {
        return int.toString().also { textView.text = it }
    }


}

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImageFromUrl(url: String?) {

    Glide.with(context)
        .load(url)
        .into(this)
}