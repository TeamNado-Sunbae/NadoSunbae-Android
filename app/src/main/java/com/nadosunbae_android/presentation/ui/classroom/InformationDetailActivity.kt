package com.nadosunbae_android.presentation.ui.classroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.ActivityInformationDetailBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.classroom.viewmodel.InfoDetailViewModel
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel

class InformationDetailActivity : BaseActivity<ActivityInformationDetailBinding>(R.layout.activity_information_detail) {
    private val infoDetailViewModel: InfoDetailViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return InfoDetailViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPostId()
    }


    //postId값 받아야함
    private fun getPostId(){
        val postId = intent.getIntExtra("postId", 0)
        infoDetailViewModel.getInfoDetail(postId)

        infoDetailViewModel.infoDetailData.observe(this){
            binding.informationDetail = it
            binding.informationDetailCount = it.data.commentCount
        }


    }
}