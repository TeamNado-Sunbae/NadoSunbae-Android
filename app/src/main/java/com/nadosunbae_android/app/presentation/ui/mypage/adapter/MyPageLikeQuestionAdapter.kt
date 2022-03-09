package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypageLikeQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.domain.model.mypage.MyPageLikeQuestionData

class MyPageLikeQuestionAdapter (private val num: Int, private val userId: Int, private val myPageNum : Int, private val postTypeId : Int) :
    RecyclerView.Adapter<MyPageLikeQuestionAdapter.MyPageLikeQuestionViewHolder>() {
    var myPageLikeQuestionData = mutableListOf<MyPageLikeQuestionData.Data.LikePost>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageLikeQuestionViewHolder {
        val binding = ItemMypageLikeQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyPageLikeQuestionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyPageLikeQuestionAdapter.MyPageLikeQuestionViewHolder,
        position: Int
    ) {
        holder.onBind(myPageLikeQuestionData[position])
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
            intent.apply {
                putExtra("myPageNum", myPageNum)
                putExtra("userId", userId)
                putExtra("postId", myPageLikeQuestionData[position].postId)
                putExtra("postTypeId", myPageLikeQuestionData[position].postTypeId)
                putExtra("all", num)

                Log.d("test", " : " + postTypeId)
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = myPageLikeQuestionData.size

    inner class MyPageLikeQuestionViewHolder(
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