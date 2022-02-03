package com.nadosunbae_android.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.model.response.classroom.ResponseClassRoomSeniorData
import com.nadosunbae_android.databinding.ItemQuestionSeniorOnQuestionBinding
import com.nadosunbae_android.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.presentation.ui.classroom.SeniorFragment

class ClassRoomSeniorOnAdapter(
    var link : SeniorFragment.DataToFragment
) : RecyclerView.Adapter<ClassRoomSeniorOnAdapter.ClassRoomSeniorOnViewHolder>() {
    var onQuestionUserList = mutableListOf<ClassRoomSeniorData.OnQuestionUser>()


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
        holder.itemView.setOnClickListener {
            link.getSeniorId(4, onQuestionUserList[position].userId)

        }
    }

    override fun getItemCount(): Int {
        return onQuestionUserList.size
    }

    inner class ClassRoomSeniorOnViewHolder(
        val binding : ItemQuestionSeniorOnQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(onQuestionUserList : ClassRoomSeniorData.OnQuestionUser){
            binding.seniorOn = onQuestionUserList
            binding.executePendingBindings()
        }
    }

    fun setOnQuestionUser(onQuestionUserList: MutableList<ClassRoomSeniorData.OnQuestionUser>){
        this.onQuestionUserList = onQuestionUserList
        notifyDataSetChanged()
    }
}