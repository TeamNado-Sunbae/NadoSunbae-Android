package com.nadosunbae_android.app.util

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.DialogDeletePostBinding
import com.nadosunbae_android.app.databinding.DialogGenericBinding
import com.nadosunbae_android.app.databinding.DialogProgressBinding
import com.nadosunbae_android.app.databinding.DialogReportBinding
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewWriteActivity
import kotlinx.android.synthetic.main.dialog_question_write_cancel.*
import kotlinx.android.synthetic.main.dialog_question_write_complete.*
import timber.log.Timber

class CustomDialog(val context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickedListener: ButtonClickListener
    private lateinit var reportClickListener: ReportClickListener

    interface ButtonClickListener {
        fun onClicked(num: Int)
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    interface ReportClickListener {
        fun reportClick(text: String)
    }

    fun setReportClickListener(listener: ReportClickListener) {
        reportClickListener = listener
    }

    fun setReportClickAction(action: (String) -> Unit) {
        reportClickListener = object : ReportClickListener {
            override fun reportClick(text: String) {
                action(text)
            }
        }
    }

    //작성 취소
    fun writeCancelDialog(@LayoutRes layout: Int, divisionNum: Int) {
        dialog.setContentView(layout)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.rectangle_fill_white_8dp)
        dialog.show()
        if (divisionNum == 1) {
            dialog.text_question_write_dialog.text =
                context.getString(R.string.question_update_cancel)
        }
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
    fun writeCompleteDialog(@LayoutRes layout: Int) {
        dialog.setContentView(layout)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
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

    fun genericDialog(
        dialogText: DialogData,
        complete: () -> Unit,
        cancel: () -> Unit
    ) {
        val binding = DataBindingUtil.inflate<DialogGenericBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_generic,
            null,
            false
        )
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
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.rectangle_fill_white_8dp)

        adjustViewWidth(binding.btnDialogCancel, binding.btnDialogComplete)     // 버튼 길이를 긴 쪽에 맞춤

        dialog.show()

    }

    private fun adjustViewWidth(view1: TextView, view2: TextView) {

        dialog.setOnShowListener {
            val minView = if (view1.width < view2.width) view1 else view2
            val maxView = if (minView == view1) view2 else view1

            minView.width = maxView.width
        }
    }

    fun progressDialog(): Dialog {
        val binding = DataBindingUtil.inflate<DialogProgressBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_progress,
            null,
            false
        )

        dialog.setContentView(binding.root)
        dialog.setCancelable(false)     // 로딩창 꺼지지 않도록
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))     // 다이얼로그 투명한 배경 적용
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)                       // 다이얼로그 외부 영역 투명
        dialog.show()
        return dialog
    }

    //리뷰 신고 다이얼로그
    private fun reviewAlertDialog(context: Context) {
        CustomDialog(context).genericDialog(
            DialogData(
                context.getString(R.string.alert_no_review_title),
                context.getString(R.string.alert_no_review_complete),
                context.getString(R.string.alert_no_review_cancel)
            ),
            complete = {
                val intent = Intent(context, ReviewWriteActivity::class.java)
                intent.putExtra("mode", ReviewWriteActivity.MODE_NEW)
                context.startActivity(intent)
            },
            cancel = {}
        )
    }

    data class DialogData(
        val title: String?,
        val complete: String,
        val cancel: String
    )


    fun reportDialog(): CustomDialog {
        val binding = DialogReportBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_8)
        dialog.show()

        binding.textReportOne.setOnClickListener {
            reportClickListener.reportClick(binding.textReportOne.text.toString())
            dialog.dismiss()
        }
        binding.textReportTwo.setOnClickListener {
            reportClickListener.reportClick(binding.textReportTwo.text.toString())
            dialog.dismiss()
        }
        binding.textReportThree.setOnClickListener {
            reportClickListener.reportClick(binding.textReportThree.text.toString())
            dialog.dismiss()
        }
        binding.textReportFour.setOnClickListener {
            reportClickListener.reportClick(binding.textReportFour.text.toString())
            dialog.dismiss()
        }
        binding.textReportFive.setOnClickListener {
            reportClickListener.reportClick(binding.textReportFive.text.toString())
            dialog.dismiss()
        }
        binding.textReportSix.setOnClickListener {
            reportClickListener.reportClick(binding.textReportSix.text.toString())
            dialog.dismiss()
        }

        return this
    }
    //삭제된 알림
    fun deleteNotificationDialog(message : String): CustomDialog {
        val binding = DialogDeletePostBinding.inflate(LayoutInflater.from(context))
        binding.tvDialogDeleteTitle.text = message
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_8)
        dialog.show()

        binding.btnDialogComplete.setOnClickListener {
            dialog.dismiss()
        }

        return this
    }

    //후기 미작성자, 신고, 부적절 후기 작성자 구분
    fun restrictDialog(
        context: Context,
        isReviewed: Boolean,
        isUserReported: Boolean,
        isReviewInappropriate: Boolean,
        message: String,
        isHome: Boolean? = false,
        behavior: () -> Unit
    ) {
        //유저 신고
        if (isUserReported) {
            deleteNotificationDialog(message)
        } else if (isReviewInappropriate) {
            if (isHome == true) {
                CustomDialog(context).genericDialog(
                    DialogData(
                        message,
                        context.getString(R.string.sign_in_question),
                        context.getString(R.string.email_certification_close)
                    ),
                    complete = {
                        var intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(context.getString(R.string.question_kakao))
                        )
                        context.startActivity(intent)
                    },
                    cancel = {}
                )
            } else {
                CustomDialog(context).reviewAlertDialog(context)
            }
        } else if (!isReviewed) {
            CustomDialog(context).reviewAlertDialog(
                context,
            )
            Timber.d("제한 다이얼로그 후기 미작성 유저")
        } else {
            behavior()
            Timber.d("제한 다이얼로그 올바른 유저")
        }
    }


}

fun BottomSheetDialogFragment.finish() {
    activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()
}

