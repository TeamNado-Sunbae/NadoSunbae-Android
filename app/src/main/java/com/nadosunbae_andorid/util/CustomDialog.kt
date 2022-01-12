package com.nadosunbae_andorid.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.presentation.ui.sign.SignUpBasicInfoActivity
import kotlinx.android.synthetic.main.signup_custom_dialog.*

class CustomDialog(context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickedListener : ButtonClickListener




    interface ButtonClickListener{
        fun onClicked(num: () -> Unit)
    }

    fun setOnClickListener(listener: ButtonClickListener)
    {
        onClickedListener = listener
    }

    fun showDialog(){

        dialog.setContentView(R.layout.signup_custom_dialog)
        dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()

        dialog.text_signup_dialog_out.setOnClickListener {
            onClickedListener.onClicked{
                SignUpBasicInfoActivity().closePage()
            }
            dialog.dismiss()
        }

        dialog.text_signup_dialog_in.setOnClickListener {
            dialog.dismiss()
        }
    }



    /* 참고하세요
     private val dialog = Dialog(context)
    private lateinit var onClickedListener : ButtonClickListener
    interface ButtonClickListener{
        fun onClicked(num : Int)
    }

    fun showDialog(@LayoutRes layout : Int){
        dialog.setContentView(layout)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_right_40_left_40)
        dialog.show()


        dialog.text_logout_ok.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        dialog.text_logout_no.setOnClickListener {
            dialog.dismiss()
        }
    }
     */

    /* 이건 액티비티나 프래그먼트에서 클릭시 이벤트 사용하면됨
     dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                여기 안에다 이벤트 작성
                }
     */
}