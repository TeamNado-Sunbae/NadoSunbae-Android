package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypageLikeQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.community.CommunityDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.user.UserLikeData
import timber.log.Timber

class MyPageLikeQuestionAdapter(
    private val num: Int,
    private val userId: Int,
    private val myPageNum: Int,
    private val postType: Int
) :
    ListAdapter<UserLikeData, MyPageLikeQuestionAdapter.MyPageLikeQuestionViewHolder>(
        DiffUtilCallback<UserLikeData>()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageLikeQuestionViewHolder {
        val binding =
            ItemMypageLikeQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyPageLikeQuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageLikeQuestionViewHolder, position: Int) {
        holder.binding.setVariable(BR.MyPageLikeQuestion, getItem(position))
        holder.itemView.setOnClickListener {
            /*
            val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
            intent.putExtra("postId",getItem(position).id.toString())
            holder.itemView.context.startActivity(intent)

             */
            CustomDialog(holder.itemView.context).restrictDialog(
                holder.itemView.context,
                ReviewGlobals.isReviewed,
                MainGlobals.signInData!!.isUserReported,
                MainGlobals.signInData!!.isReviewInappropriate,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    //1:1질문 일 때
                    if (postType == 0) {
                        val intent =
                            Intent(holder.itemView.context, QuestionDetailActivity::class.java)

                            intent.putExtra(
                                "postId",
                                getItem(holder.absoluteAdapterPosition).id
                            )
                            Timber.e("TEST11111 : ${getItem(holder.absoluteAdapterPosition).id}")
                            intent.putExtra("userId", userId)

                        ContextCompat.startActivity(holder.itemView.context, intent, null)
                    }
                    //커뮤니티일 때
                    else {
                        val intent =
                            Intent(holder.itemView.context, CommunityDetailActivity::class.java)
                        intent.putExtra(
                            "postId",
                            getItem(holder.absoluteAdapterPosition).id.toString()
                        )
                        holder.itemView.context.startActivity(intent)
                    }


                })

        }
    }

    class MyPageLikeQuestionViewHolder(val binding: ItemMypageLikeQuestionBinding) :
        RecyclerView.ViewHolder(binding.root)
}