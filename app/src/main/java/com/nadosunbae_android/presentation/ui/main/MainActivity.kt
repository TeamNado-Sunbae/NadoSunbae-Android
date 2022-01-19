package com.nadosunbae_android.presentation.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.ui.MajorData
import com.nadosunbae_android.databinding.ActivityMainBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.classroom.*
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
        deviceToken()
        initMajorList()
        setDefaultMajor()
    }


    // 디바이스 등록
    private fun deviceToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener{ task ->
            if(!task.isSuccessful){
                Log.d("deviceToken", "디바이스 토큰 정보 가저오기 실패", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            Log.d("token", token)

        } )
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
            when (it) {
                2 -> changeFragment(R.id.fragment_container_main,AskEveryoneFragment())

                1 -> changeFragmentNoBackStack(R.id.fragment_container_main, ClassRoomFragment())

                3 -> changeFragment(R.id.fragment_container_main, SeniorFragment())

                4 -> changeFragment(R.id.fragment_container_main, SeniorPersonalFragment())

                5 -> changeFragment(R.id.fragment_container_main, ClassRoomReviewFragment())
            }
        })
    }


    // 학과 목록 불러오기
    private fun initMajorList() {
        mainViewModel.getMajorList(1)
    }

    // 본전공이 선택되어 있도록
    private fun setDefaultMajor() {
        mainViewModel.setSelectedMajor(MajorData(5, "경영학과"))
    }



}