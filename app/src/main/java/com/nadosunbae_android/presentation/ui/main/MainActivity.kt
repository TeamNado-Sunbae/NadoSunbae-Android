package com.nadosunbae_android.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.ActivityMainBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.classroom.AskEveryoneFragment
import com.nadosunbae_android.presentation.ui.classroom.ClassRoomFragment
import com.nadosunbae_android.presentation.ui.classroom.SeniorFragment
import com.nadosunbae_android.presentation.ui.classroom.SeniorPersonalFragment
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.mypage.MyPageFragment
import com.nadosunbae_android.presentation.ui.notification.NotificationFragment
import com.nadosunbae_android.presentation.ui.review.ReviewFragment
import com.nadosunbae_android.util.changeFragment
import com.nadosunbae_android.util.changeFragmentNoBackStack

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
               return MainViewModel() as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNav()
        classRoomFragmentChange()
    }


    //바텀네비
    private fun initBottomNav(){
        // 첫 프래그먼트
        changeFragmentNoBackStack(R.id.fragment_container_main, ReviewFragment())

        binding.btNvMain.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_review -> {
                    changeFragmentNoBackStack(R.id.fragment_container_main,ReviewFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_room -> {
                    mainViewModel.classRoomNum.value = 1
                    changeFragmentNoBackStack(R.id.fragment_container_main,ClassRoomFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_notice -> {
                    changeFragmentNoBackStack(R.id.fragment_container_main,NotificationFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_mypage -> {
                    changeFragmentNoBackStack(R.id.fragment_container_main,MyPageFragment())
                    return@setOnItemSelectedListener true
                }
            }
            true
        }
    }

    //과방 프레그먼트 전환
    private fun classRoomFragmentChange(){
        mainViewModel.classRoomFragmentNum.observe(this, Observer {
            if(it==2){
                changeFragment(R.id.fragment_container_main,AskEveryoneFragment())
            }else if(it == 1){
                changeFragmentNoBackStack(R.id.fragment_container_main, ClassRoomFragment())
            }else if(it == 3){
                changeFragment(R.id.fragment_container_main, SeniorFragment())
            }else if(it == 4){
                changeFragment(R.id.fragment_container_main, SeniorPersonalFragment())
            }
        })
    }







}