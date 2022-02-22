package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemQuestionSeniorOffQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.SeniorFragment
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData

class ClassRoomSeniorOffAdapter(
    var link : SeniorFragment.DataToFragment
) : RecyclerView.Adapter<ClassRoomSeniorOffAdapter.ClassRoomSeniorOffViewHolder>() {
    var offQuestionUserList = mutableListOf<ClassRoomSeniorData.OffQuestionUser>()

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
        holder.itemView.setOnClickListener {
            link.getSeniorId(4, offQuestionUserList[position].userId)
        }
    }

    override fun getItemCount(): Int {
        return offQuestionUserList.size
    }

    inner class ClassRoomSeniorOffViewHolder(
        val binding : ItemQuestionSeniorOffQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(offQuestionUserList :ClassRoomSeniorData.OffQuestionUser ){
            binding.apply {
                seniorOff = offQuestionUserList
                executePendingBindings()
            }

        }
    }

    fun setOffQuestionUser(offQuestionUserList: MutableList<ClassRoomSeniorData.OffQuestionUser>){
        this.offQuestionUserList = offQuestionUserList
        notifyDataSetChanged()
    }
}