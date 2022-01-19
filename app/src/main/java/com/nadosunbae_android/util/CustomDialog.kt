package com.nadosunbae_android.util

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.presentation.base.BaseActivity

class CustomDialog(context : Context) {



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

fun BottomSheetDialogFragment.finish() {
    activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()
}
