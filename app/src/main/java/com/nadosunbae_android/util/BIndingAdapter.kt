package com.nadosunbae_android.util

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nadosunbae_android.R
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    //날짜 변환
    @JvmStatic
    @BindingAdapter("dateToText")
    fun getDateToText(textView: TextView, date: Date?) {
        if (date == null) {
            textView.text = ""
        } else {
            val format = SimpleDateFormat("a HH:MM")
            format.format(date).also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("intToText")
    fun getIntToText(textView: TextView, int: Int): String {
        return int.toString().also { textView.text = it }
    }

    @JvmStatic
    @BindingAdapter("plusStart")
    fun plusStart(textView: TextView, text: String): String {
        return (text + "진입").also { textView.text = it }
    }

    @JvmStatic
    @BindingAdapter("int", "nickname", requireAll = false)
    fun notification(textView: TextView, int: Int, nickname: String): SpannableStringBuilder {
        val param = listOf("","1:1질문", "작성하신 질문글", "작성하신 정보글", "답글을 작성하신 질문글", "답글을 작성하신 정보글" )
        val text = listOf(
            "","마이페이지에 ${nickname}이 1:1질문을 남겼습니다.",
            "작성하신 질문글에 ${nickname}이 답글을 남겼습니다",
            "작성하신 정보글에 ${nickname}이 답글을 남겼습니다.",
            "답글을 작성하신 질문글에 ${nickname}이 답글을 남겼습니다.",
            "답글을 작성하신 정보글에 ${nickname}이 답글을 남겼습니다.",

        )
        var content = param[int]
        var start = text[int].indexOf(content)
        var end = start + content.length
        var spannable = SpannableStringBuilder(text[int])
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#00C8B0")), start, end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return spannable.also { textView.text = it }
    }

    @JvmStatic
    @BindingAdapter("notificationOval")
    fun notificationOval(imageView: ImageView, isRead: Boolean) {
        if (isRead) imageView.visibility = View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("commentCount")
    fun commentCount(textView : TextView, text : String?){
        textView.text = "댓글 ${text}개"
    }

    @JvmStatic
    @BindingAdapter("writerVisible")
    fun writerVisible(textView : TextView, isPosterWriter : Boolean){
        if(isPosterWriter){
            textView.visibility = View.VISIBLE
        }else{
            textView.visibility = View.GONE
        }

    }

}


@BindingAdapter("isOnQuestion")
fun TextView.isOnQuestion(isOnQuestion: Boolean) {
    text = if (isOnQuestion) context.getString(R.string.review_question_on_message)
    else context.getString(R.string.review_question_off_message)
}

@BindingAdapter("majorName", "majorStart")
fun TextView.majorText(majorName: String?, majorStart: String?) {
    if (majorName != null && majorStart != null)
        text = "$majorName ($majorStart)"
}

@BindingAdapter("dateFormat_yyMMdd")
fun TextView.dateToTextFormat(date: Date?) {
    if (date != null)
        text = SimpleDateFormat("yy/MM/dd").format(date)
}

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImageFromUrl(url: String?) {

    Glide.with(context)
        .load(url)
        .into(this)
}

@BindingAdapter("loadImageFromId")
fun ImageView.loadImageFromId(resId: Int?) {
    if (resId != null)
        this.setImageResource(resId)
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
    if (margin != 0) {
        val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.bottomMargin = margin.dpToPx
        this.layoutParams = layoutParams
    }
}