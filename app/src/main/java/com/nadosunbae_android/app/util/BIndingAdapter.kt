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
import com.nadosunbae_android.app.di.NadoSunBaeApplication
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {


    const val SEC = 60
    const val MIN = 60
    const val HOUR = 24

    enum class TimeValue(val value: Int, val maximum: Int, val msg: String) {
        SEC(60, 60, "분 전"),
        MIN(60, 24, "시간 전"),
        HOUR(24, 30, "일 전"),

    }


    //날짜 변환(알림)
    @JvmStatic
    @BindingAdapter("dateToTextMinute")
    fun getDateToTextMinute(textView: TextView, date: Date?) {
        val format = SimpleDateFormat("yy/MM/dd")
        val currentTime = System.currentTimeMillis()
        var diffTime = (currentTime - (date?.time ?: 0)) / 1000
        Timber.d("시간 $diffTime")
        if (date != null) {
            if (diffTime < TimeValue.SEC.value) {
                textView.text = "방금 전"
            } else {
                for (i in TimeValue.values()) {
                    diffTime /= i.value
                    Timber.d("첫번째 계산 시간 $diffTime")
                    if (i.value == 24) {
                        format.format(date).also { textView.text = it }
                        break
                    }
                    if (diffTime < i.maximum) {
                        textView.text = "$diffTime${i.msg}"
                        break
                    }

                }
            }
        }
    }

    //날짜
    @JvmStatic
    @BindingAdapter("dateToText")
    fun getDateToText(textView: TextView, date: Date?) {
        val format = SimpleDateFormat("yy/MM/dd")
        if (date == null) {
            textView.text = ""
        } else {
            format.format(date).also { textView.text = it }
        }
    }

    //정보글 삭제
    @JvmStatic
    @BindingAdapter("infoTextVisible")
    fun visibleInfo(view: View, isDelete: Boolean) {
        if (!isDelete) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    //선배 구성원 페이지 본전공 2중전공 구분
    @JvmStatic
    @BindingAdapter("divisionFirst")
    fun divisionFirst(textView: TextView, isFirst: Boolean) {
        if (isFirst) {
            textView.text = "본"
        } else {
            textView.text = "제2"
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
        return ("$text 진입").also { textView.text = it }
    }

    //좋아요 selector
    @JvmStatic
    @BindingAdapter("likeCheck")
    fun getLikeCheck(imageView: ImageView, like: Boolean) {
        imageView.isSelected = like

    }

    @JvmStatic
    @BindingAdapter("int", "nickname", requireAll = false)
    fun notification(textView: TextView, int: Int, nickname: String): SpannableStringBuilder {
        val param = hashMapOf(
            1 to "마이페이지에", 2 to "1:1 질문글에", 3 to "작성하신 정보글에",
            4 to "답글을 작성하신 1:1 질문글에", 5 to "답글을 작성하신 정보글에",
            6 to "1:1 질문글에", 7 to "작성하신 1:1 질문글에",
            8 to "작성하신 커뮤니티 글에", 9 to "답글을 작성하신 커뮤니티 글에"
        )
        val text = hashMapOf(
            1 to "마이페이지에 ${nickname}님이 1:1질문을 남겼습니다.",
            2 to "${nickname}님이 1:1 질문글에 답글을 남겼습니다",
            3 to "작성하신 정보글에 ${nickname}님이 답글을 남겼습니다.",
            4 to "답글을 작성하신 1:1 질문글에 ${nickname}님이 답글을 남겼습니다.",
            5 to "답글을 작성하신 정보글에 ${nickname}님이 답글을 남겼습니다.",
            6 to "${nickname}님이 1:1 질문글에 답글을 남겼습니다",
            7 to "작성하신 1:1 질문글에 ${nickname}님이 답글을 남겼습니다.",
            8 to "작성하신 커뮤니티 글에 ${nickname}님이 답글을 남겼습니다.",
            9 to "답글을 작성하신 커뮤니티 글에 ${nickname}님이 답글을 남겼습니다."
        )
        val content = param[int].toString()
        val start = text[int].toString().indexOf(content)
        val end = start + content.length
        val spannable = SpannableStringBuilder(text[int].toString())
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#00C8B0")), start, end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        Timber.d("spannable: spannable이 작동이 안되는 것인가")
        return spannable.also { textView.text = it }
    }

    @JvmStatic
    @BindingAdapter("notificationOval")
    fun notificationOval(imageView: ImageView, isRead: Boolean) {
        if (isRead) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("secondMajor")
    fun secondMajor(textView: TextView, text: String) {
        if (text == "미진입") {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
        }
    }

    //댓글 개수 보이게
    @JvmStatic
    @BindingAdapter("commentCount")
    fun commentCount(textView: TextView, text: Int) {
        textView.text = "댓글 ${text}개"
    }

    //작성자 처리
    @JvmStatic
    @BindingAdapter("writerVisible", "isDelete")
    fun writerVisible(textView: TextView, isPosterWriter: Boolean, isDelete: Boolean) {
        if (isPosterWriter && !isDelete) {
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }

    //프로필 이미지72
    @JvmStatic
    @BindingAdapter("profileImgBig")
    fun setProfileImgBig(imageView: ImageView, imageId: Int) {
        when (imageId) {
            1 -> imageSelect(imageView, R.drawable.mask_group_5)
            2 -> imageSelect(imageView, R.drawable.mask_group_4)
            3 -> imageSelect(imageView, R.drawable.mask_group_2)
            4 -> imageSelect(imageView, R.drawable.mask_group_1)
            5 -> imageSelect(imageView, R.drawable.mask_group_3)
        }
    }

    @JvmStatic
    @BindingAdapter("profileImgSmall")
    fun setProfileImgSmall(imageView: ImageView, imageId: Int) {
        when (imageId) {
            1 -> imageSelect(imageView, R.drawable.mask_group_1_64)
            2 -> imageSelect(imageView, R.drawable.mask_group_2_64)
            3 -> imageSelect(imageView, R.drawable.mask_group_3_64)
            4 -> imageSelect(imageView, R.drawable.mask_group_4_64)
            5 -> imageSelect(imageView, R.drawable.mask_group_5_64)
        }
    }

    fun imageSelect(imageView: ImageView, image: Int) {
        Glide.with(imageView.context)
            .load(image)
            .override(72.dpToPx, 72.dpToPx)
            .into(imageView)
    }

    // 1:1질문, 전체 질문 댓글 isDelete 여부(update랑, text) -> 일반 댓글 표시
    @JvmStatic
    @BindingAdapter("isDeleteTextUpdate")
    fun isDeleteTextUpdate(layout: ConstraintLayout, isDelete: Boolean) {
        if (!isDelete) {
            layout.visibility = View.VISIBLE
        } else {
            layout.visibility = View.GONE
        }
    }


    // 1:1질문, 전체 질문 댓글 isDelete 여부(delete) -> 삭제된 댓글 표시
    @JvmStatic
    @BindingAdapter("isDeleteComment")
    fun isDelete(layout: ConstraintLayout, isDelete: Boolean) {
        if (isDelete) {
            layout.visibility = View.VISIBLE
        } else {
            layout.visibility = View.GONE
        }
    }

    //마이페이지 수정 1:1질문 토글
    @JvmStatic
    @BindingAdapter("questionCheck")
    fun getQuestionCheck(imageView: ImageView, question: Boolean) {
        imageView.isSelected = question
    }

    // 현재 글자/최대 글자
    @JvmStatic
    @BindingAdapter("displayMaxLength")
    fun displayMaxLength(textView: TextView, length: Int) {
        textView.text =
            "${length}/${NadoSunBaeApplication.context().getString(R.string.review_write_max_40)}"
    }

    // 현재 글자/최소 글자
    @JvmStatic
    @BindingAdapter("displayMinLength")
    fun displayMinLength(textView: TextView, length: Int) {
        textView.text =
            "${length}/${NadoSunBaeApplication.context().getString(R.string.review_write_min_100)}"
    }

    // 글자수 + 자
    @JvmStatic
    @BindingAdapter("displayWriteLength")
    fun displayWriteLength(textView: TextView, length: Int) {
        textView.text =
            "${length}${NadoSunBaeApplication.context().getString(R.string.review_write_length)}"
    }

    @JvmStatic
    @BindingAdapter("setVisible")
    fun setVisible(view: View, visible: Boolean) {
        if (visible)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

    @JvmStatic
    @BindingAdapter("isCommunity")
    fun isCommunity(textView: TextView, type: String?) {
        if (type == "자유" || type == "정보")
            textView.visibility = View.VISIBLE
        else
            textView.visibility = View.GONE
    }

    @JvmStatic
    @BindingAdapter("rateText")
    fun rateText(textView: TextView, int: Int) {
        val rate = int.toString().also { textView.text = it }
        if (rate == null) {
            textView.text = "-"
        } else {
            textView.text = "${"응답률 "}${rate}${"%"}"
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

    if (majorName == "정보없음" || majorName == "미진입")
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
