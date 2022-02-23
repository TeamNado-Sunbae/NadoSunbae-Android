package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypagePersonalQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData

class MyPageMainAdapter (private val num: Int, private val userId: Int, private val myPageNum : Int) :
    RecyclerView.Adapter<MyPageMainAdapter.MyPageMainViewHolder>() {
    var myPageMainData = mutableListOf<MyPageQuestionData.Data.ClassroomPost>()

    private var like = 0
    private var likeSelect = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageMainViewHolder {
        val binding = ItemMypagePersonalQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyPageMainViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: MyPageMainViewHolder,
        position: Int
    ) {
        holder.onBind(myPageMainData[position])
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
            intent.apply {
                putExtra("myPageNum",myPageNum)
                putExtra("userId", userId)
                putExtra("postId", myPageMainData[position].postId)
                putExtra("all", num)
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return if (myPageMainData.size < 6) myPageMainData.size else 5

    }

    inner class MyPageMainViewHolder(
        val binding: ItemMypagePersonalQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myPageMainData: MyPageQuestionData.Data.ClassroomPost) {
            binding.apply {
                myPageMain = myPageMainData
                executePendingBindings()
            }
        }
    }

    fun setQuestionMain(questionMainData: MutableList<MyPageQuestionData.Data.ClassroomPost>) {
        this.myPageMainData = questionMainData
        notifyDataSetChanged()

    }
}