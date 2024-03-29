package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView.OnEditorActionListener
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityModifyMyInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.mypage.MyPageModifyItem
import com.nadosunbae_android.domain.model.sign.NicknameDuplicationData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.regex.Pattern

@AndroidEntryPoint
class ModifyMyInfoActivity :
    BaseActivity<ActivityModifyMyInfoBinding>(R.layout.activity_modify_my_info) {

    private val myPageViewModel: MyPageViewModel by viewModels()
    private val signViewModel: SignViewModel by viewModels()
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    private lateinit var secondMajorBottomSheetDialog: CustomBottomSheetDialog
    private val firstDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("본전공 진입시기")
    private val secondDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("제2전공 진입시기")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomSheetDialog()
        clickMajor()
        completeMajor()
        observeLoadingEnd()
        initNotEntered()
        initWriteMode()
        firstMajorPeriod()
        initSecondBottomSheetDialog()
        clickSecondMajor()
        completeSecondMajor()
        clickSecondMajorFavorites()
        secondMajorPeriod()
        pressSwitchEvent()
        initFocus()
        isNickNamePattern()
        nicknameTextWatcher()
        backBtnClick()
        observeModifyResult()
        observeEditFinish()
        introductionTextWatcher()
        modifyImgListener()
        clickMajorFavorites()
    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }


    override fun onResume() {
        super.onResume()
        initWriteMode()
    }

    //기존 데이터 불러오기
    private fun initWriteMode() {
        binding.textMyPageSave.setBackgroundResource(R.drawable.rectangle_fill_gray_0_8)
        binding.textMyPageSave.setTextColor(Color.parseColor("#94959E"))
        mainViewModel.signData.observe(this) {
            myPageViewModel.getPersonalInfo(it.userId)
        }
        myPageViewModel.getPersonalInfo(intent.getIntExtra("id", 0))
        myPageViewModel.personalInfo.observe(this) {
            binding.myPageInfo = it
            myPageViewModel.selectImgId.value = it.profileImageId
            signViewModel.firstMajor.value = it.firstMajorName
            signViewModel.secondMajor.value = it.secondMajorName
            binding.layoutCommunityWriteMajor.bottomSheetMajor = it.firstMajorName
            binding.layoutModifyProfileSecondMajor.bottomSheetMajor = it.secondMajorName

            binding.apply {
                if (it.secondMajorName == "미진입") {
                    binding.textMyPageMajorinfoDoubleMajorTime.setText("미진입")
                    textMyPageMajorinfoDoubleMajorMintTime.isEnabled = false
                    textMyPageMajorinfoDoubleMajorTime.text = "미진입"
                    textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
                    textMyPageMajorinfoDoubleMajorMintTime.setText("선택")
                    textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#C0C0CB"))
                } else {
                    textMyPageMajorinfoDoubleMajorMintTime.isEnabled = true
                    textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                    textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#00C8B0"))
                }
            }
        }
    }


    //초기 데이터 제 2전공 미진입인지 체크
    private fun initNotEntered() = with(binding) {
        if (layoutModifyProfileSecondMajor.toString() == "미진입") {
            textMyPageMajorinfoDoubleMajorMintTime.isEnabled = false
            textMyPageMajorinfoDoubleMajorTime.text = "미진입"
            textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
            textMyPageMajorinfoDoubleMajorMintTime.setText("선택")
            textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#C0C0CB"))
        } else {
            textMyPageMajorinfoDoubleMajorMintTime.isEnabled = true
            textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
            textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#00C8B0"))
        }
    }


    //학과 변경 세팅 - 제 1 전공
    private fun initBottomSheetDialog() {
        majorBottomSheetDialog = CustomBottomSheetDialog(getString(R.string.signup_first_major))
        myPageViewModel.majorList.observe(this) {
            observeBottomSheet(
                it ?: emptyList(),
                majorBottomSheetDialog,
            )
        }
    }


    //학과 변경 클릭 - 제 1전공
    private fun clickMajor() {
        val showDialog = {
            majorBottomSheetDialog.show(supportFragmentManager, majorBottomSheetDialog.tag)
        }
        binding.layoutCommunityWriteMajor.root.setOnClickListener {
            myPageViewModel.getMajorList(
                MainGlobals.signInData?.universityId ?: 1, "firstMajor", null,
                MainGlobals.signInData?.userId ?: 0
            )
            showDialog()
        }
    }

    //학과 변경 완료
    private fun completeMajor() {
        majorBottomSheetDialog.setCompleteListener {
            myPageViewModel.setFilter(majorBottomSheetDialog.getSelectedData())
        }

        myPageViewModel.firstFilter.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.id == 0) {
                    it.id = myPageViewModel.majorList.value?.get(0)?.majorId ?: 0
                }
                binding.layoutCommunityWriteMajor.bottomSheetMajor = it.name

                if (it.name != myPageViewModel.personalInfo.value?.firstMajorName) {
                    initActiveSaveBtn()
                }
            }
            .launchIn(lifecycleScope)
    }

    //즐겨찾기 클릭시
    private fun clickMajorFavorites() {
        majorBottomSheetDialog.setCompleteFavoritesListener {
            myPageViewModel.postCommunityFavorite(it)
        }
        myPageViewModel.myPageFavorites.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.success) {
                    myPageViewModel.getMajorList(
                        MainGlobals.signInData?.universityId ?: 1
                        , "firstMajor", null,
                        MainGlobals.signInData?.userId ?: 0
                    )
                }
            }
            .launchIn(lifecycleScope)

    }

    //제 1전공 진입시기 선택 바텀시트
    private fun firstMajorPeriod() {
        // local data
        var firstMajorSelectionPeriodData = mainViewModel.majorTime
        firstDepartmentPeriodBottomSheetDialog.setDataList(firstMajorSelectionPeriodData)

        binding.clMyPageMajorInfoMajorTime.setOnClickListener {
            firstDepartmentPeriodBottomSheetDialog.show(
                supportFragmentManager,
                firstDepartmentPeriodBottomSheetDialog.tag
            )

            firstDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val firstMajorPeriod = firstDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.firstMajorPeriod.value = firstMajorPeriod?.name
                initActiveSaveBtn()
            }
            signViewModel.firstMajorPeriod.observe(this) {
                binding.textMyPageMajorinfoMajorTime.setText(it)
            }
        }
    }

    //학과 변경 세팅 - 제 2 전공
    private fun initSecondBottomSheetDialog() {
        secondMajorBottomSheetDialog =
            CustomBottomSheetDialog(getString(R.string.signup_second_major))
        myPageViewModel.secondMajorList.observe(this) {
            observeBottomSheet(it ?: emptyList(), secondMajorBottomSheetDialog)
        }
    }


    //학과 변경 클릭 - 제 2전공
    private fun clickSecondMajor() {
        val showDialog = {
            secondMajorBottomSheetDialog.show(
                supportFragmentManager,
                secondMajorBottomSheetDialog.tag
            )
        }
        binding.layoutModifyProfileSecondMajor.root.setOnClickListener {
            myPageViewModel.getMajorList(
                MainGlobals.signInData?.universityId ?: 1, "secondMajor", null,
                MainGlobals.signInData?.userId ?: 0
            )
            showDialog()
        }
    }

    //학과 변경 완료 - 제 2전공
    private fun completeSecondMajor() {
        secondMajorBottomSheetDialog.setCompleteListener {
            myPageViewModel.setSecondFilter(secondMajorBottomSheetDialog.getSelectedData())
        }
        myPageViewModel.secondFilter.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.id == 0) {
                    it.id = myPageViewModel.secondMajorList.value?.get(0)?.majorId ?: 0
                }
                binding.layoutModifyProfileSecondMajor.bottomSheetMajor = it.name

                if (it.name == "미진입") {
                    binding.textMyPageMajorinfoDoubleMajorTime.setText("미진입")
                    binding.textMyPageMajorinfoDoubleMajorMintTime.isEnabled = false
                    binding.textMyPageMajorinfoDoubleMajorTime.text = "미진입"
                    binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
                    binding.textMyPageMajorinfoDoubleMajorMintTime.setText("선택")
                    binding.textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#C0C0CB"))
                } else {
                    binding.textMyPageMajorinfoDoubleMajorMintTime.isEnabled = true
                    binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                    binding.textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#00C8B0"))
                }

                if (it.name != myPageViewModel.personalInfo.value?.secondMajorName) {
                    initActiveSaveBtn()
                }

                if(it.name != "미진입" && binding.textMyPageMajorinfoDoubleMajorTime.text.toString() == "미진입") {
                    saveBtnInActive()
                }
            }
            .launchIn(lifecycleScope)
    }

    //즐겨찾기 클릭시 - 제 2전공
    private fun clickSecondMajorFavorites() {
        secondMajorBottomSheetDialog.setCompleteFavoritesListener {
            myPageViewModel.postCommunityFavorite(it)
        }
        myPageViewModel.myPageFavorites.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.success) {
                    myPageViewModel.getMajorList(
                        MainGlobals.signInData?.universityId ?: 1, "secondMajor", null,
                        MainGlobals.signInData?.userId ?: 0
                    )
                }
            }
            .launchIn(lifecycleScope)
    }

    //제 2전공 진입시기 바텀시트
    private fun secondMajorPeriod() {
        // local data
        var secondMajorSelectionPeriodData = mainViewModel.majorTime
        secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodData)

        binding.clMyPageMajorInfoDoubleMajorTime.setOnClickListener {
            secondDepartmentPeriodBottomSheetDialog.show(
                supportFragmentManager,
                secondDepartmentPeriodBottomSheetDialog.tag
            )

            secondDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val secondMajorPeriod = secondDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.secondMajorPeriod.value = secondMajorPeriod?.name
                initActiveSaveBtn()
                //initBtnActive()
            }
            signViewModel.secondMajorPeriod.observe(this) {
                binding.textMyPageMajorinfoDoubleMajorTime.setText(it)
                binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#00C8B0"))

            }
        }
    }


    //질문 스위치 클릭
    private fun pressSwitchEvent() {
        binding.imgMyPageModifySwitch.setOnClickListener {
            binding.imgMyPageModifySwitch.isSelected = !binding.imgMyPageModifySwitch.isSelected
            initActiveSaveBtn()
        }
    }


    // 화면 포커스 설정
    private fun initFocus() {
        //버튼 누르면 editText 활성화
        binding.textMyPageNicknameChange.setOnClickListener {
            binding.etMyPageNickname.isEnabled = true
            binding.etMyPageNickname.requestFocus()
            binding.etMyPageNickname.setFocusableInTouchMode(true)
            binding.etMyPageNickname.setFocusable(true)

            //키보드 나오게
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etMyPageNickname, InputMethodManager.SHOW_IMPLICIT)
            binding.textMyPageNicknameChange.isVisible = false
        }

        // scrollView 안의 constraintlayout 클릭 시 editText의 포커스 뺏어오고 해당 레이아웃에 focus 요청
        binding.clMyPageScrollviewInner.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                binding.svMypageModify.requestDisallowInterceptTouchEvent(false)
                binding.clMyPageScrollviewInner.setOnClickListener {
                    binding.etMyPageNickname.setFocusableInTouchMode(false)
                    binding.etMyPageNickname.setFocusable(false)
                    binding.etMyPageNickname.clearFocus()
                    binding.clMyPageScrollviewInner.requestFocus()

                    //키보드 내리기
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.etMyPageNickname.windowToken, 0)

                    binding.textMyPageNicknameChange.isVisible = true
                    if (binding.etMyPageNickname.text.toString() != myPageViewModel.personalInfo.value?.nickname) {
                        signUpBasicInfoViewModel.nickNameDuplication(NicknameDuplicationData(binding.etMyPageNickname.text.toString()))
                    } else {
                        binding.textMyPageModifyNicknameDuplicaitionOk.visibility = View.INVISIBLE
                        binding.textMyPageModifyNicknameDuplicaitionNo.visibility = View.INVISIBLE
                    }
                }
                return false
            }

        })

        //키보드의 완료 버튼 눌렀을 때 editText의 focus 뺏기
        binding.etMyPageNickname.setOnEditorActionListener(OnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                binding.etMyPageNickname.setFocusableInTouchMode(false)
                binding.etMyPageNickname.setFocusable(false)
                binding.textMyPageNicknameChange.isVisible = true
                signUpBasicInfoViewModel.nickNameDuplication(NicknameDuplicationData(binding.etMyPageNickname.text.toString()))
            }
            false
        })
    }


    //닉네임 정규식
    private fun isNickNamePattern() {
        if (!Pattern.matches(
                "^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9|]{2,8}\$",
                binding.etMyPageNickname.text.toString()
            )
        ) {
            binding.textMyPageNicknameTitle.isSelected = true
            signUpBasicInfoViewModel.nicknameDuplicationCheck.observe(this) {
                binding.textMyPageModifyNicknameDuplicaitionNo.visibility = View.INVISIBLE
                binding.textMyPageModifyNicknameDuplicaitionOk.visibility = View.INVISIBLE
            }
        } else {
            binding.textMyPageNicknameTitle.isSelected = false
        }
    }


    //닉네임
    private fun nicknameTextWatcher() = with(binding) {
        etMyPageNickname.addTextChangedListener {
            if (!textMyPageNicknameTitle.isSelected) {
                nicknameDuplication()
            }
            //닉네임 textfield 빈칸인지 체크
            if (etMyPageNickname.text.toString() == "") {
                textMyPageModifyNicknameDuplicaitionNo.visibility = View.INVISIBLE
                textMyPageModifyNicknameDuplicaitionOk.visibility = View.INVISIBLE
            } else {
                isNickNamePattern()
            }

            val nickname = signUpBasicInfoViewModel.nickName.value

            //닉네임 textfield 한글자라도 바뀐다면 하단 텍스트 사라지게
            if (nickname != etMyPageNickname.text.toString()) {
                textMyPageModifyNicknameDuplicaitionNo.visibility = View.INVISIBLE
                textMyPageModifyNicknameDuplicaitionOk.visibility = View.INVISIBLE
            }
            saveBtnInActive()
        }
    }

    //닉네임 중복 체크 서버 통신
    private fun nicknameDuplication() {
        Timber.d("NicknameDuplication : 서버 통신 성공")
        signUpBasicInfoViewModel.nicknameDuplicationCheck.observe(this) {
            if (!it.success) {
                Timber.d("닉네임 중복확인: 실패")
                binding.textMyPageModifyNicknameDuplicaitionOk.isVisible = false
                binding.textMyPageModifyNicknameDuplicaitionNo.isVisible = true
                binding.textMyPageNicknameTitle.isSelected = false
            } else if (it.success) {
                Timber.d("닉네임 중복확인: 성공")
                binding.textMyPageModifyNicknameDuplicaitionNo.isVisible = false
                binding.textMyPageModifyNicknameDuplicaitionOk.isVisible = true
                initActiveSaveBtn()
            }
        }
        if (binding.etMyPageNickname.text.toString() == "") {
            binding.textMyPageModifyNicknameDuplicaitionOk.isVisible = false
            binding.textMyPageModifyNicknameDuplicaitionNo.isVisible = false
        }
    }

    //2전공 미진입 분기처리
    private fun saveBtn() {
        if (binding.layoutModifyProfileSecondMajor.toString() != "미진입") {
            binding.textMyPageSave.isSelected =
                binding.textMyPageMajorinfoDoubleMajorTime.text.toString() != "선택하기"

        }
    }


    // 저장 버튼 활성화
    private fun initActiveSaveBtn() {
        if (!binding.textMyPageModifyNicknameDuplicaitionNo.isVisible && !binding.textMyPageNicknameTitle.isSelected && binding.layoutCommunityWriteMajor.bottomSheetMajor != binding.layoutModifyProfileSecondMajor.bottomSheetMajor) {
            binding.textMyPageSave.isSelected = true
            binding.textMyPageSave.setBackgroundResource(R.drawable.rectangle_fill_main_black_8)
            binding.textMyPageSave.setTextColor(Color.parseColor("#DFF6F4"))

            if (binding.textMyPageSave.isSelected) {
                binding.textMyPageSave.setOnClickListener {
                    confirmExit()
                }
            } else {
                binding.textMyPageSave.isClickable = false
            }
        } else {
            saveBtnInActive()
        }
    }


    //뒤로가기 버튼 클릭 리스너
    private fun backBtnClick() {
        binding.imgMypageModifyTitle.setOnClickListener {
            if (binding.textMyPageSave.isSelected) {
                confirmBack()
            } else {
                finish()
            }
        }
    }

    // 회원정보 수정 put 서버통신
    private fun completeModifyInfo() {
        with(binding) {
            val requestBody = MyPageModifyItem(
                myPageViewModel.selectImgId.value ?: 1,
                etMyPageNickname.text.toString(),
                etMyPageIntroduction.text.toString(),
                (
                        if (majorBottomSheetDialog.getSelectedData().id != -1) {
                            majorBottomSheetDialog.getSelectedData().id
                        } else {
                            MainGlobals.signInData?.firstMajorId ?: 1
                        }),
                textMyPageMajorinfoMajorTime.text.toString(),
                (
                        if (secondMajorBottomSheetDialog.getSelectedData().id != -1) {
                            secondMajorBottomSheetDialog.getSelectedData().id
                        } else {
                            MainGlobals.signInData?.secondMajorId ?: 1
                        }
                        ),
                textMyPageMajorinfoDoubleMajorTime.text.toString(),
                binding.imgMyPageModifySwitch.isSelected
            )
            myPageViewModel.putMyPageModify(requestBody)
            finish()
        }
    }


    //저장버튼 알럿
    private fun confirmExit(): MutableLiveData<Boolean> {
        val confirm = MutableLiveData<Boolean>()
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                getString(R.string.mypage_alert_modify_title),
                getString(R.string.mypage_alert_modify_save),
                getString(R.string.mypage_alert_modify_no)
            ),
            complete = {
                showLoading()
                completeModifyInfo()
                setProfileModifyGA()
            },
            cancel = {

            }
        )
        return confirm
    }
    //프로필 수정 GA
    private fun setProfileModifyGA(){
        if(myPageViewModel.personalInfo.value?.bio.toString() != binding.etMyPageIntroduction.toString()){
            myPageViewModel.profileGA.add("oneline_introduce")
        }
        if(myPageViewModel.personalInfo.value?.profileImageId != myPageViewModel.selectImgId.value){
            myPageViewModel.profileGA.add("profile_image")
        }
        if(binding.imgMyPageModifyMain.isSelected){
            myPageViewModel.profileGA.add("question_allow_on")
        }else{
            myPageViewModel.profileGA.add("question_allow_off")
        }

        FirebaseAnalyticsUtil.firebaseLogs("profile_change","type",myPageViewModel.profileGA)
        myPageViewModel.profileGA.clear()
    }


    //뒤로가기 버튼 알럿
    private fun confirmBack(): MutableLiveData<Boolean> {
        val confirm = MutableLiveData<Boolean>()
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                getString(R.string.mypage_modify_alert_back_title),
                getString(R.string.mypage_modify_alert_back_exit),
                getString(R.string.mypage_modify_alert_back_continue)
            ),
            complete = {
                finish()
            },
            cancel = {

            }
        )
        return confirm
    }

    //이거 아직 필요한가 ?
    // 수정 완료 시 학과 정보 저장
    private fun observeModifyResult() {
        myPageViewModel.modifyInfo.observe(this) {
            myPageViewModel.getMajorName(isFirstMajor = true, it.data.firstMajorId)
        }
        myPageViewModel.firstMajorName.observe(this) {
            myPageViewModel.getMajorName(
                isFirstMajor = false,
                myPageViewModel.modifyInfo.value?.data?.secondMajorId ?: 1
            )
            storeMajorData()
        }
        myPageViewModel.secondMajorName.observe(this) {
            storeMajorData()
            myPageViewModel.editFinish()
        }
    }

    private fun storeMajorData() {
        ReviewGlobals.firstMajor?.majorId =
            myPageViewModel.modifyInfo.value?.data?.firstMajorId ?: 1
        ReviewGlobals.firstMajor?.majorName = myPageViewModel.firstMajorName.value ?: ""
        ReviewGlobals.secondMajor?.majorId =
            myPageViewModel.modifyInfo.value?.data?.secondMajorId ?: 1
        ReviewGlobals.secondMajor?.majorName = myPageViewModel.secondMajorName.value ?: ""
    }

    // 모든 과정 끝나고 finish 하도록
    private fun observeEditFinish() {
        myPageViewModel.editFinish.observe(this) {
            if (it == true) {
                dismissLoading()
                finish()
            }
        }
    }

    private fun introductionTextWatcher() {
        binding.etMyPageIntroduction.addTextChangedListener {
            binding.textMyPageModifyLength.setText(binding.etMyPageIntroduction.text.length.toString())
            if(myPageViewModel.personalInfo.value?.bio.toString() != binding.etMyPageIntroduction.text.toString()) {
                initActiveSaveBtn()
            } else {
                saveBtnInActive()
            }
        }
    }

    private fun saveBtnInActive() {
        binding.textMyPageSave.isSelected = false
        binding.textMyPageSave.setBackgroundResource(R.drawable.rectangle_fill_gray_0_8)
        binding.textMyPageSave.setTextColor(Color.parseColor("#94959E"))
        binding.textMyPageSave.isClickable = false
    }

    //프로필 이미지 수정 누르면 바텀시트 올라옴
    private fun modifyImgListener() {
        val dialog = ModifyImgBottomSheetFragment()
        binding.textMyPageModifyImg.setOnClickListener {
            dialog.show(
                supportFragmentManager,
                dialog.tag
            )
        }
        dialog.setCallbackButtonClickListener {
            applyModifyImg()
        }
    }

    //바텀시트에서 수정한 이미지 적용
    private fun applyModifyImg() {
        when (myPageViewModel.selectImgId.value) {
            1 -> imageSelect(binding.imgMyPageModifyMain, R.drawable.c_72)
            2 -> imageSelect(binding.imgMyPageModifyMain, R.drawable.e_72)
            3 -> imageSelect(binding.imgMyPageModifyMain, R.drawable.b_72)
            4 -> imageSelect(binding.imgMyPageModifyMain, R.drawable.d_72)
            5 -> imageSelect(binding.imgMyPageModifyMain, R.drawable.a_72)
        }
        if (myPageViewModel.selectImgId.value != myPageViewModel.personalInfo.value?.profileImageId) {
            initActiveSaveBtn()
        }
    }

    fun imageSelect(imageView: ImageView, image: Int) {
        Glide.with(imageView.context)
            .load(image)
            .override(72.dpToPx, 72.dpToPx)
            .into(imageView)
    }
}
