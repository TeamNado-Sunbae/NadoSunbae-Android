package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypageReplyByMeBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.mypage.MyPageReplyData

class MyPageReplyAdapter(private val num: Int, private val userId: Int, private val myPageNum : Int) :
    RecyclerView.Adapter<MyPageReplyAdapter.MyPageReplyViewHolder>() {
    var myPageReplyData = mutableListOf<MyPageReplyData.Data.ClassroomPostListByMyComment>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageReplyViewHolder {
        val binding = ItemMypageReplyByMeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyPageReplyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyPageReplyViewHolder,
        position: Int
    ) {
        holder.onBind(myPageReplyData[position])
        holder.binding.root.setOnClickListener {
            CustomDialog(holder.itemView.context).restrictDialog(
                holder.itemView.context,
                ReviewGlobals.isReviewed,
                MainGlobals.signInData!!.isUserReported,
                MainGlobals.signInData!!.isReviewInappropriate,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
                    intent.apply {
                        putExtra("myPageNum", myPageNum)
                        putExtra("userId", userId)
                        putExtra("postId", myPageReplyData[position].postId)
                        putExtra("postTypeId", myPageReplyData[position].postTypeId)
                        putExtra("all", num)
                    }
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                })
        }
    }

    override fun getItemCount(): Int = myPageReplyData.size

    inner class MyPageReplyViewHolder(
        val binding: ItemMypageReplyByMeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myPageReplyData: MyPageReplyData.Data.ClassroomPostListByMyComment) {
            binding.apply {
                myPageReply = myPageReplyData
                executePendingBindings()
            }
        }
    }

    fun setQuestionReply(myPageReplyData: MutableList<MyPageReplyData.Data.ClassroomPostListByMyComment>) {
        this.myPageReplyData = myPageReplyData
        notifyDataSetChanged()

    }
}