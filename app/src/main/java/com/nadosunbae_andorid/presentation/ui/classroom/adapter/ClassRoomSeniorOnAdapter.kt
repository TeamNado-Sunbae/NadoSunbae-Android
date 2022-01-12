package com.nadosunbae_andorid.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_andorid.data.model.response.classroom.ResponeClassRoomSeniorData
import com.nadosunbae_andorid.databinding.ItemQuestionAllBinding
import com.nadosunbae_andorid.databinding.ItemQuestionSeniorOnQuestionBinding

class ClassRoomSeniorOnAdapter : RecyclerView.Adapter<ClassRoomSeniorOnAdapter.ClassRoomSeniorOnViewHolder>() {
    var onQuestionUserList = mutableListOf<ResponeClassRoomSeniorData.Data.OnQuestionUser>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomSeniorOnAdapter.ClassRoomSeniorOnViewHolder {
        val binding = ItemQuestionSeniorOnQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomSeniorOnViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomSeniorOnViewHolder,
        position: Int
    ) {
        holder.onBind(onQuestionUserList[position])
    }

    override fun getItemCount(): Int {
        return onQuestionUserList.size
    }

    inner class ClassRoomSeniorOnViewHolder(
        val binding : ItemQuestionSeniorOnQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(onQuestionUserList : ResponeClassRoomSeniorData.Data.OnQuestionUser){
            binding.seniorOn = onQuestionUserList
            binding.executePendingBindings()
        }
    }

    fun setOnQuestionUser(onQuestionUserList: MutableList<ResponeClassRoomSeniorData.Data.OnQuestionUser>){
        this.onQuestionUserList = onQuestionUserList
        notifyDataSetChanged()
    }
}