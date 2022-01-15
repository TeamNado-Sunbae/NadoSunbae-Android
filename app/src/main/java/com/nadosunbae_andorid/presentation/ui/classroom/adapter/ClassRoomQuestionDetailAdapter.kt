package com.nadosunbae_andorid.presentation.ui.classroom.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_andorid.databinding.ItemQuestionDetailQuestionerBinding
import com.nadosunbae_andorid.databinding.ItemQuestionDetailWriterBinding

const val WRITER_VIEW_TYPE = 0
const val QUESTIONER_VIEW_TYPE = 1

class ClassRoomQuestionDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var questionDetailData  = mutableListOf<ResponseClassRoomQuestionDetail.Data>()




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
            else ->{
                val layoutInflaters = LayoutInflater.from(parent.context)
                val bindings = ItemQuestionDetailQuestionerBinding.inflate(layoutInflaters, parent, false)
                ClassRoomQuestionDetailQuestionerViewHolder(bindings)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(questionDetailData[position].messageList[position].writer.isQuestioner){
            WRITER_VIEW_TYPE
        }else{
            QUESTIONER_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if(holder is ClassRoomQuestionDetailWriterViewHolder){
            holder.onBind(questionDetailData[position])
        }else if(holder is ClassRoomQuestionDetailQuestionerViewHolder){
            holder.onBinds(questionDetailData[position].messageList[position])
        }
    }

    override fun getItemCount(): Int {
        return questionDetailData.size
    }

    inner class ClassRoomQuestionDetailWriterViewHolder(
        val binding : ItemQuestionDetailWriterBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(questionDetailData : ResponseClassRoomQuestionDetail.Data){
            binding.questionDetail = questionDetailData
            binding.executePendingBindings()
        }
    }

    inner class ClassRoomQuestionDetailQuestionerViewHolder(
        val bindings : ItemQuestionDetailQuestionerBinding
    ) : RecyclerView.ViewHolder(bindings.root){
        fun onBinds(questionDetailData : ResponseClassRoomQuestionDetail.Data.Message){
            bindings.questionDetailQuestioner = questionDetailData
            bindings.executePendingBindings()
        }
    }


    fun setQuestionDetail(questionDetailData: MutableList<ResponseClassRoomQuestionDetail.Data>){
        this.questionDetailData = questionDetailData
        notifyDataSetChanged()

    }
}