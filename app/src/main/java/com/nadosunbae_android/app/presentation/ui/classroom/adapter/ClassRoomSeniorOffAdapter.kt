package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemQuestionSeniorOffQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.question.DataToFragment
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData

class ClassRoomSeniorOffAdapter(
    var link : DataToFragment
) : RecyclerView.Adapter<ClassRoomSeniorOffAdapter.ClassRoomSeniorOffViewHolder>() {
    var offQuestionUserList = mutableListOf<ClassRoomSeniorData.UserSummaryData>()

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
            link.getSeniorId(offQuestionUserList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return offQuestionUserList.size
    }

    inner class ClassRoomSeniorOffViewHolder(
        val binding : ItemQuestionSeniorOffQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(userSummaryDataList :ClassRoomSeniorData.UserSummaryData ){
            binding.apply {
                seniorOff = userSummaryDataList
                executePendingBindings()
            }

        }
    }

    fun setOffQuestionUser(userSummaryDataList: MutableList<ClassRoomSeniorData.UserSummaryData>){
        this.offQuestionUserList = userSummaryDataList
        notifyDataSetChanged()
    }
}