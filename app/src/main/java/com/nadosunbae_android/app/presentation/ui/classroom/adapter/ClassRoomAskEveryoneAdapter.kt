package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionWriteActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.classroom.ClassRoomData


// 전체 게시물 보기
class ClassRoomAskEveryoneAdapter(private var userId : Int) : RecyclerView.Adapter<ClassRoomAskEveryoneAdapter.ClassRoomAskEveryoneViewHolder>() {
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
            CustomDialog(holder.itemView.context).restrictDialog(
                holder.itemView.context,
                ReviewGlobals.isReviewed,
                MainGlobals.signInData!!.isUserReported,
                MainGlobals.signInData!!.isReviewInappropriate,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
                    intent.apply {
                        putExtra("postId", askEveryoneData[position].postId)
                        putExtra("all", 1)
                        putExtra("userId", userId)
                    }
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                }
            )
        }

        if(position == (itemCount - 1)){
            holder.binding.lineQuestionAll.visibility = View.GONE
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