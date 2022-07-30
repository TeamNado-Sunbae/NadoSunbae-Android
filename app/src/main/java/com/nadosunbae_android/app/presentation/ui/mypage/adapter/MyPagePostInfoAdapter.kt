package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypagePostByMeBinding
import com.nadosunbae_android.app.presentation.ui.classroom.InformationDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.mypage.MyPagePostData

class MyPagePostInfoAdapter (private val num: Int, private val userId: Int, private val myPageNum : Int) :
    RecyclerView.Adapter<MyPagePostInfoAdapter.MyPagePostViewHolder>() {
    var myPagePostData = mutableListOf<MyPagePostData.Data.ClassroomPost>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPagePostViewHolder {
        val binding = ItemMypagePostByMeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyPagePostViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyPagePostViewHolder,
        position: Int
    ) {
        holder.onBind(myPagePostData[position])
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
                        putExtra("postId", myPagePostData[position].postId)
                        putExtra("userId", userId)
                    }
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                })
        }
    }

    override fun getItemCount(): Int = myPagePostData.size

    inner class MyPagePostViewHolder(
        val binding: ItemMypagePostByMeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myPagePostData: MyPagePostData.Data.ClassroomPost) {
            binding.apply {
                myPagePost = myPagePostData
                executePendingBindings()
            }
        }
    }

    fun setQuestionPost(myPagePostData: MutableList<MyPagePostData.Data.ClassroomPost>) {
        this.myPagePostData = myPagePostData
        notifyDataSetChanged()

    }
}