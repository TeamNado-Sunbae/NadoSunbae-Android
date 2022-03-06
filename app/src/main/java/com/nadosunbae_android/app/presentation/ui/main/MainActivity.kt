package com.nadosunbae_android.app.presentation.ui.main

import android.os.Bundle
import androidx.core.view.doOnAttach
import androidx.lifecycle.Observer
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import androidx.viewpager2.widget.ViewPager2
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMainBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.*
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.AppInfoFragment
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageBlockFragment
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageFragment
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageSettingFragment
import com.nadosunbae_android.app.presentation.ui.notification.NotificationFragment
import com.nadosunbae_android.app.presentation.ui.review.ReviewFragment
import com.nadosunbae_android.app.util.DateUtil
import com.nadosunbae_android.app.util.changeFragment
import com.nadosunbae_android.app.util.changeFragmentNoBackStack
import com.nadosunbae_android.app.util.popFragmentBackStack
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.domain.model.sign.SignInData
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DateUtil.initTimeZone()

        initBottomNav()

        classRoomFragmentChange()

        initMajorList()
        setDefaultMajor()
        getSignDataFromIntent()
        classRoomBack()
        // clickBottomNav()

        myPageFragmentChange()
        initClickProfile()
    }




    //바텀네비 클릭( 2-> 과방탭, 3 -> 마이페이지)
    /* private fun clickBottomNav(){
         mainViewModel.notificationClickNum.observe(this){
             when(it){
                 2 -> binding.btNvMain.menu.findItem(R.id.navigation_room).setChecked(true)
                 3 -> binding.btNvMain.menu.findItem(R.id.navigation_mypage).setChecked(true)
             }
         }


     } */

    private fun firebaseLogTab(tab: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, getString(R.string.ga_activity_main))
            param(FirebaseAnalytics.Param.SCREEN_NAME, tab)
        }
    }


    //바텀네비
    private fun initBottomNav(){
        //바텀 네비 아이템 클릭된 것 처럼 보이도록 ( 4-> 마이페이지, 2 -> 과방)
        // 첫 프래그먼트 설정 (닉네임 클릭시 마이페이지 및 선배 개인페이지를 위해)
        mainViewModel.bottomNavItem.observe(this){
            when (it) {
                4 -> {
                    binding.btNvMain.selectedItemId= R.id.navigation_mypage

                }
                2 -> {
                    binding.btNvMain.selectedItemId = R.id.navigation_room
                    changeFragmentNoBackStack(R.id.fragment_container_main, SeniorPersonalFragment())
                }
                else -> {
                    changeFragmentNoBackStack(R.id.fragment_container_main, ReviewFragment())
                }
            }
        }
        binding.btNvMain.itemIconTintList = null
        binding.btNvMain.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_review -> {
                    changeFragmentNoBackStack(R.id.fragment_container_main,ReviewFragment())
                    firebaseLogTab(getString(R.string.ga_tab_review))
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_room -> {
                    mainViewModel.classRoomNum.value = 1
                    changeFragmentNoBackStack(R.id.fragment_container_main,ClassRoomFragment())
                    firebaseLogTab(getString(R.string.ga_tab_classroom))
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_notice -> {
                    changeFragmentNoBackStack(R.id.fragment_container_main,NotificationFragment())
                    firebaseLogTab(getString(R.string.ga_tab_notification))
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_mypage -> {
                    changeFragmentNoBackStack(R.id.fragment_container_main,MyPageFragment())
                    firebaseLogTab(getString(R.string.ga_tab_mypage))
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
    //프로필 및 닉네임 클릭시 전환되는 데이터 받아오는 부분
    private fun initClickProfile(){
        mainViewModel.bottomNavItem.value = intent.getIntExtra("bottomNavItem", -1)
        mainViewModel.seniorId.value = intent.getIntExtra("seniorId", -1)
        mainViewModel.initLoading.value = intent.getBooleanExtra("loading", false)
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
        val signData = intent.getParcelableExtra<SignInData.User>("signData") as SignInData.User
        MainGlobals.signInData = signData
        // null check
        mainViewModel.setSignData(signData)

        // 본전공이 default 선택
        mainViewModel.setSelectedMajor(MajorSelectData(signData.firstMajorId, signData.secondMajorName))
        mainViewModel.setFirstMajor(MajorSelectData(signData.firstMajorId, signData.firstMajorName))
        mainViewModel.setSecondMajor(MajorSelectData(signData.secondMajorId, signData.secondMajorName))
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
            if (signData != null)
                mainViewModel.setSelectedMajor(MajorSelectData(signData.firstMajorId, signData.firstMajorName))

        }
    }

    //마이페이지 프래그먼트 전환
    private fun myPageFragmentChange() {
        mainViewModel.mypageFragmentNum.observe(this) {
            when(it) {
                1 -> changeFragment(R.id.fragment_container_main, MyPageSettingFragment(), "myPageSetting")
                2 -> changeFragment(R.id.fragment_container_main, AppInfoFragment(), "myPageAppInfo")
                3 -> changeFragment(R.id.fragment_container_main, MyPageBlockFragment(), "myPageBlock")
            }
        }
    }
}