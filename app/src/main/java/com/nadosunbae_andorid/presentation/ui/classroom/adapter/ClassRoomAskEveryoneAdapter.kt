package com.nadosunbae_andorid.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_andorid.databinding.ItemQuestionAllBinding


// 전체 게시물 보기
class ClassRoomAskEveryoneAdapter : RecyclerView.Adapter<ClassRoomAskEveryoneAdapter.ClassRoomAskEveryoneViewHolder>() {
    var askEveryoneData = mutableListOf<ResponseClassRoomMainData.Data>()

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
    }

    override fun getItemCount(): Int {
        return askEveryoneData.size
    }

    inner class ClassRoomAskEveryoneViewHolder(
        val binding : ItemQuestionAllBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(askEveryoneData : ResponseClassRoomMainData.Data){
            binding.apply {
                questionMain = askEveryoneData
                executePendingBindings()
            }
        }
    }

    fun setAskEveryone(askEveryoneData: MutableList<ResponseClassRoomMainData.Data>){
        this.askEveryoneData = askEveryoneData
        notifyDataSetChanged()

    }
}