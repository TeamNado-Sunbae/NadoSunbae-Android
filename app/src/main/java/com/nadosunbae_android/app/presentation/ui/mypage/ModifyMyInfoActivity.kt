package com.nadosunbae_android.app.presentation.ui.mypage

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityModifyMyInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.mypage.MyPageModifyData
import org.koin.androidx.viewmodel.ext.android.viewModel


class ModifyMyInfoActivity :
    BaseActivity<ActivityModifyMyInfoBinding>(R.layout.activity_modify_my_info) {

    private lateinit var modifyData: MyPageModifyData

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

        initNotEntered()
        initWriteMode()
        firstMajor()
        firstMajorPeriod()
        secondMajor()
        secondMajorPeriod()
        pressSwitchEvent()
        nicknameChange()

    }


    //기존 데이터 불러오기
    private fun initWriteMode() = with(binding) {
        etMyPageNickname.setText(intent.getStringExtra("nickname"))
        textMyPageMajorinfoMajor.setText(intent.getStringExtra("firstMajor"))
        textMyPageMajorinfoMajorTime.setText(intent.getStringExtra("firstMajorStart"))
        textMyPageMajorinfoDoubleMajor.setText(intent.getStringExtra("secondMajor"))
        textMyPageMajorinfoDoubleMajorTime.setText(intent.getStringExtra("secondMajorStart"))
    }

    //초기 데이터 제 2전공 미진입인지 체크
    private fun initNotEntered() = with(binding) {
        if(intent.getStringExtra("secondMajor") == "미진입") {
            binding.textMyPageMajorinfoDoubleMajorMintTime.isEnabled = false
            binding.textMyPageMajorinfoDoubleMajorTime.text = "선택하기"
            binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
            binding.textMyPageMajorinfoDoubleMajorMintTime.setText("선택")
            binding.textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#C0C0CB"))
        } else {
            binding.textMyPageMajorinfoDoubleMajorMintTime.isEnabled = true
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

            if (signViewModel.secondMajor.value.toString() == "미진입") {
                binding.textMyPageMajorinfoDoubleMajorMintTime.isClickable = false
                binding.textMyPageMajorinfoDoubleMajorTime.text = "선택하기"
                binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
                binding.textMyPageMajorinfoDoubleMajorMintTime.setText("선택")
                binding.textMyPageMajorinfoDoubleMajorMintTime.setTextColor(Color.parseColor("#C0C0CB"))
            } else {
                binding.textMyPageMajorinfoDoubleMajorMintTime.isClickable = true
            }
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

    // 닉네임 변경
    // 변경 버튼 누르면 textfield 활성화
    private fun nicknameChange() {
        binding.textMyPageNicknameChange.setOnClickListener {
            binding.etMyPageNickname.isEnabled = true
            binding.etMyPageNickname.requestFocus()
            binding.etMyPageNickname.setFocusableInTouchMode(true)
            binding.etMyPageNickname.setFocusable(true)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etMyPageNickname, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.clMyPageScrollviewInner.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                binding.svMypageModify.requestDisallowInterceptTouchEvent(false)
                binding.clMyPageScrollviewInner.setOnClickListener {
                    binding.etMyPageNickname.setFocusableInTouchMode(false)
                    binding.etMyPageNickname.setFocusable(false)
                    binding.etMyPageNickname.clearFocus()
                    binding.clMyPageScrollviewInner.requestFocus()
                }
                return false
            }
        })

        binding.etMyPageNickname.setOnEditorActionListener(OnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                binding.etMyPageNickname.setFocusableInTouchMode(false)
                binding.etMyPageNickname.setFocusable(false)
            }
            false
        })
    }

    // 저장 버튼 활성화
    private fun initActiveSaveBtn() {
        binding.textMyPageSave.isSelected = true
        if(binding.textMyPageSave.isSelected) {
            binding.textMyPageSave.setOnClickListener {
                confirmExit()
            }
        }
    }

    //서버통신


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
                confirm.value = true
            },
            cancel = {
                confirm.value = false
            }
        )
        return confirm
    }


}
