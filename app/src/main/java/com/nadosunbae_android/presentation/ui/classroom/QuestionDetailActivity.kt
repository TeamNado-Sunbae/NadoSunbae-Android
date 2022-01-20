package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_android.databinding.ActivityQuestionDetailBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomQuestionDetailAdapter
import com.nadosunbae_android.presentation.ui.classroom.viewmodel.QuestionDetailViewModel
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel

class QuestionDetailActivity :
    BaseActivity<ActivityQuestionDetailBinding>(R.layout.activity_question_detail) {
    private val questionDetailViewModel: QuestionDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QuestionDetailViewModel() as T
            }
        }
    }



    private lateinit var classRoomQuestionDetailAdapter: ClassRoomQuestionDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initQuestionDetail()
        divisionQuestionDetail()
        backBtn()
    }



    // 전체 질문 상세보기
    private fun initQuestionDetail() {
        val postId = intent.getIntExtra("postId", 0)
        questionDetailViewModel.getClassRoomQuestionDetail(postId)
        classRoomQuestionDetailAdapter = ClassRoomQuestionDetailAdapter(this)
        binding.rcQuestionDetail.adapter = classRoomQuestionDetailAdapter

        questionDetailViewModel.questionDetailData.observe(this){
            classRoomQuestionDetailAdapter.setLike(it.data.like.likeCount, it.data.like.isLiked)
            classRoomQuestionDetailAdapter.setQuestionDetail(it.data.messageList as MutableList<ResponseClassRoomQuestionDetail.Data.Message>)
            registerComment(postId)
        }


    }
    // 전체 질문 상세 댓글 등록
    private fun registerComment(postId : Int){
        binding.imgQuestionCommentComplete.setOnClickListener {
            questionDetailViewModel.postQuestionCommentWrite(RequestQuestionCommentWriteData(
                postId, binding.etQuestionComment.text.toString()
            ))
        }

        questionDetailViewModel.registerComment.observe(this){
            if(it.success){
                binding.etQuestionComment.setText("")
                questionDetailViewModel.getClassRoomQuestionDetail(postId)
            }
        }

    }


    //전체 질문 1:1질문 구분
    private fun divisionQuestionDetail(){
        val all = intent.getIntExtra("all", 1)
        if(all == 1){
            binding.textQuestionDetailTitle.text = "질문"
        }
    }

    //뒤로가기
    private fun backBtn(){
        binding.imgQuestionDetailTitle.setOnClickListener {
            finish()
        }

    }
}