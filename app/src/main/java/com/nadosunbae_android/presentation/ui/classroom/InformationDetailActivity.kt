package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.classroom.ResponseInfoDetailData
import com.nadosunbae_android.databinding.ActivityInformationDetailBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomInfoDetailAdapter
import com.nadosunbae_android.presentation.ui.classroom.viewmodel.InfoDetailViewModel

class InformationDetailActivity : BaseActivity<ActivityInformationDetailBinding>(R.layout.activity_information_detail) {
    private val infoDetailViewModel: InfoDetailViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return InfoDetailViewModel() as T
            }
        }
    }
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
            if(it.data.writer.secondMajorName == "미진입"){
                binding.textInformationDetailQuestionSecondMajorStart.visibility = View.GONE
            }

            classRoomInfoDetailAdapter.setInfoDetail(it.data.commentList as MutableList<ResponseInfoDetailData.Data.Comment>)
        }
    }
}