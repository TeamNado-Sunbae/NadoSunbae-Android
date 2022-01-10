package com.nadosunbae_andorid.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_andorid.databinding.ItemQuestionAllBinding

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
    }

    override fun getItemCount(): Int {
        return questionMainData.size
    }

    inner class ClassRoomQuestionMainViewHolder(
        private val binding : ItemQuestionAllBinding
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