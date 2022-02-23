package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityQuestionDetailBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomQuestionDetailAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.QuestionDetailViewModel
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.domain.model.classroom.QuestionDetailData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.model.main.SelectableData
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.nadosunbae_android.app.databinding.ItemQuestionDetailWriterBinding as ItemQuestionDetailWriterBinding

class QuestionDetailActivity :
    BaseActivity<ActivityQuestionDetailBinding>(R.layout.activity_question_detail) {
    private val questionDetailViewModel: QuestionDetailViewModel by viewModel()


    private lateinit var classRoomQuestionDetailAdapter: ClassRoomQuestionDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initQuestionOneToOneMenu()
        initQuestionDetail()
        questionAllDetailLike()
        backBtn()
        questionOneToOneMenu()
    }


    // 전체 질문 상세보기
    private fun initQuestionDetail() {
        val postId = intent.getIntExtra("postId", 0)
        questionDetailViewModel.setLikePostId(postId)
        val userId = intent.getIntExtra("userId", 0)
        val all = intent.getIntExtra("all", 0)
        questionDetailViewModel.setDivisionQuestion(all)
        val myPageNum = intent.getIntExtra("myPageNum", 0)
        Log.d("postId", postId.toString())
        Log.d("userId", userId.toString())
        questionDetailViewModel.getClassRoomQuestionDetail(postId)
        classRoomQuestionDetailAdapter = ClassRoomQuestionDetailAdapter(this, userId)
        binding.rcQuestionDetail.adapter = classRoomQuestionDetailAdapter

        questionDetailViewModel.questionDetailData.observe(this) {
            with(classRoomQuestionDetailAdapter) {
                setQuestionDetailUser(it)
                setLike(it.likeCount, it.isLiked)
                setQuestionDetail(it.messageList as MutableList<QuestionDetailData.Message>)
            }
            registerComment(postId)

            //1:1질문 타인 글 쓰는거 막기
            if (myPageNum != 1 && all != 1 && userId != it.questionerId && userId != it.answererId) {
                binding.etQuestionComment.isEnabled = false
                binding.etQuestionComment.hint = getString(R.string.text_comment_impossible)
            }

            //전체질문 1:1질문 구분
            if (all == 1) {
                binding.textQuestionDetailTitle.text = "질문"
            } else {
                binding.textQuestionDetailTitle.text = "1:1질문"
            }
        }
    }

    //전체 질문 상세보기 좋아요
    private fun questionAllDetailLike() {
        val divisionNum = questionDetailViewModel.divisionQuestion.value
        classRoomQuestionDetailAdapter.setItemLikeClickListener(
            object : ClassRoomQuestionDetailAdapter.OnItemLikeClickListener {
                override fun onLikeClick(v: View) {
                    val postId = questionDetailViewModel.likePostId.value ?: 0
                    if (divisionNum == 1) {
                        Log.d("전체 질문 좋아요", "서버 통신 하는 중")
                        questionDetailViewModel.postClassRoomLike(LikeItem(postId, 3))
                        questionDetailViewModel.getClassRoomQuestionDetail(postId)
                    } else {
                        Log.d("1:1 질문 좋아요", "서버 통신 하는 중")
                        questionDetailViewModel.postClassRoomLike(LikeItem(postId, 4))
                        questionDetailViewModel.getClassRoomQuestionDetail(postId)
                    }
                }
            }
        )

    }

    // 전체 질문 상세 댓글 등록
    private fun registerComment(postId: Int) {
        binding.imgQuestionCommentComplete.setOnClickListener {
            questionDetailViewModel.postQuestionCommentWrite(
                QuestionCommentWriteItem(
                    postId, binding.etQuestionComment.text.toString()
                )
            )
            binding.etQuestionComment.setText("")
        }

        questionDetailViewModel.registerComment.observe(this) {
            if (it.success) {
                questionDetailViewModel.getClassRoomQuestionDetail(postId)
            }
        }

    }
    private fun initQuestionOneToOneMenu(){
        questionDetailViewModel.dropDownSelected.value = SelectableData(3, "테스트", false)
    }


    // 1:1 질문 점 세개 메뉴 분기처리 (position : 1 -> 질문자, 2 -> 답변자, 3 -> 질문자 재답변) 나머지는 제 3자
    // 1 -> 질문자 뷰, 2 -> 답변자 뷰
    private fun questionOneToOneMenu() {
        classRoomQuestionDetailAdapter.setItemClickListener(
            object : ClassRoomQuestionDetailAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int, viewNum : Int) {
                    Log.d("oneToOneVIew", v.toString())
                    Log.d("oneToOneNum", "$position+$viewNum")
                    if((position == 1 && viewNum == 1) or (position == 2 && viewNum == 2)){
                        val dropDown = mutableListOf<SelectableData>(
                            SelectableData(1, resources.getString(R.string.question_detail_update), true),
                            SelectableData(2, resources.getString(R.string.question_detail_delete), false)
                        )
                        showCustomDropDown(questionDetailViewModel,v, 160f.dpToPx, null, -1 * 16f.dpToPx, null, true, questionDetailViewModel.dropDownSelected.value!!.id, dropDown)
                    }else if((position == 1 && viewNum == 2) or (position == 3)){
                        val dropDown = mutableListOf<SelectableData>(
                            SelectableData(1, resources.getString(R.string.question_detail_report), true),
                        )
                        showCustomDropDown(questionDetailViewModel,v, 160f.dpToPx, null, -1 * 16f.dpToPx, null, true, questionDetailViewModel.dropDownSelected.value!!.id, dropDown)
                    }else if(position == 2 && viewNum == 1){
                        val dropDown = mutableListOf<SelectableData>(
                            SelectableData(1, resources.getString(R.string.question_detail_report), true),
                            SelectableData(2, resources.getString(R.string.question_detail_delete), false)
                        )
                        showCustomDropDown(questionDetailViewModel,v, 160f.dpToPx, null, -1 * 16f.dpToPx, null, true, questionDetailViewModel.dropDownSelected.value!!.id, dropDown)
                    }

                }
            })
    }


    //뒤로가기
    private fun backBtn() {
        binding.imgQuestionDetailTitle.setOnClickListener {
            finish()
        }
    }
}