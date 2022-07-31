package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypageLikeQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.InformationDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.mypage.MyPageLikeQuestionData

class MyPageLikeQuestionInfoAdapter(private val num: Int, private val userId: Int, private val myPageNum : Int) :
    RecyclerView.Adapter<MyPageLikeQuestionInfoAdapter.MyPageLikeQuestionInfoViewHolder>() {
    var myPageLikeQuestionData = mutableListOf<MyPageLikeQuestionData.Data.LikePost>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageLikeQuestionInfoViewHolder {
        val binding = ItemMypageLikeQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyPageLikeQuestionInfoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyPageLikeQuestionInfoViewHolder,
        position: Int
    ) {
        holder.onBind(myPageLikeQuestionData[position])
        holder.binding.root.setOnClickListener {
            CustomDialog(holder.itemView.context).restrictDialog(
                holder.itemView.context,
                ReviewGlobals.isReviewed,
                MainGlobals.signInData!!.isUserReported,
                MainGlobals.signInData!!.isReviewInappropriate,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val intent = Intent(holder.itemView.context, InformationDetailActivity::class.java)
                    intent.apply {
                        putExtra("postId", myPageLikeQuestionData[position].postId)
                        putExtra("userId", userId)
                    }
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                })
        }
    }

    override fun getItemCount(): Int = myPageLikeQuestionData.size

    inner class MyPageLikeQuestionInfoViewHolder(
        val binding: ItemMypageLikeQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myPageLikeQuestionData: MyPageLikeQuestionData.Data.LikePost) {
            binding.apply {
                myPageLikeQuestion = myPageLikeQuestionData
                executePendingBindings()
            }
        }
    }

    fun setQuestionPost(myPageLikeQuestionData: MutableList<MyPageLikeQuestionData.Data.LikePost>) {
        this.myPageLikeQuestionData = myPageLikeQuestionData
        notifyDataSetChanged()

    }
}
