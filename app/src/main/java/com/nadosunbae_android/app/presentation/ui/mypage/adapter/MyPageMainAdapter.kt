package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypagePersonalQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.user.UserQuestionData

class MyPageMainAdapter (private val num: Int, private val userId: Int, private val myPageNum : Int) :
    ListAdapter<UserQuestionData, MyPageMainAdapter.MyPageMainViewHolder>(
        DiffUtilCallback<UserQuestionData>()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageMainViewHolder {
        val binding =
            ItemMypagePersonalQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyPageMainViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyPageMainViewHolder, position: Int) {
        holder.bind(getItem(position))
        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            CustomDialog(context).restrictDialog(
                context,
                ReviewGlobals.isReviewed,
                MainGlobals.signInData?.isUserReported ?: false,
                MainGlobals.signInData?.isReviewInappropriate ?: false,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val intent =
                        Intent(holder.itemView.context, QuestionDetailActivity::class.java)
                    intent.putExtra(
                        "postId",
                        getItem(holder.absoluteAdapterPosition).postId
                    )
                    holder.itemView.context.startActivity(intent)
                }
            )
        }
    }


    class MyPageMainViewHolder(val binding: ItemMypagePersonalQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userQuestionData: UserQuestionData) {
            with(binding) {
                this.myPageMain = userQuestionData
                executePendingBindings()
            }
        }
    }
}