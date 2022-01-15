package com.nadosunbae_andorid.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.RelativeLayout
import com.nadosunbae_andorid.databinding.ViewCustomEditTextBinding

class CustomEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var binding: ViewCustomEditTextBinding

    init {
        binding = ViewCustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)

        setTouchListener()
    }

    private fun setTouchListener() {
        binding.etContent.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }
    }

}


