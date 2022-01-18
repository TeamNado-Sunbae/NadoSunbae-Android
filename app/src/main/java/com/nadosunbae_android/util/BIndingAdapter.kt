package com.nadosunbae_android.util

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.w3c.dom.Text
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
@BindingAdapter("majorName", "majorStart")
fun TextView.majorText(majorName: String, majorStart: String) {
    text = "$majorName $majorStart"
}

@BindingAdapter("dateFormat_YYMMDD")
fun TextView.dateToTextFormat(date: Date) {
    text = SimpleDateFormat("yy/MM/dd").format(date)
}

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImageFromUrl(url: String?) {

    Glide.with(context)
        .load(url)
        .into(this)
}

@BindingAdapter("layoutMarginStart")
fun View.layoutMarginStart(margin: Int) {
    if (margin != 0) {
        val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = margin.dpToPx
        this.layoutParams = layoutParams
    }

}

@BindingAdapter("layoutMarginEnd")
fun View.layoutMarginEnd(margin: Int) {
    if (margin != 0) {
        val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginEnd = margin.dpToPx
        this.layoutParams = layoutParams
    }
}

@BindingAdapter("layoutMarginTop")
fun View.layoutMarginTop(margin: Int) {
    if (margin != 0) {
        val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = margin.dpToPx
        this.layoutParams = layoutParams
    }

}

@BindingAdapter("layoutMarginBottom")
fun View.layoutMarginBottom(margin: Int) {
    if (margin != 0)
    {
        val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.bottomMargin = margin.dpToPx
        this.layoutParams = layoutParams
    }
}