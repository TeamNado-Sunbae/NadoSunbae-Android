package com.nadosunbae_android.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.WindowManager
import com.nadosunbae_android.R
import com.nadosunbae_android.presentation.ui.sign.SignUpBasicInfoActivity
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
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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