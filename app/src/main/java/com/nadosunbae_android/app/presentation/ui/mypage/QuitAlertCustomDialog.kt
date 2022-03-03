package com.nadosunbae_android.app.presentation.ui.mypage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.nadosunbae_android.app.R
import kotlinx.android.synthetic.main.activity_quit_alert_custom_dialog.*


class QuitAlertCustomDialog(val context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickedListener : ButtonClickListener

    interface ButtonClickListener{
        fun onClicked(num: Int)
    }

    fun setOnClickListener(listener: ButtonClickListener)
    {
        onClickedListener = listener
    }


    //작성 취소
    fun writeCancelDialog(@LayoutRes layout : Int){
        dialog.setContentView(layout)
        dialog.text_mypage_dialog_out.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        dialog.text_mypage_dialog_in.setOnClickListener {

            Log.d("비밀번호", ":" + dialog.editText.text.toString())
            onClickedListener.onClicked(2)
            //dialog.editText.setText(dialog.editText.text.toString())
            dialog.dismiss()
        }
    }

    fun showDialog(){
        dialog.setContentView(R.layout.activity_quit_alert_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()

        dialog.text_mypage_dialog_out.setOnClickListener {
            dialog.dismiss()
        }

        dialog.text_mypage_dialog_in.setOnClickListener {
            if(dialog.editText != null) {
                Log.d("비밀번호", ":" + dialog.editText.text.toString())
                //myPageViewModel.deleteMyPageQuit(MyPageQuitItem(dialog.editText.text.toString()))

            }
            dialog.dismiss()
        }
    }

}