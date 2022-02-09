package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.app.presentation.ui.classroom.InformationDetailActivity
import com.nadosunbae_android.domain.model.classroom.ClassRoomData

class ClassRoomInfoMainAdapter: RecyclerView.Adapter<ClassRoomInfoMainAdapter.ClassRoomInfoMainViewHolder>() {
    var questionMainData = mutableListOf<ClassRoomData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomInfoMainAdapter.ClassRoomInfoMainViewHolder {
        val binding = ItemQuestionAllBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomInfoMainViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomInfoMainAdapter.ClassRoomInfoMainViewHolder,
        position: Int
    ) {
        holder.onBind(questionMainData[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, InformationDetailActivity::class.java)
            intent.apply {
                putExtra("postId", questionMainData[position].postId)
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = questionMainData.size

    inner class ClassRoomInfoMainViewHolder(
        val binding : ItemQuestionAllBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(questionMainData : ClassRoomData){
            binding.apply {
                questionMain = questionMainData
                executePendingBindings()
            }
        }
    }

    fun setQuestionMain(questionMainData: MutableList<ClassRoomData>){
        this.questionMainData = questionMainData
        notifyDataSetChanged()

    }
}