package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityQuestionDetailBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomQuestionDetailAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.QuestionDetailViewModel
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.domain.model.classroom.QuestionDetailData
import com.nadosunbae_android.domain.model.like.LikeItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionDetailActivity :
    BaseActivity<ActivityQuestionDetailBinding>(R.layout.activity_question_detail) {
    private val questionDetailViewModel: QuestionDetailViewModel by viewModel()



    private lateinit var classRoomQuestionDetailAdapter: ClassRoomQuestionDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initQuestionDetail()
        questionAllDetailLike()
        backBtn()
    }



    // 전체 질문 상세보기
    private fun initQuestionDetail() {
        val postId = intent.getIntExtra("postId", 0)
        questionDetailViewModel.setLikePostId(postId)
        val userId = intent.getIntExtra("userId", 0)
        val all = intent.getIntExtra("all", 0)
        val myPageNum = intent.getIntExtra("myPageNum", 0)
        Log.d("postId", postId.toString())
        Log.d("userId", userId.toString())
        questionDetailViewModel.getClassRoomQuestionDetail(postId)
        classRoomQuestionDetailAdapter = ClassRoomQuestionDetailAdapter(this)
        binding.rcQuestionDetail.adapter = classRoomQuestionDetailAdapter

        questionDetailViewModel.questionDetailData.observe(this){
            classRoomQuestionDetailAdapter.setLike(it.likeCount, it.isLiked)
            classRoomQuestionDetailAdapter.setQuestionDetail(it.messageList as MutableList<QuestionDetailData.Message>)
            registerComment(postId)

            //1:1질문 타인 글 쓰는거 막기
           if(myPageNum != 1 && all != 1 && userId != it.questionerId && userId != it.answererId){
                binding.etQuestionComment.isEnabled = false
                binding.etQuestionComment.hint = getString(R.string.text_comment_impossible)
            }

            //전체질문 1:1질문 구분
            if(all == 1){
                binding.textQuestionDetailTitle.text = "질문"
            }else{
                binding.textQuestionDetailTitle.text = "1:1질문"
            }
        }
    }
    //전체 질문 상세보기 좋아요
    private fun questionAllDetailLike(){
        classRoomQuestionDetailAdapter.setItemClickListener(
            object : ClassRoomQuestionDetailAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    val postId = questionDetailViewModel.likePostId.value ?: 0

                    questionDetailViewModel.postClassRoomLike(LikeItem(postId, 3))
                    questionDetailViewModel.getClassRoomQuestionDetail(postId)
                }
            }
        )

    }

    // 전체 질문 상세 댓글 등록
    private fun registerComment(postId : Int){
        binding.imgQuestionCommentComplete.setOnClickListener {
            questionDetailViewModel.postQuestionCommentWrite(
                QuestionCommentWriteItem(
                postId, binding.etQuestionComment.text.toString()
            )
            )
            binding.etQuestionComment.setText("")
        }

        questionDetailViewModel.registerComment.observe(this){
            if(it.success){
                questionDetailViewModel.getClassRoomQuestionDetail(postId)
            }
        }

    }


    //좋아요 클릭 및 서버 통신



    //뒤로가기
    private fun backBtn(){
        binding.imgQuestionDetailTitle.setOnClickListener {
            finish()
        }
    }
}