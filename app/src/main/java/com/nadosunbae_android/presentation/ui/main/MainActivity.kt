package com.nadosunbae_android.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.sign.ResponseSignIn
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
import com.nadosunbae_android.util.popFragmentBackStack
import kotlin.math.sign

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

        initMajorList()
        setDefaultMajor()
        getSignDataFromIntent()
        classRoomBack()
        clickBottomNav()
    }




    //바텀네비 클릭( 2-> 과방탭, 3 -> 마이페이지)
    private fun clickBottomNav(){
        mainViewModel.notificationClickNum.observe(this){
            when(it){
                2 -> binding.btNvMain.menu.findItem(R.id.navigation_room).setChecked(true)
                3 -> binding.btNvMain.menu.findItem(R.id.navigation_mypage).setChecked(true)
            }
        }


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
                2 -> changeFragment(R.id.fragment_container_main,AskEveryoneFragment(), "askEveryOne")

                1 -> changeFragmentNoBackStack(R.id.fragment_container_main, ClassRoomFragment())

                3 -> changeFragment(R.id.fragment_container_main, SeniorFragment(),"senior")

                4 -> changeFragment(R.id.fragment_container_main, SeniorPersonalFragment(),"seniorPersonal")

                5 -> changeFragment(R.id.fragment_container_main, ClassRoomReviewFragment(),"classRoomReview")

                6 -> changeFragment(R.id.fragment_container_main, MyPageFragment(), "myPage")
            }
        })
    }

    //과방 뒤로가기 전환
    private fun classRoomBack(){
        mainViewModel.classRoomBackFragmentNum.observe(this){
            when (it) {
                1 -> popFragmentBackStack("seniorPersonal")
                2 -> popFragmentBackStack("senior")
            }

        }

    }

    // 로그인 response 전달  받기
    private fun getSignDataFromIntent() {
        // real code
        //val signData = intent.getSerializableExtra("signData") as ResponseSignIn

        //test data
        val signData = ResponseSignIn(ResponseSignIn.Data("d", ResponseSignIn.Data.User("ku2@korea.ac.kr", 3, "건축사회공학부", true, 42, "소프트웨어벤처", 1, 2)), "test", 200, true)
        // null check
        if (signData != null) {
            mainViewModel.setSignData(signData)

            val user = signData.data.user
            // 본전공이 default 선택
            mainViewModel.setSelectedMajor(MajorData(user.firstMajorId, user.secondMajorName))
            mainViewModel.setFirstMajor(MajorData(user.firstMajorId, user.firstMajorName))
            mainViewModel.setSecondMajor(MajorData(user.secondMajorId, user.secondMajorName))
        }
    }


    // 학과 목록 불러오기
    private fun initMajorList() {
        mainViewModel.getMajorList(1)
    }

    // 본전공이 선택되어 있도록
    private fun setDefaultMajor() {
        mainViewModel.signData.observe(this) {
            val signData = mainViewModel.signData.value

            // null check
            if (signData != null) {
                val user = signData.data.user
                mainViewModel.setSelectedMajor(MajorData(user.firstMajorId, user.firstMajorName))
            }
        }
    }



}