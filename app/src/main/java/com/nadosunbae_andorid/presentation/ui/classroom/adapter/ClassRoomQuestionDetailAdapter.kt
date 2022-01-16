package com.nadosunbae_andorid.presentation.ui.classroom.adapter

import android.content.Context
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_andorid.databinding.ItemQuestionDetailCommentBinding
import com.nadosunbae_andorid.databinding.ItemQuestionDetailQuestionerBinding
import com.nadosunbae_andorid.databinding.ItemQuestionDetailWriterBinding


class ClassRoomQuestionDetailAdapter(context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context = context
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
        when (holder) {
            is ClassRoomQuestionDetailWriterViewHolder -> {
                holder.onBind(questionDetailData[position], position)
                holder.binding.imgQuestionDetailWriterMenu.setOnClickListener {
                    Log.d("imgQuestionMenu", "작동은 되는 겁니까?")
                    val wrapperStyle = ContextThemeWrapper(context, R.style.Widget_App_PopupMenu)
                    val popup = PopupMenu(wrapperStyle, holder.binding.imgQuestionDetailWriterMenu)
                    popup.menuInflater.inflate(R.menu.menu_question_detail_update, popup.menu)

                    popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                        when(it.itemId){
                            R.id.question_detail_update ->true
                            else -> false
                        }
                    })
                    popup.show()
                }
            }
            is ClassRoomQuestionDetailQuestionerViewHolder -> {
                holder.onBind(questionDetailData[position])
            }
            is ClassRoomQuestionDetailWriterCommentViewHolder -> {
                holder.onBind(questionDetailData[position])
                holder.binding.includeQuestionDetailCommentText.imgQuestionDetailWriterCommentMenu.setOnClickListener {
                    Log.d("imgQuestionMenu", "작동은 되는 겁니까?")
                    val wrapperStyle = ContextThemeWrapper(context, R.style.Widget_App_PopupMenu)
                    val popup = PopupMenu(wrapperStyle, holder.binding.includeQuestionDetailCommentText.imgQuestionDetailWriterCommentMenu)
                    popup.menuInflater.inflate(R.menu.menu_question_detail_update, popup.menu)

                    popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                        when(it.itemId){
                            //수정버튼 눌렀을 때
                            R.id.question_detail_update -> {
                                with(holder){
                                    holder.visibleQuestionDetailComment(1)
                                    //취소
                                    binding.includeQuestionDetailCommentUpdate.textQuestionDetailWriterCommentContentCancel.setOnClickListener {
                                        visibleQuestionDetailComment(0)
                                    }
                                    //저장
                                    binding.includeQuestionDetailCommentUpdate.textQuestionDetailWriterCommentContentSave.setOnClickListener {
                                        visibleQuestionDetailComment(0)
                                    }
                                }

                                true
                            }
                            else -> false
                        }
                    })
                    popup.show()
                }
            }
        }



    }

    override fun getItemCount(): Int {
        return questionDetailData.size
    }

    inner class ClassRoomQuestionDetailWriterViewHolder(
        val binding : ItemQuestionDetailWriterBinding,

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
            binding.includeQuestionDetailCommentText.questionDetailWriterComment = questionDetailData
            binding.includeQuestionDetailCommentUpdate.questionDetailWriterComment = questionDetailData
            binding.executePendingBindings()
        }

        fun visibleQuestionDetailComment(num : Int ){
            if(num == 1){
                //수정시에
                binding.includeQuestionDetailCommentText.clQuestionDetailCommentText.visibility = View.GONE
                binding.includeQuestionDetailCommentUpdate.clQuetionDetailCommentUpdate.visibility = View.VISIBLE
            }else{
                //수정아닐때
                binding.includeQuestionDetailCommentText.clQuestionDetailCommentText.visibility = View.VISIBLE
                binding.includeQuestionDetailCommentUpdate.clQuetionDetailCommentUpdate.visibility = View.GONE
            }
        }
    }


    fun setQuestionDetail(questionDetailData: MutableList<ResponseClassRoomQuestionDetail.Data.Message>){
        this.questionDetailData = questionDetailData
        notifyDataSetChanged()

    }
    

}