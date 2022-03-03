package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityInformationDetailBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomInfoDetailAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.InfoDetailViewModel
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.classroom.InfoDetailData
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.domain.model.classroom.ReportItem
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.model.main.SelectableData
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationDetailActivity : BaseActivity<ActivityInformationDetailBinding>(R.layout.activity_information_detail) {
    private val infoDetailViewModel: InfoDetailViewModel by viewModel()
    private lateinit var classRoomInfoDetailAdapter : ClassRoomInfoDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInfoDetail()
        infoLike()
        clickBackBtn()
        initInfoCommentMenu()
        infoCommentMenu()
        infoCommentMenuClick()
        reportToast()
    }


    //정보 상세보기 서버 통신
    private fun initInfoDetail(){
        val postId = intent.getIntExtra("postId", 0)
        Log.d("infoPostId", postId.toString())
        val userId = intent.getIntExtra("userId", 0)

        infoDetailViewModel.setPostId(postId)
        infoDetailViewModel.getInfoDetail(postId)

        classRoomInfoDetailAdapter = ClassRoomInfoDetailAdapter(userId,this)
        binding.rcInformationDetailQuestionComment.adapter = classRoomInfoDetailAdapter
        infoDetailViewModel.infoDetailData.observe(this){
            // 원글 데이터 연결
            binding.informationDetail = it
            if(it.secondMajorName == "미진입"){
                binding.textInformationDetailQuestionSecondMajorStart.visibility = View.GONE
            }
            classRoomInfoDetailAdapter.setInfoDetail(it.commentList.filter {item -> !item.isDeleted } as MutableList<InfoDetailData.Comment>)
            binding.imgInformationCommentComplete.setOnClickListener {
                registerComment(postId)
                binding.etInformationComment.setText("")
            }

        }
    }
    //점 세개 메뉴 다이얼로그 초기화
    private fun initInfoCommentMenu(){
        infoDetailViewModel.dropDownSelected.value = SelectableData(3, "테스트", false)
    }

    //답글 점 세개 메뉴 클릭시 나오는 다이얼로그
    private fun infoCommentMenu(){
        classRoomInfoDetailAdapter.setItemClickListener(
            object : ClassRoomInfoDetailAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int, user: Int, commentId: Int) {
                    infoDetailViewModel.commentId.value = commentId
                    infoDetailViewModel.position.value = position
                    infoDetailViewModel.divisionPost.value = comment
                        if(user == 1){
                            val dropDown = mutableListOf<SelectableData>(
                                SelectableData(1, resources.getString(R.string.question_detail_delete), false)
                            )
                            showCustomDropDown(infoDetailViewModel,v, 160f.dpToPx, null, -1 * 16f.dpToPx, null, false, infoDetailViewModel.dropDownSelected.value!!.id, dropDown)
                        }else{
                            val dropDown = mutableListOf<SelectableData>(
                                SelectableData(2, resources.getString(R.string.question_detail_report), false))
                            showCustomDropDown(infoDetailViewModel,v, 160f.dpToPx, null, -1 * 16f.dpToPx, null, false, infoDetailViewModel.dropDownSelected.value!!.id, dropDown)
                        }
                }
            }
        )
    }

    //답글 메뉴 신고, 삭제 클릭시 이벤트
    private fun infoCommentMenuClick(){
        infoDetailViewModel.dropDownSelected.observe(this){
            val divisionPost = infoDetailViewModel.divisionPost.value ?: 0
            val position = infoDetailViewModel.position.value ?: 0
            val commentId = infoDetailViewModel.commentId.value ?: 0
            Log.d("infoDeleteDivision", divisionPost.toString())
            Log.d("infoDeleteCommentId", commentId.toString())
            Log.d("infoDeleteName", it.name)
            when(it.name){
                resources.getString(R.string.question_detail_delete) ->
                    deleteDialog(divisionPost,
                        setCheckMenu = {classRoomInfoDetailAdapter.setCheckMenu(delete, position)},
                        deleteComment = {infoDetailViewModel.deleteComment(commentId)},
                        deleteWrite = {},
                    )
                resources.getString(R.string.question_detail_report) -> floatReportReasonDialog()

            }


        }


    }


    //정보 답글 삭제
    private fun deleteDialog(divisionPost : Int, setCheckMenu : () -> Unit, deleteComment : () -> Unit, deleteWrite : () -> Unit ){
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                resources.getString(R.string.alert_delete_review_title),
                resources.getString(R.string.alert_delete_review_complete),
                resources.getString(R.string.alert_delete_review_cancel)
            ),
            complete = {
                if(divisionPost == 1){
                    deleteComment()
                }else{
                    deleteWrite()
                }
                setCheckMenu()
            },
            cancel = {}
        )
    }

    //신고 사유 다이얼로그 띄우기
    private fun floatReportReasonDialog(){
        val divisionPost = infoDetailViewModel.divisionPost.value ?: 0
        val dialog = CustomDialog(this)
        dialog.reportDialog(this)
        dialog.setReportClickListener(
            object : CustomDialog.ReportClickListener{
                override fun reportClick(text: String) {
                    infoDetailViewModel.reportReasonInfo.value = text
                    reportDialog(divisionPost)
                }
            }
        )


    }


    //신고 다이얼로그 띄우기
    private fun reportDialog(divisionPost: Int) {
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                resources.getString(R.string.request_report),
                resources.getString(R.string.agree_report),
                resources.getString(R.string.disagree_report)
            ),
            complete = {
                when (divisionPost) {
                    comment -> infoDetailViewModel.postReport(
                        ReportItem(
                            infoDetailViewModel.commentId.value ?: 0,
                        comment,
                            infoDetailViewModel.reportReasonInfo.value ?: "")
                    )
                }
            },
            cancel = {

            }
        )

    }
    //신고하기 토스트 띄우기
    private fun reportToast() {
        infoDetailViewModel.reportStatusInfo.observe(this) {
            if (it == 200) {
                Toast.makeText(this, "신고가 접수되었습니다", Toast.LENGTH_SHORT).show()
            } else if (it == 400) {
                Toast.makeText(this, "이미 신고한 댓글입니다.", Toast.LENGTH_SHORT).show()
            }

        }

    }


    //정보 상세보기 댓글 달기
    private fun registerComment(postId : Int){
        infoDetailViewModel.postInfoCommentWrite(
            QuestionCommentWriteItem(
            postId, binding.etInformationComment.text.toString()
        ) )

        infoDetailViewModel.registerInfoComment.observe(this){
            if(it.success){
                infoDetailViewModel.getInfoDetail(postId)
            }
        }
    }

    //정보 좋아요 서버 통신
    private fun infoLike(){
        binding.imgInformationDetailQuestionLike.setOnClickListener {
            val likePostId = infoDetailViewModel.infoPostId.value ?: 0
            infoDetailViewModel.postClassRoomInfoLike(LikeItem(likePostId,2 ))
            infoDetailViewModel.getInfoDetail(likePostId)
        }
    }

    //정보 뒤로가기
    private fun clickBackBtn(){
        binding.imgInformationDetailTitleBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val update = 1
        const val report = 2
        const val delete = 3


        //원글 댓글 분류
        const val post = 2
        const val comment = 3
    }
}