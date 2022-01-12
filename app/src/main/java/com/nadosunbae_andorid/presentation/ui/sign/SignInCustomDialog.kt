package com.nadosunbae_andorid.util

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.presentation.ui.sign.SignUpBasicInfoActivity
import kotlinx.android.synthetic.main.activity_sign_in_custom_dialog.*

class SignInCustomDialog(context : Context) {

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

        dialog.setContentView(R.layout.activity_sign_in_custom_dialog)
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

}