package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemQuestionDetailCommentBinding
import com.nadosunbae_android.app.databinding.ItemQuestionDetailQuestionerBinding
import com.nadosunbae_android.app.databinding.ItemQuestionDetailWriterBinding
import com.nadosunbae_android.domain.model.classroom.QuestionDetailData


class ClassRoomQuestionDetailAdapter(context: Context, private var userId: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context = context
    private var like = 0
    private var likeSelect = false

    //View Type (WRITER -> 질문자, QUESTIONER -> 답변자, WRITER_COMMENT -> 질문자의 재 답변)
    private val WRITER_VIEW_TYPE = 0
    private val QUESTIONER_VIEW_TYPE = 1
    private val WRITER_COMMENT_VIEW_TYPE = 2


    var questionDetailData = mutableListOf<QuestionDetailData.Message>()
    lateinit var questionDetailUserData : QuestionDetailData

    fun setLike(num: Int, isLiked: Boolean) {
        like = num
        likeSelect = isLiked
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            WRITER_VIEW_TYPE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemQuestionDetailWriterBinding.inflate(layoutInflater, parent, false)
                ClassRoomQuestionDetailWriterViewHolder(binding)
            }
            QUESTIONER_VIEW_TYPE -> {
                val layoutInflaters = LayoutInflater.from(parent.context)
                val binding =
                    ItemQuestionDetailQuestionerBinding.inflate(layoutInflaters, parent, false)
                ClassRoomQuestionDetailQuestionerViewHolder(binding)
            }
            else -> {
                val layoutInflaters = LayoutInflater.from(parent.context)
                val binding =
                    ItemQuestionDetailCommentBinding.inflate(layoutInflaters, parent, false)
                ClassRoomQuestionDetailWriterCommentViewHolder(binding)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (questionDetailData[position].isQuestioner && position == 0) {
            WRITER_VIEW_TYPE
        } else if (questionDetailData[position].isQuestioner && position != 0) {
            WRITER_COMMENT_VIEW_TYPE
        } else if (!questionDetailData[position].isQuestioner && position != 0) {
            QUESTIONER_VIEW_TYPE
        } else {
            0
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            // 질문자
            is ClassRoomQuestionDetailWriterViewHolder -> {
                holder.onBind(questionDetailData[position], position)
                //좋아요 처리
                holder.binding.imgQuestionDetailLike.setOnClickListener {
                    itemLikeClickListener.onLikeClick(it)
                }
                with(holder.binding) {
                    imgQuestionDetailLike.isSelected = likeSelect
                    textQuestionDetailLikeCount.text = like.toString()
                }
                    if (questionDetailData[position].secondMajorName == "미진입") {
                        holder.binding.textQuestionDetailSecondStartMajor.visibility = View.GONE
                    }
                    holder.binding.imgQuestionDetailWriterMenu.setOnClickListener {
                        itemClickListener.onClick(it, lookForThirdParty( position, userId), writer)
                    }
                }

            //답변자 문답
            is ClassRoomQuestionDetailQuestionerViewHolder -> {
                holder.onBind(questionDetailData[position])
                if (questionDetailData[position].secondMajorName == "미진입") {
                    holder.binding.includeQuestionDetailQuestionerText.textQuestionDetailQuestionerSecondStartMajor.visibility = View.GONE
                }

                holder.binding.includeQuestionDetailQuestionerText.imgQuestionDetailQuestionerMenu.setOnClickListener {
                        itemClickListener.onClick(it, lookForThirdParty( position, userId), questioner)
                }
            }

            // 질문자 문답
            is ClassRoomQuestionDetailWriterCommentViewHolder -> {
                holder.onBind(questionDetailData[position])
                if (questionDetailData[position].secondMajorName == "미진입") {
                    holder.binding.includeQuestionDetailCommentText.textQuestionDetailWriterCommentSecondStartMajor.visibility = View.GONE
                }
                holder.binding.includeQuestionDetailCommentText.imgQuestionDetailWriterCommentMenu.setOnClickListener {
                    itemClickListener.onClick(it, lookForThirdParty( position, userId), writer)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return questionDetailData.size
    }
    //첫번쨰
    inner class ClassRoomQuestionDetailWriterViewHolder(
        val binding: ItemQuestionDetailWriterBinding,

        ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            questionDetailData: QuestionDetailData.Message,
            position: Int
        ) {
            if (position == 0)
                binding.textQuestionDetailLikeCount.text = like.toString()
            binding.questionDetail = questionDetailData
            binding.executePendingBindings()


        }
    }
    //두번째
    inner class ClassRoomQuestionDetailQuestionerViewHolder(
        val binding: ItemQuestionDetailQuestionerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(questionDetailData: QuestionDetailData.Message) {
            binding.includeQuestionDetailQuestionerText.questionDetailQuestioner =
                questionDetailData
            binding.includeQuestionDetailQuestionerUpdate.questionDetailQuestioner =
                questionDetailData
            binding.executePendingBindings()
        }

        fun visibleQuestionDetailComment(num: Int) {
            if (num == 1) {
                //수정시에
                binding.includeQuestionDetailQuestionerText.clQuestionDetailQuestionerText.visibility =
                    View.GONE
                binding.includeQuestionDetailQuestionerUpdate.clQuestionDetailQuestionerUpdate.visibility =
                    View.VISIBLE
            } else {
                //수정아닐때
                binding.includeQuestionDetailQuestionerText.clQuestionDetailQuestionerText.visibility =
                    View.VISIBLE
                binding.includeQuestionDetailQuestionerUpdate.clQuestionDetailQuestionerUpdate.visibility =
                    View.GONE
            }
        }


    }
    //3번쨰
    inner class ClassRoomQuestionDetailWriterCommentViewHolder(
        val binding: ItemQuestionDetailCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(questionDetailData: QuestionDetailData.Message) {
            binding.includeQuestionDetailCommentText.questionDetailWriterComment =
                questionDetailData
            binding.includeQuestionDetailCommentUpdate.questionDetailWriterComment =
                questionDetailData
            binding.executePendingBindings()
        }

        fun visibleQuestionDetailComment(num: Int) {
            if (num == 1) {
                //수정시에
                binding.includeQuestionDetailCommentText.clQuestionDetailCommentText.visibility =
                    View.GONE
                binding.includeQuestionDetailCommentUpdate.clQuetionDetailCommentUpdate.visibility =
                    View.VISIBLE
            } else {
                //수정아닐때
                binding.includeQuestionDetailCommentText.clQuestionDetailCommentText.visibility =
                    View.VISIBLE
                binding.includeQuestionDetailCommentUpdate.clQuetionDetailCommentUpdate.visibility =
                    View.GONE
            }
        }
    }


    fun setQuestionDetail(questionDetailData: MutableList<QuestionDetailData.Message>) {
        this.questionDetailData = questionDetailData
        notifyDataSetChanged()
    }

    fun setQuestionDetailUser(questionDetailUserData: QuestionDetailData){
        this.questionDetailUserData = questionDetailUserData
        notifyDataSetChanged()
    }


    //클릭 이벤트
    interface OnItemLikeClickListener {
        fun onLikeClick(v: View)
    }
    fun setItemLikeClickListener(onItemLikeClickListener: OnItemLikeClickListener){
        this.itemLikeClickListener = onItemLikeClickListener
    }
    private lateinit var itemLikeClickListener : OnItemLikeClickListener

    interface OnItemClickListener {
        fun onClick(v: View, position : Int, viewNum : Int)
    }

    fun setItemClickListener(onItemClickListener : OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener


    // writer -> 1, questioner -> 2, thirdParty -> 3
    private fun lookForThirdParty(position : Int, userId : Int) : Int{
        Log.d("questionOneToUserId", userId.toString())
        return if(!questionDetailData[position].isQuestioner && questionDetailData[position].writerId != userId) {
            thirdParty
        }else if(questionDetailUserData.answererId == userId) {
            questioner
        } else if(questionDetailUserData.questionerId == userId) {
            writer
        }else{
            return -1
        }
    }


    companion object{
        const val writer = 1
        const val questioner = 2
        const val thirdParty = 3
    }
}