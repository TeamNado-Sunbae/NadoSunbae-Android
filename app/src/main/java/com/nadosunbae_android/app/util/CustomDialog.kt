package com.nadosunbae_android.app.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.DialogGenericBinding
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
        dialog.show()

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

