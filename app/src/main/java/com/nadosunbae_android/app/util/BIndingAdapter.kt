package com.nadosunbae_android.app.util

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nadosunbae_android.app.R
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
    //좋아요 selector
    @JvmStatic
    @BindingAdapter("likeCheck")
    fun getLikeCheck(imageView : ImageView, like : Boolean){
        imageView.isSelected = like

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
        Log.d("spannable", "spannable이 작동이 안되는 것인가")
        return spannable.also { textView.text = it }
    }

    @JvmStatic
    @BindingAdapter("notificationOval")
    fun notificationOval(imageView: ImageView, isRead: Boolean) {
        if (isRead){
            imageView.visibility = View.GONE
        } else{
            imageView.visibility = View.VISIBLE
        }
    }
    
    //댓글 개수 보이게
    @JvmStatic
    @BindingAdapter("commentCount")
    fun commentCount(textView : TextView, text : Int){
        textView.text = "댓글 ${text}개"
    }

    //작성자 처리
    @JvmStatic
    @BindingAdapter("writerVisible")
    fun writerVisible(textView : TextView, isPosterWriter : Boolean){
        if(isPosterWriter){
            textView.visibility = View.VISIBLE
        }else{
            textView.visibility = View.GONE
        }
    }

    //프로필 이미지72
    @JvmStatic
    @BindingAdapter("profileImgBig")
    fun setProfileImgBig(imageView : ImageView, imageId : Int){
        when (imageId) {
            1 -> imageSelect(imageView, R.drawable.mask_group_1)
            2 -> imageSelect(imageView, R.drawable.mask_group_2)
            3 -> imageSelect(imageView, R.drawable.mask_group_3)
            4 -> imageSelect(imageView, R.drawable.mask_group_4)
            5 -> imageSelect(imageView, R.drawable.mask_group_5)
        }
    }
    @JvmStatic
    @BindingAdapter("profileImgSmall")
    fun setProfileImgSmall(imageView : ImageView, imageId : Int){
        when(imageId){
            1 -> imageSelect(imageView, R.drawable.mask_group_1_64)
            2 -> imageSelect(imageView, R.drawable.mask_group_2_64)
            3 -> imageSelect(imageView, R.drawable.mask_group_3_64)
            4 -> imageSelect(imageView, R.drawable.mask_group_4_64)
            5 -> imageSelect(imageView, R.drawable.mask_group_5_64)
        }
    }
    fun imageSelect(imageView : ImageView, image : Int){
        Glide.with(imageView.context)
            .load(image)
            .override(72.dpToPx, 72.dpToPx)
            .into(imageView)
    }

    // 1:1질문, 전체 질문 댓글 isDelete 여부(update랑, text) -> 일반 댓글 표시
    @JvmStatic
    @BindingAdapter("isDeleteTextUpdate")
    fun isDeleteTextUpdate(layout : ConstraintLayout, isDelete : Boolean){
        if(!isDelete){
            layout.visibility = View.VISIBLE
        }else{
            layout.visibility = View.GONE
        }
    }



    // 1:1질문, 전체 질문 댓글 isDelete 여부(delete) -> 삭제된 댓글 표시
    @JvmStatic
    @BindingAdapter("isDeleteComment")
    fun isDelete(layout : ConstraintLayout, isDelete : Boolean){
        if(isDelete){
            layout.visibility = View.VISIBLE
        }else{
            layout.visibility = View.GONE
        }
    }

    //마이페이지 수정 1:1질문 토글
    @JvmStatic
    @BindingAdapter("questionCheck")
    fun getQuestionCheck(imageView : ImageView, question : Boolean){
        imageView.isSelected = question

    }




}


@BindingAdapter("isOnQuestion")
fun TextView.isOnQuestion(isOnQuestion: Boolean) {
    text = if (isOnQuestion) context.getString(R.string.review_question_on_message)
    else context.getString(R.string.review_question_off_message)
}

@BindingAdapter("majorName", "majorStart")
fun TextView.majorText(majorName: String?, majorStart: String?) {

    if (majorName == "정보없음")
        text = majorName
    else if (majorName != null && majorStart != null)
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

@BindingAdapter("intToString")
fun android.widget.Button.intToString(num: Int) {
    this.text = num.toString()
}

// View의 isSelected를 설정
@BindingAdapter("setSelected")
fun View.setSelected(selected: Boolean) {
    this.isSelected = selected
}

