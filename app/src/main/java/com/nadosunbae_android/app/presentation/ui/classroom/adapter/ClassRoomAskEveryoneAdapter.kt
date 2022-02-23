package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.classroom.ClassRoomData


// 전체 게시물 보기
class ClassRoomAskEveryoneAdapter : RecyclerView.Adapter<ClassRoomAskEveryoneAdapter.ClassRoomAskEveryoneViewHolder>() {
    var askEveryoneData = mutableListOf<ClassRoomData>()

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
        holder.itemView.setOnClickListener {
            if(ReviewGlobals.isReviewed){
                val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
                intent.apply {
                    putExtra("postId", askEveryoneData[position].postId)
                    putExtra("all", 1)
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }else{
                CustomDialog(holder.itemView.context).reviewAlertDialog(holder.itemView.context)
            }

        }
    }

    override fun getItemCount(): Int {
        return askEveryoneData.size
    }

    inner class ClassRoomAskEveryoneViewHolder(
        val binding : ItemQuestionAllBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(askEveryoneData : ClassRoomData){
            binding.apply {
                questionMain = askEveryoneData
                executePendingBindings()
            }
        }
    }

    fun setAskEveryone(askEveryoneData: MutableList<ClassRoomData>){
        this.askEveryoneData = askEveryoneData
        notifyDataSetChanged()

    }
}