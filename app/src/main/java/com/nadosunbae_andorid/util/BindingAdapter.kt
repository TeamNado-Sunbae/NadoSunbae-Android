package com.nadosunbae_andorid.util

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.nadosunbae_andorid.R
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("dateToText")
    fun getDateToText(textView: TextView, date: Date) : String{
        val format = SimpleDateFormat("yyyy.MM.dd.")
        return format.format(date).also { textView.text = it }
    }

    @JvmStatic
    @BindingAdapter("intToText")
    fun getIntToText(textView: TextView, int : Int) : String{
        return int.toString().also { textView.text = it }
    }

}