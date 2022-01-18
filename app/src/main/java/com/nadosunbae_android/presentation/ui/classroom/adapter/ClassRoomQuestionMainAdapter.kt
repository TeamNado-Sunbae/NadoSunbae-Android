package com.nadosunbae_android.presentation.ui.classroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.presentation.ui.classroom.QuestionDetailActivity

class ClassRoomQuestionMainAdapter : RecyclerView.Adapter<ClassRoomQuestionMainAdapter.ClassRoomQuestionMainViewHolder>() {
    var questionMainData = mutableListOf<ResponseClassRoomMainData.Data>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomQuestionMainAdapter.ClassRoomQuestionMainViewHolder {
        val binding = ItemQuestionAllBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomQuestionMainViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomQuestionMainAdapter.ClassRoomQuestionMainViewHolder,
        position: Int
    ) {
        holder.onBind(questionMainData[position])
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
            intent.apply {
                putExtra("postId", questionMainData[position].postId)
                putExtra("all", 1)
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return questionMainData.size
    }

    inner class ClassRoomQuestionMainViewHolder(
        val binding : ItemQuestionAllBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(questionMainData : ResponseClassRoomMainData.Data){
            binding.apply {
                questionMain = questionMainData
                executePendingBindings()
            }
        }
    }

    fun setQuestionMain(questionMainData: MutableList<ResponseClassRoomMainData.Data>){
        this.questionMainData = questionMainData
        notifyDataSetChanged()

    }
}