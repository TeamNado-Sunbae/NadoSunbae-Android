package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypagePersonalQuestionBinding
import com.nadosunbae_android.app.databinding.ItemMypagePostByMeBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData

class MyPagePostAdapter(private val num: Int, private val userId: Int, private val myPageNum : Int) :
    RecyclerView.Adapter<MyPagePostAdapter.MyPagePostViewHolder>() {
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
            val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
            intent.apply {
                putExtra("myPageNum", myPageNum)
                putExtra("userId", userId)
                putExtra("postId", myPagePostData[position].postId)
                putExtra("postTypeId", myPagePostData[position].postTypeId)
                putExtra("all", num)
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
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