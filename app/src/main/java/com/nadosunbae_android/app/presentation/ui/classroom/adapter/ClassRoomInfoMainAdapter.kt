package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.app.presentation.ui.classroom.InformationDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.classroom.ClassRoomData

class ClassRoomInfoMainAdapter(private var userId : Int): RecyclerView.Adapter<ClassRoomInfoMainAdapter.ClassRoomInfoMainViewHolder>() {
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
            CustomDialog(holder.itemView.context).restrictDialog(
                holder.itemView.context,
                ReviewGlobals.isReviewed,
                MainGlobals.signInData!!.isUserReported,
                MainGlobals.signInData!!.isReviewInappropriate,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val intent = Intent(holder.itemView.context, InformationDetailActivity::class.java)
                    intent.apply {
                        putExtra("postId", questionMainData[position].postId)
                        putExtra("userId", userId)
                    }
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                })
        }
        
        if(position == (itemCount - 1)){
            holder.binding.lineQuestionAll.visibility = View.GONE
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