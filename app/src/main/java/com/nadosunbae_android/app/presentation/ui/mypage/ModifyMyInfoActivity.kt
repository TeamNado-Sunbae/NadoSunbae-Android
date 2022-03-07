package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityModifyMyInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.mypage.MyPageModifyItem
import com.nadosunbae_android.domain.model.sign.NicknameDuplicationData
import com.nadosunbae_android.domain.model.sign.SignInData
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class ModifyMyInfoActivity :
    BaseActivity<ActivityModifyMyInfoBinding>(R.layout.activity_modify_my_info) {

    private val myPageViewModel: MyPageViewModel by viewModel()
    private val signViewModel: SignViewModel by viewModel()
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

    private val firstDepartmentBottomSheetDialog = CustomBottomSheetDialog("본전공")
    private val firstDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("본전공 진입시기")
    private val secondDepartmentBottomSheetDialog = CustomBottomSheetDialog("제2전공")
    private val secondDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("제2전공 진입시기")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLoadingEnd()
        initNotEntered()
        initWriteMode()
        firstMajor()
        firstMajorPeriod()
        secondMajor()
        secondMajorPeriod()
        pressSwitchEvent()
        initFocus()
        isNickNamePattern()
        nicknameTextWatcher()
        backBtnClick()

    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }


    //기존 데이터 불러오기
    private fun initWriteMode() {
        mainViewModel.signData.observe(this) {
            myPageViewModel.getPersonalInfo(it.userId)
        }

        myPageViewModel.getPersonalInfo(intent.getIntExtra("id", 0))
        myPageViewModel.personalInfo.observe(this) {
            binding.myPageInfo = it
            Log.d("서버통신", "성공")

            if (it.data.secondMajorName == "미진입")
                binding.textMyPageMajorinfoDoubleMajorTime.setText("미진입")
        }

        initNotEntered()

    }


    //초기 데이터 제 2전공 미진입인지 체크
    private fun initNotEntered() = with(binding) {
        if (textMyPageMajorinfoDoubleMajor.text.toString() == "미진입") {
            textMyPageMajorinfoDoubleMajorMintTime.isEnabled = false
            textMyPageMajorinfoDoubleMajorTime.text = "선택하기"
            textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
            textMyPageMajorinfoDoubleMajorMintTime.setText("선택")
            textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#C0C0CB"))
        } else {
            textMyPageMajorinfoDoubleMajorMintTime.isEnabled = true
            textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
            textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#00C8B0"))
        }
    }


    //제 1전공 학과 선택 바텀시트
    private fun firstMajor() {

        binding.textMyPageMajorinfoMajorMint.setOnClickListener {
            firstDepartmentBottomSheetDialog.show(
                supportFragmentManager,
                firstDepartmentBottomSheetDialog.tag
            )

        }

        signUpBasicInfoViewModel.getFirstDepartment(1, "firstMajor")

        signUpBasicInfoViewModel.firstDepartment.observe(this) {
            firstDepartmentBottomSheetDialog.setDataList(it.data.filter { it.isFirstMajor }
                .map { SelectableData(it.majorId, it.majorName, false) }.toMutableList())
        }

        //데이터 넣기
        firstDepartmentBottomSheetDialog.setCompleteListener {
            val firstMajor = firstDepartmentBottomSheetDialog.getSelectedData()
            signViewModel.firstMajor.value = firstMajor?.name
        }

        signViewModel.firstMajor
            .observe(this) {
                binding.textMyPageMajorinfoMajor.setText(it)
                binding.textMyPageMajorinfoMajor.text = it
                binding.textMyPageMajorinfoMajorMint.text = "변경"
                initActiveSaveBtn()
            }
    }

    //제 1전공 진입시기 선택 바텀시트
    private fun firstMajorPeriod() {
        // local data
        var firstMajorSelectionPeriodData = mutableListOf(
            SelectableData(1, "22-1", false),
            SelectableData(2, "21-2", false),
            SelectableData(3, "21-1", false),
            SelectableData(4, "20-2", false),
            SelectableData(5, "20-1", false),
            SelectableData(6, "19-2", false),
            SelectableData(7, "19-1", false),
            SelectableData(8, "18-2", false),
            SelectableData(9, "18-1", false),
            SelectableData(10, "17-2", false),
            SelectableData(11, "17-1", false),
            SelectableData(12, "16-2", false),
            SelectableData(13, "16-1", false),
            SelectableData(14, "15-2", false),
            SelectableData(15, "15-1", false),
            SelectableData(16, "15년 이전", false)
        )
        firstDepartmentPeriodBottomSheetDialog.setDataList(firstMajorSelectionPeriodData)

        binding.textMyPageMajorinfoMajorTimeMint.setOnClickListener {
            firstDepartmentPeriodBottomSheetDialog.show(
                supportFragmentManager,
                firstDepartmentPeriodBottomSheetDialog.tag
            )

            firstDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val firstMajorPeriod = firstDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.firstMajorPeriod.value = firstMajorPeriod?.name
            }
            signViewModel.firstMajorPeriod.observe(this) {
                binding.textMyPageMajorinfoMajorTime.setText(it)
                binding.textMyPageMajorinfoMajorTimeMint.text = "변경"
                initActiveSaveBtn()
            }
        }
    }


    //제 2전공 학과 선택 바텀시트
    private fun secondMajor() {
        binding.textMyPageMajorinfoDoubleMajorMint.setOnClickListener {
            secondDepartmentBottomSheetDialog.show(
                supportFragmentManager,
                secondDepartmentBottomSheetDialog.tag
            )

            signUpBasicInfoViewModel.getSecondDepartment(1, "secondMajor")
        }

        signUpBasicInfoViewModel.secondDepartment.observe(this) {
            secondDepartmentBottomSheetDialog.setDataList(it.data.filter { it.isSecondMajor }
                .map { SelectableData(it.majorId, it.majorName, false) }.toMutableList())
        }

        secondDepartmentBottomSheetDialog.setCompleteListener {
            val secondMajor = secondDepartmentBottomSheetDialog.getSelectedData()
            signViewModel.secondMajor.value = secondMajor?.name
            signUpBasicInfoViewModel.secondDepartmentClick.value = true
            initNotEntered()
        }

        signViewModel.secondMajor
            .observe(this) {
                binding.textMyPageMajorinfoDoubleMajor.setText(it)
                binding.textMyPageMajorinfoDoubleMajor.text = it
                binding.textMyPageMajorinfoDoubleMajor.setTextColor(Color.parseColor("#001D19"))
                binding.textMyPageMajorinfoDoubleMajorMint.text = "변경"
                initActiveSaveBtn()
            }
    }


    //제 2전공 진입시기 바텀시트
    private fun secondMajorPeriod() {
        // local data
        var secondMajorSelectionPeriodData = mutableListOf(
            SelectableData(1, "22-1", false),
            SelectableData(2, "21-2", false),
            SelectableData(3, "21-1", false),
            SelectableData(4, "20-2", false),
            SelectableData(5, "20-1", false),
            SelectableData(6, "19-2", false),
            SelectableData(7, "19-1", false),
            SelectableData(8, "18-2", false),
            SelectableData(9, "18-1", false),
            SelectableData(10, "17-2", false),
            SelectableData(11, "17-1", false),
            SelectableData(12, "16-2", false),
            SelectableData(13, "16-1", false),
            SelectableData(14, "15-2", false),
            SelectableData(15, "15-1", false),
            SelectableData(16, "15년 이전", false)
        )
        secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodData)

        binding.textMyPageMajorinfoDoubleMajorMintTime.setOnClickListener {
            secondDepartmentPeriodBottomSheetDialog.show(
                supportFragmentManager,
                secondDepartmentPeriodBottomSheetDialog.tag
            )

            secondDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val secondMajorPeriod = secondDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.secondMajorPeriod.value = secondMajorPeriod?.name
            }
            signViewModel.secondMajorPeriod.observe(this) {
                binding.textMyPageMajorinfoDoubleMajorTime.setText(it)
                binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#00C8B0"))
                binding.textMyPageMajorinfoDoubleMajorMintTime.text = "변경"
                initActiveSaveBtn()
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
            //signUpBasicInfoViewModel.nickNameDuplication(NicknameDuplicationData(binding.etMyPageNickname.text.toString()))
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
                    if (binding.etMyPageNickname.text.toString() != myPageViewModel.personalInfo.value?.data?.nickname) {
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
    private fun isNickNamePattern() = with(binding) {
        val nickname = etMyPageNickname

        textMyPageNicknameTitle.isSelected =
            !Pattern.matches("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9|]{2,8}\$", nickname.text.toString())

    }


    //닉네임 textwatcher
    private fun nicknameTextWatcher() = with(binding) {
        etMyPageNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                nicknameDuplication()

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
            }
        })
    }


    //닉네임 중복 체크 서버 통신
    private fun nicknameDuplication() {
        Log.d("NicknameDuplication", "서버 통신 성공")
        signUpBasicInfoViewModel.nicknameDuplicationCheck.observe(this) {
            if (!it.success) {
                Log.d("닉네임 중복확인", "실패")
                binding.textMyPageModifyNicknameDuplicaitionOk.isVisible = false
                binding.textMyPageModifyNicknameDuplicaitionNo.isVisible = true
            }
            if (it.success) {
                Log.d("닉네임 중복확인", "성공")
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

    // 저장 버튼 활성화
    private fun initActiveSaveBtn() {
        if (!binding.textMyPageModifyNicknameDuplicaitionNo.isVisible) {
            binding.textMyPageSave.isSelected = true
            if (binding.textMyPageSave.isSelected) {
                binding.textMyPageSave.setOnClickListener {
                    showLoading()
                    confirmExit()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        completeModifyInfo()
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
                etMyPageNickname.text.toString(),
                (
                        if (firstDepartmentBottomSheetDialog.getSelectedData()?.id == null) {
                            ReviewGlobals.firstMajor!!.majorId
                        } else {
                            firstDepartmentBottomSheetDialog.getSelectedData()?.id!!
                        }),

                textMyPageMajorinfoMajorTime.text.toString(),
                (
                        if (secondDepartmentBottomSheetDialog.getSelectedData()?.id == null) {
                            ReviewGlobals.secondMajor!!.majorId
                        } else {
                            secondDepartmentBottomSheetDialog.getSelectedData()?.id!!
                        }
                        ),

                textMyPageMajorinfoDoubleMajorTime.text.toString(),
                binding.imgMyPageModifySwitch.isSelected
            )
            myPageViewModel.putMyPageModify(requestBody)
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
                finish()
            },
            cancel = {

            }
        )
        return confirm
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

}
