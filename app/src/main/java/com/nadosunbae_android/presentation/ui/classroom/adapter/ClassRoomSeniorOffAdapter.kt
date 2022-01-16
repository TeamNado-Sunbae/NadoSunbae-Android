package com.nadosunbae_android.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomSeniorData
import com.nadosunbae_android.databinding.ItemQuestionSeniorOffQuestionBinding

class ClassRoomSeniorOffAdapter : RecyclerView.Adapter<ClassRoomSeniorOffAdapter.ClassRoomSeniorOffViewHolder>() {
    var offQuestionUserList = mutableListOf<ResponseClassRoomSeniorData.Data.OffQuestionUser>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomSeniorOffAdapter.ClassRoomSeniorOffViewHolder {
        val binding = ItemQuestionSeniorOffQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomSeniorOffViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomSeniorOffAdapter.ClassRoomSeniorOffViewHolder,
        position: Int
    ) {
        holder.onBind(offQuestionUserList[position])
    }

    override fun getItemCount(): Int {
        return offQuestionUserList.size
    }

    inner class ClassRoomSeniorOffViewHolder(
        val binding : ItemQuestionSeniorOffQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(offQuestionUserList :ResponseClassRoomSeniorData.Data.OffQuestionUser ){
            binding.apply {
                seniorOff = offQuestionUserList
                executePendingBindings()
            }

        }
    }

    fun setOffQuestionUser(offQuestionUserList: MutableList<ResponseClassRoomSeniorData.Data.OffQuestionUser>){
        this.offQuestionUserList = offQuestionUserList
        notifyDataSetChanged()

    }
}