package com.nadosunbae_andorid.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object BIndingAdapter {

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