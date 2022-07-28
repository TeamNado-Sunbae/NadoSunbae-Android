package com.nadosunbae_android.app.presentation.ui.mypage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.nadosunbae_android.app.R
import kotlinx.android.synthetic.main.activity_quit_alert_custom_dialog.*
import timber.log.Timber

class QuitAlertCustomDialog(val context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickedListener: ButtonClickListener

    interface ButtonClickListener {
        fun onClicked(num: Int, toString: String)
    }

    fun setOnClickListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    //버튼 클릭
    fun initBtnClickDialog(@LayoutRes layout: Int) {
        dialog.setContentView(layout)
        dialog.text_mypage_dialog_out.setOnClickListener {
            onClickedListener.onClicked(1, dialog.editText.text.toString())
            dialog.dismiss()
        }
    }

    private fun initInBtn() {
        dialog.text_mypage_dialog_in.setOnClickListener {
            Timber.d("비밀번호: ${dialog.editText.text}")
            onClickedListener.onClicked(2, dialog.editText.text.toString())
            dialog.dismiss()
        }
    }

    //다이얼로그 show
    fun showDialog() {
        dialog.setContentView(R.layout.activity_quit_alert_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()

        dialog.text_mypage_dialog_out.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun editTextWatcher() {
        dialog.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(dialog.editText.text.toString() == "") {
                    dialog.text_mypage_dialog_in.isSelected = false
                    dialog.text_mypage_dialog_in.isEnabled = false
                } else {
                    dialog.text_mypage_dialog_in.isSelected = true
                    dialog.text_mypage_dialog_in.isEnabled = true
                    initInBtn()
                }
            }

        })
    }

}