package com.nadosunbae_android.presentation.ui.classroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.ui.classroom.ClassRoomData
import com.nadosunbae_android.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.presentation.ui.classroom.QuestionDetailActivity


// 전체 게시물 보기
class ClassRoomAskEveryoneAdapter : RecyclerView.Adapter<ClassRoomAskEveryoneAdapter.ClassRoomAskEveryoneViewHolder>() {
    var askEveryoneData = mutableListOf<ClassRoomData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomAskEveryoneViewHolder {
        val binding = ItemQuestionAllBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomAskEveryoneViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomAskEveryoneViewHolder,
        position: Int
    ) {
        holder.onBind(askEveryoneData[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
            intent.apply {
                putExtra("postId", askEveryoneData[position].postId)
                putExtra("all", 1)
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return askEveryoneData.size
    }

    inner class ClassRoomAskEveryoneViewHolder(
        val binding : ItemQuestionAllBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(askEveryoneData : ClassRoomData){
            binding.apply {
                questionMain = askEveryoneData
                executePendingBindings()
            }
        }
    }

    fun setAskEveryone(askEveryoneData: MutableList<ClassRoomData>){
        this.askEveryoneData = askEveryoneData
        notifyDataSetChanged()

    }
}