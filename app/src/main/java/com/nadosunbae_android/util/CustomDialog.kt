package com.nadosunbae_android.util

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.R
import com.nadosunbae_android.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.dialog_question_write_cancel.*
import kotlinx.android.synthetic.main.dialog_question_write_complete.*

class CustomDialog(context : Context) {
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




}

fun BottomSheetDialogFragment.finish() {
    activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()
}
