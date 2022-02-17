package com.nadosunbae_android.app.presentation.ui.mypage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityModifyMyInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
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

    val firstDepartmentBottomSheetDialog = CustomBottomSheetDialog("본전공")
    val firstDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("본전공 진입시기")

    val secondDepartmentBottomSheetDialog = CustomBottomSheetDialog("제2전공")
    val secondDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("제2전공 진입시기")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

            if (signViewModel.secondMajor.value.toString() == "미진입") {
                binding.textMyPageMajorinfoDoubleMajorMintTime.isClickable = false
                binding.textMyPageMajorinfoDoubleMajorTime.text = "미진입"
                binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#94959E"))
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
            }
    }


    //제 2전공 진입시기 바텀시트
    private fun secondMajorPeriod() {
        // test data
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
                binding.textMyPageMajorinfoDoubleMajorTime.text = it
                binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textMyPageMajorinfoDoubleMajorMintTime.setText("변경")
            }

        }

        if (binding.textMyPageMajorinfoDoubleMajor.text != "미진입") {
            secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodData)
        }
    }

    //질문 스위치 클릭
    private fun pressSwitchEvent() {
        binding.imgMyPageModifySwitch.setOnClickListener {
            binding.imgMyPageModifySwitch.isSelected = !binding.imgMyPageModifySwitch.isSelected
        }
    }

    // 닉네임 변경
    // 변경 버튼 누르면 textfield 활성화
    private fun nicknameChange() {
        binding.textMyPageNicknameChange.setOnClickListener {
            binding.etMyPageNickname.isEnabled = true
            binding.etMyPageNickname.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etMyPageNickname, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.clMypageModifyMain.setOnClickListener {
            Log.d("포커스", "제발")
            binding.etMyPageNickname.setFocusableInTouchMode(false)
            binding.etMyPageNickname.setFocusable(false)

            binding.etMyPageNickname.clearFocus()
            binding.clMypageModifyMain.requestFocus()
            binding.imgMyPageModifySwitch.requestFocus()
        }

        binding.svMypageModify.setOnClickListener {
            Log.d("포커스", "제발")
            binding.etMyPageNickname.setFocusableInTouchMode(false)
            binding.etMyPageNickname.setFocusable(false)

            binding.etMyPageNickname.clearFocus()
            binding.clMypageModifyMain.requestFocus()
            binding.imgMyPageModifySwitch.requestFocus()
        }

        /*
        binding.etMyPageNickname.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                //  .. 포커스시
                Log.d("포커스1", "onFocus:${hasFocus}")
            } else {
                //  .. 포커스 뺏겼을 때
                Log.d("포커스2", "onFocus:${hasFocus}")

            }
        }

         */
    }

}
