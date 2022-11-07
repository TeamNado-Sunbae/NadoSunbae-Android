package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.classroom.ClassRoomData

class ClassRoomQuestionMainAdapter(private val num: Int, private val userId: Int, private val myPageNum : Int) :
    RecyclerView.Adapter<ClassRoomQuestionMainAdapter.ClassRoomQuestionMainViewHolder>() {
    var questionMainData = listOf<ClassRoomData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomQuestionMainAdapter.ClassRoomQuestionMainViewHolder {
        val binding = ItemQuestionAllBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomQuestionMainViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomQuestionMainAdapter.ClassRoomQuestionMainViewHolder,
        position: Int
    ) {

        holder.onBind(questionMainData[position])
        holder.binding.root.setOnClickListener {
            CustomDialog(holder.itemView.context).restrictDialog(
                holder.itemView.context,
                ReviewGlobals.isReviewed,
                MainGlobals.signInData!!.isUserReported,
                MainGlobals.signInData!!.isReviewInappropriate,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
                    intent.apply {
                        putExtra("myPageNum",myPageNum)
                        putExtra("userId", userId)
                        putExtra("postId", questionMainData[position].postId)
                        putExtra("all", num)
                    }
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                }
            )
        }
        if(position==(itemCount - 1)){
            holder.binding.lineQuestionAll.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return if (questionMainData.size < 6) questionMainData.size else 5

    }

    inner class ClassRoomQuestionMainViewHolder(
        val binding: ItemQuestionAllBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(questionMainData: ClassRoomData) {
            binding.apply {
                questionMain = questionMainData
                executePendingBindings()
            }
        }
    }

    fun setQuestionMain(questionMainData: List<ClassRoomData>) {
        this.questionMainData = questionMainData
        notifyDataSetChanged()
    }


}