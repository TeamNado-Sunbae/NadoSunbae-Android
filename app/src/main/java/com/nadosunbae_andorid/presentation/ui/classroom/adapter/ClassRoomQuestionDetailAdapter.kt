package com.nadosunbae_andorid.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_andorid.databinding.ItemQuestionDetailCommentBinding
import com.nadosunbae_andorid.databinding.ItemQuestionDetailQuestionerBinding
import com.nadosunbae_andorid.databinding.ItemQuestionDetailWriterBinding



class ClassRoomQuestionDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var like = 0
    private val WRITER_VIEW_TYPE = 0
    private val QUESTIONER_VIEW_TYPE = 1
    private val WRITER_COMMENT_VIEW_TYPE = 2

    var questionDetailData  = mutableListOf<ResponseClassRoomQuestionDetail.Data.Message>()


    fun setLike(num : Int) {
       like = num
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType){
            WRITER_VIEW_TYPE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemQuestionDetailWriterBinding.inflate(layoutInflater, parent, false)
                ClassRoomQuestionDetailWriterViewHolder(binding)
            }
            QUESTIONER_VIEW_TYPE ->{
                val layoutInflaters = LayoutInflater.from(parent.context)
                val binding = ItemQuestionDetailQuestionerBinding.inflate(layoutInflaters, parent, false)
                ClassRoomQuestionDetailQuestionerViewHolder(binding)
            } else -> {
                val layoutInflaters = LayoutInflater.from(parent.context)
                val binding = ItemQuestionDetailCommentBinding.inflate(layoutInflaters, parent, false)
                ClassRoomQuestionDetailWriterCommentViewHolder(binding)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(questionDetailData[position].writer.isQuestioner && position == 0){
            WRITER_VIEW_TYPE
        }else if(questionDetailData[position].writer.isQuestioner && position != 0) {
            WRITER_COMMENT_VIEW_TYPE
        }else if(!questionDetailData[position].writer.isQuestioner && position != 0){
            QUESTIONER_VIEW_TYPE
        }else{
            0
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if(holder is ClassRoomQuestionDetailWriterViewHolder){
            holder.onBind(questionDetailData[position], position)
        }else if(holder is ClassRoomQuestionDetailQuestionerViewHolder){
            holder.onBind(questionDetailData[position])
        }else if(holder is ClassRoomQuestionDetailWriterCommentViewHolder){
            holder.onBind(questionDetailData[position])
        }


    }

    override fun getItemCount(): Int {
        return questionDetailData.size
    }

    inner class ClassRoomQuestionDetailWriterViewHolder(
        val binding : ItemQuestionDetailWriterBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(questionDetailData : ResponseClassRoomQuestionDetail.Data.Message, position : Int){
            if(position == 0)
                binding.textQuestionDetailLikeCount.text = like.toString()

            binding.questionDetail = questionDetailData
            binding.executePendingBindings()
        }
    }

    inner class ClassRoomQuestionDetailQuestionerViewHolder(
        val binding : ItemQuestionDetailQuestionerBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(questionDetailData : ResponseClassRoomQuestionDetail.Data.Message){
            binding.questionDetailQuestioner = questionDetailData
            binding.executePendingBindings()
        }
    }

    inner class ClassRoomQuestionDetailWriterCommentViewHolder(
        val binding : ItemQuestionDetailCommentBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(questionDetailData: ResponseClassRoomQuestionDetail.Data.Message){
            binding.questionDetailWriterComment = questionDetailData
            binding.executePendingBindings()

        }
    }


    fun setQuestionDetail(questionDetailData: MutableList<ResponseClassRoomQuestionDetail.Data.Message>){
        this.questionDetailData = questionDetailData
        notifyDataSetChanged()

    }
}