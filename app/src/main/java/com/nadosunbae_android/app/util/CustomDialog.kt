package com.nadosunbae_android.app.util

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.DialogGenericBinding
import com.nadosunbae_android.app.databinding.DialogProgressBinding
import com.nadosunbae_android.app.presentation.ui.review.ReviewWriteActivity
import kotlinx.android.synthetic.main.dialog_question_write_cancel.*
import kotlinx.android.synthetic.main.dialog_question_write_complete.*

class CustomDialog(val context : Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickedListener : ButtonClickListener

    interface ButtonClickListener{
        fun onClicked(num : Int)
    }
    fun setOnClickedListener(listener : ButtonClickListener){
        onClickedListener = listener
    }
    //작성 취소
    fun writeCancelDialog(@LayoutRes layout : Int){
        dialog.setContentView(layout)
        dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.rectangle_fill_white_8dp)
        dialog.show()
        dialog.text_question_write_dialog_out.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        dialog.text_question_write_dialog_in.setOnClickListener {
            onClickedListener.onClicked(2)
            dialog.dismiss()
        }
    }

    //작성 완료
    fun writeCompleteDialog(@LayoutRes layout : Int){
        dialog.setContentView(layout)
        dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.rectangle_fill_white_8dp)
        dialog.show()


        dialog.text_question_write_complete_dialog_out.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        dialog.text_question_write_complete_in.setOnClickListener {
            onClickedListener.onClicked(2)
            dialog.dismiss()
        }
    }

    fun genericDialog(dialogText: DialogData,
                      complete: () -> Unit,
                      cancel: () -> Unit) {
        val binding = DataBindingUtil.inflate<DialogGenericBinding>(LayoutInflater.from(context), R.layout.dialog_generic, null, false)
        binding.dialogText = dialogText
        binding.btnDialogCancel.setOnClickListener {
            cancel()
            dialog.dismiss()
        }
        binding.btnDialogComplete.setOnClickListener {
            complete()
            dialog.dismiss()
        }

        dialog.setContentView(binding.root)
        dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.rectangle_fill_white_8dp)

        // 버튼 길이를 긴 쪽에 맞춤
        adjustViewWidth(binding.btnDialogCancel, binding.btnDialogComplete)
        dialog.show()

    }

    private fun adjustViewWidth(view1: TextView, view2: TextView) {

        dialog.setOnShowListener {
            val minView = if (view1.width < view2.width) view1 else view2
            val maxView = if (minView == view1) view2 else view1

            minView.width = maxView.width
        }
    }

    fun progressDialog() {
        val binding = DataBindingUtil.inflate<DialogProgressBinding>(LayoutInflater.from(context), R.layout.dialog_progress, null, false)

        dialog.setContentView(binding.root)
        dialog.setCancelable(false)     // 로딩창 꺼지지 않도록
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))     // 투명한 배경 적용
        dialog.show()
    }


     fun reviewAlertDialog(context : Context){
        CustomDialog(context).genericDialog(
            DialogData(
                context.resources.getString(R.string.alert_no_review_title),
                context.resources.getString(R.string.alert_no_review_complete),
                context.resources.getString(R.string.alert_no_review_cancel)
            ),
            complete = {
                val intent = Intent(context, ReviewWriteActivity::class.java)
                context.startActivity(intent)
            },
            cancel = {}
        )
    }

    data class DialogData(
        val title: String,
        val complete: String,
        val cancel: String
    )

}

fun BottomSheetDialogFragment.finish() {
    activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()
}

