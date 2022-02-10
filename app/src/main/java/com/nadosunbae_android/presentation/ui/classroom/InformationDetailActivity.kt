package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.ResponseInfoDetailData
import com.nadosunbae_android.databinding.ActivityInformationDetailBinding
import com.nadosunbae_android.model.classroom.InfoDetailData
import com.nadosunbae_android.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomInfoDetailAdapter
import com.nadosunbae_android.presentation.ui.classroom.viewmodel.InfoDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationDetailActivity : BaseActivity<ActivityInformationDetailBinding>(R.layout.activity_information_detail) {
    private val infoDetailViewModel: InfoDetailViewModel by viewModel()
    private lateinit var classRoomInfoDetailAdapter : ClassRoomInfoDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInfoDetail()
    }


    //정보 상세보기 서버 통신
    private fun initInfoDetail(){
        val postId = intent.getIntExtra("postId", 0)
        infoDetailViewModel.getInfoDetail(postId)

        classRoomInfoDetailAdapter = ClassRoomInfoDetailAdapter()
        binding.rcInformationDetailQuestionComment.adapter = classRoomInfoDetailAdapter
        infoDetailViewModel.infoDetailData.observe(this){
            binding.informationDetail = it
            if(it.secondMajorName == "미진입"){
                binding.textInformationDetailQuestionSecondMajorStart.visibility = View.GONE
            }
            classRoomInfoDetailAdapter.setInfoDetail(it.commentList as MutableList<InfoDetailData.Comment>)
            binding.imgInformationCommentComplete.setOnClickListener {
                registerComment(postId)
                binding.etInformationComment.setText("")
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
}