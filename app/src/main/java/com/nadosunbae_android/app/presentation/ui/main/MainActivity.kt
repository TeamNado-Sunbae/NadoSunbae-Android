package com.nadosunbae_android.app.presentation.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMainBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.AskEveryoneFragment
import com.nadosunbae_android.app.presentation.ui.classroom.ClassRoomMainContentFragment
import com.nadosunbae_android.app.presentation.ui.classroom.SeniorFragment
import com.nadosunbae_android.app.presentation.ui.classroom.SeniorPersonalFragment
import com.nadosunbae_android.app.presentation.ui.classroom.review.ClassRoomReviewFragment
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.community.CommunityFragment
import com.nadosunbae_android.app.presentation.ui.home.HomeFrameFragment
import com.nadosunbae_android.app.presentation.ui.home.HomeRankingFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.AppInfoFragment
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageBlockFragment
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageFragment
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageSettingFragment
import com.nadosunbae_android.app.presentation.ui.notification.NotificationFragment
import com.nadosunbae_android.app.presentation.ui.sign.SignUpAgreementFragment
import com.nadosunbae_android.app.util.*
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.domain.model.sign.SignInData
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DateUtil.initTimeZone()

        initBottomNav()
        classRoomFragmentChange()
        setDefaultMajor()
        getSignDataFromIntent()
        classRoomBack()
        getMajorList()
        homeFragmentChange()
        // clickBottomNav()
        myPageFragmentChange()
        myPageBack()
        initClickProfile()
        trackActiveUser()
        floatIsReviewInappropriate()
        floatAppUpdateDialog()
        seniorDetailBack()

    }

    //앱 업데이트 알럿 띄우기
    private fun floatAppUpdateDialog() {
        if (intent.getBooleanExtra("updateCondition", false)) {
            CustomDialog(this).genericDialog(
                dialogText = CustomDialog.DialogData(
                    getString(R.string.app_update),
                    getString(R.string.update),
                    getString(R.string.nex_update)
                ),
                complete = {
                    val uri = Uri.parse(getString(R.string.google_app))
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                },
                cancel = {}
            )
        }

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

    //부적절 후기 일경우 띄우기
    private fun floatIsReviewInappropriate() {
        CustomDialog(this).restrictDialog(
            this,
            ReviewGlobals.isReviewed,
            MainGlobals.signInData?.isUserReported ?: false,
            MainGlobals.signInData?.isReviewInappropriate ?: false,
            MainGlobals.signInData?.message.toString(),
            true
        ) {}
    }

    //학과 리스트 가져오기
    private fun getMajorList() {
        mainViewModel.getMajorList(
            1, "all", null,
            MainGlobals.signInData?.userId ?: 0
        )

    }

    //바텀네비
    private fun initBottomNav() {

        //바텀 네비 아이템 클릭된 것 처럼 보이도록 ( 4-> 마이페이지, 2 -> 과방)
        // 첫 프래그먼트 설정 (닉네임 클릭시 마이페이지 및 선배 개인페이지를 위해)

        mainViewModel.bottomNavItem.observe(this) {
            when (it) {
                MYPAGE, MYPAGEDIVISION -> {
                    binding.btNvMain.selectedItemId = R.id.navigation_mypage
                    changeFragmentNoBackStack(
                        R.id.fragment_container_main,
                        MyPageFragment()
                    )
                }
                SENIORPERSONAL -> {
                    binding.btNvMain.selectedItemId = R.id.navigation_room
                    changeFragmentNoBackStack(
                        R.id.fragment_container_main,
                        SeniorPersonalFragment()
                    )
                }
                CLASSROOM -> {
                    binding.btNvMain.selectedItemId = R.id.navigation_room
                }
                CLASSROOM_NOBACK -> {
                    changeFragmentNoBackStack(
                        R.id.fragment_container_main,
                        ClassRoomMainContentFragment()
                    )
                }
                NOTIFICATION -> {
                    binding.btNvMain.selectedItemId = R.id.navigation_notice
                    changeFragmentNoBackStack(R.id.fragment_container_main, NotificationFragment())
                }
                COMMUNITY -> {
                    binding.btNvMain.selectedItemId = R.id.navigation_community
                    changeFragmentNoBackStack(
                        R.id.fragment_container_main,
                        CommunityFragment()
                    )
                }

                else -> {
                    changeFragmentNoBackStack(R.id.fragment_container_main, HomeFrameFragment())
                }
            }


            binding.btNvMain.itemIconTintList = null
            binding.btNvMain.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        changeFragmentNoBackStack(R.id.fragment_container_main, HomeFrameFragment())
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_room -> {
                        mainViewModel.classRoomNum.value = 1
                        changeFragmentNoBackStack(
                            R.id.fragment_container_main,
                            ClassRoomMainContentFragment()
                        )
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_community -> {
                        changeFragmentNoBackStack(R.id.fragment_container_main, CommunityFragment())
                    }
                    R.id.navigation_notice -> {
                        changeFragmentNoBackStack(
                            R.id.fragment_container_main,
                            NotificationFragment()
                        )
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_mypage -> {
                        changeFragmentNoBackStack(R.id.fragment_container_main, MyPageFragment())
                        return@setOnItemSelectedListener true
                    }
                }
                true
            }
        }
    }
    //계산

    //홈 프래그먼트 전환
    private fun homeFragmentChange() {
        mainViewModel.homeFragmentNum.observe(this) {
            when (it) {
                1 -> changeFragment(
                    R.id.fragment_container_main,
                    SeniorPersonalFragment(),
                    "seniorPersonal"
                )
            }
        }
    }

    //과방 프레그먼트 전환
    private fun classRoomFragmentChange() {
        mainViewModel.classRoomFragmentNum.observe(this, Observer {
            when (it) {
                2 -> changeFragment(
                    R.id.fragment_container_main,
                    AskEveryoneFragment(),
                    "askEveryOne"
                )

                1 -> changeFragmentNoBackStack(
                    R.id.fragment_container_main,
                    ClassRoomMainContentFragment()
                )

                3 -> changeFragment(R.id.fragment_container_main, SeniorFragment(), "senior")

                4 -> changeFragment(
                    R.id.fragment_container_main,
                    SeniorPersonalFragment(),
                    "seniorPersonal"
                )

                5 -> changeFragment(
                    R.id.fragment_container_main,
                    ClassRoomReviewFragment(),
                    "classRoomReview"
                )

                6 -> changeFragment(R.id.fragment_container_main, MyPageFragment(), "myPage")

                7 -> changeFragmentNoBackStack(R.id.fragment_container_main, SeniorFragment())
            }
        })
    }

    //프로필 및 닉네임 클릭시 전환되는 데이터 받아오는 부분
    private fun initClickProfile() {
        mainViewModel.bottomNavItem.value = intent.getIntExtra("bottomNavItem", -1)
        mainViewModel.seniorId.value = intent.getIntExtra("seniorId", -1)
        mainViewModel.initLoading.value = intent.getBooleanExtra("loading", false)
        mainViewModel.divisionBlock.value = intent.getIntExtra("blockDivision", -1)
    }


    //과방 뒤로가기 전환
    private fun classRoomBack() {
        mainViewModel.classRoomBackFragmentNum.observe(this) {
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
        ReviewGlobals.isReviewed = signData.isReviewed
        // null check
        mainViewModel.setSignData(signData)
        // 본전공이 default 선택
        mainViewModel.setSelectedMajor(
            MajorSelectData(
                signData.firstMajorId,
                signData.secondMajorName
            )
        )
        mainViewModel.setFirstMajor(MajorSelectData(signData.firstMajorId, signData.firstMajorName))
        mainViewModel.setSecondMajor(
            MajorSelectData(
                signData.secondMajorId,
                signData.secondMajorName
            )
        )

        mainViewModel.firstMajor.value?.majorId = signData.firstMajorId
        mainViewModel.secondMajor.value?.majorId = signData.secondMajorId
    }


    // 본전공이 선택되어 있도록
    private fun setDefaultMajor() {
        mainViewModel.signData.observe(this) {
            val signData = mainViewModel.signData.value
            // null check
            if (signData != null) {
                mainViewModel.setSelectedMajor(
                    MajorSelectData(
                        signData.firstMajorId,
                        signData.firstMajorName
                    )
                )

                // firebase analytics user property
                FirebaseAnalyticsUtil.setUserProperty(signData)
            }
        }
    }

    //마이페이지 프래그먼트 전환
    private fun myPageFragmentChange() {
        mainViewModel.mypageFragmentNum.observe(this) {
            when (it) {
                1 -> changeFragment(
                    R.id.fragment_container_main,
                    MyPageSettingFragment(),
                    "myPageSetting"
                )
                2 -> changeFragment(
                    R.id.fragment_container_main,
                    AppInfoFragment(),
                    "myPageAppInfo"
                )
                3 -> changeFragment(
                    R.id.fragment_container_main,
                    MyPageBlockFragment(),
                    "myPageBlock"
                )
                4 -> changeFragment(R.id.fragment_container_main, MyPageFragment(), "myPageMain")
            }
        }
    }

    //선배 상세보기 뒤로가기 전환
    private fun seniorDetailBack() {
        mainViewModel.seniorDetailNum.observe(this) {
            when (it) {
                //홈 단상대에서 진입
                1 -> changeFragment(
                    R.id.fragment_container_main,
                    HomeFrameFragment(),
                    "HomeMainFragment"
                )
                //홈 -> 랭킹으로 진입
                2 -> changeFragment(
                    R.id.fragment_container_main,
                    HomeRankingFragment(),
                    "HomeRankingFragment"
                )
                //과방 -> 일렬로 된 선배 리스트에서 진입 (ClassRoomQuestion)
                3 -> changeFragment(
                    R.id.fragment_container_main,
                    ClassRoomMainContentFragment(),
                    "ClassroomFragment"
                )
                //과방 -> 선배리스트에서 진입
                4 -> changeFragment(
                    R.id.fragment_container_main,
                    SeniorFragment(),
                    "SeniorFragment"
                )
                //커뮤니티 -> 커뮤니티 상세보기 -> 선배리스트에서 진입

            }
        }
    }


    //마이페이지 뒤로가기 전환
    private fun myPageBack() {
        mainViewModel.myPageFragmentNum.observe(this) {
            when (it) {
                1 -> popFragmentBackStack("myPageSetting")
                2 -> popFragmentBackStack("myPageAppInfo")
                3 -> popFragmentBackStack("myPageBlock")
                4 -> popFragmentBackStack("")
            }

        }

    }

    private fun trackActiveUser() {

        val now = Calendar.getInstance()

        if (!NadoSunBaeSharedPreference.getUserActive(
                this,
                now,
                ActiveUser.DAU
            )
        ) {   // dau 없을 때 -> 등록
            NadoSunBaeSharedPreference.setUserActive(this, now, ActiveUser.DAU)
            FirebaseAnalyticsUtil.dau()
        }

        if (!NadoSunBaeSharedPreference.getUserActive(
                this,
                now,
                ActiveUser.WAU
            )
        ) {   // wau 없을 때 -> 등록
            NadoSunBaeSharedPreference.setUserActive(this, now, ActiveUser.WAU)
            FirebaseAnalyticsUtil.wau()
        }

        if (!NadoSunBaeSharedPreference.getUserActive(
                this,
                now,
                ActiveUser.MAU
            )
        ) {     // mau 없을 때 -> 등록
            NadoSunBaeSharedPreference.setUserActive(this, now, ActiveUser.MAU)
            FirebaseAnalyticsUtil.mau()
        }
    }


    companion object {
        const val REVIEW = 1
        const val SENIORPERSONAL = 2
        const val CLASSROOM = 3
        const val MYPAGE = 4
        const val MYPAGEDIVISION = 5
        const val NOTIFICATION = 6
        const val CLASSROOM_NOBACK = 7
        const val COMMUNITY = 8
    }
}