package com.nadosunbae_android.presentation.ui.classroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.presentation.ui.classroom.InformationDetailActivity

class ClassRoomInfoDetailAdapter: RecyclerView.Adapter<ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder>() {
    var questionMainData = mutableListOf<ResponseClassRoomMainData.Data>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder {
        val binding = ItemQuestionAllBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomInfoDetailViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder,
        position: Int
    ) {
        holder.onBind(questionMainData[position])
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, InformationDetailActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = questionMainData.size

    inner class ClassRoomInfoDetailViewHolder(
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