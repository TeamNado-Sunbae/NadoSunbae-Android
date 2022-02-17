package com.nadosunbae_android.app.presentation.ui.mypage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import org.koin.android.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
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

    }


    //기존 데이터 불러오기
    private fun initWriteMode() {

        mainViewModel.signData.observe(this) {
            myPageViewModel.getPersonalInfo(it.userId)
        }

        //userID가 null로 들어옴
        myPageViewModel.getPersonalInfo(mainViewModel.userId.value ?: 0)

        myPageViewModel.personalInfo.observe(this) {
            binding.myPageModify = it
            Log.d("나올까?" , "3")
            binding.etMyPageNickname.setText(it.data.nickname)
            Log.d("나올까?" , it.data.nickname )
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
            signUpBasicInfoViewModel.firstDepartmentClick.value = true
        }

        signViewModel.firstMajor
            .observe(this) {
                binding.textMyPageMajorinfoMajor.setText(it)
                binding.textMyPageMajorinfoMajor.text = it

                binding.textMyPageMajorinfoMajorMint.setTextColor(Color.parseColor("#001D19"))
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
                signUpBasicInfoViewModel.firstDepartmentGo.value = true
            }
            signViewModel.firstMajorPeriod.observe(this) {
                binding.textMyPageMajorinfoMajorTime.setText(it)
                binding.textMyPageMajorinfoMajorTime.setTextColor(Color.parseColor("#001D19"))
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
            signUpBasicInfoViewModel.secondDepartmentClick.value = true

            if (signViewModel.secondMajor.value.toString() == "미진입") {

                signUpBasicInfoViewModel.secondDepartmentClick.value = true
                signUpBasicInfoViewModel.secondDepartmentGo.value = true

                binding.textMyPageMajorInfoDoubleMajorTime.isClickable = false
                binding.textMyPageMajorinfoDoubleMajorTime.text = "선택하기"
                binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
                binding.textMyPageMajorinfoDoubleMajorMintTime.setText("선택")
            } else {
                signUpBasicInfoViewModel.secondDepartmentGo.value = false
                binding.clMyPageMajorInfoDoubleMajorTime.isClickable = true
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

        binding.clMyPageMajorInfoDoubleMajorTime.setOnClickListener {
            secondDepartmentPeriodBottomSheetDialog.show(
                supportFragmentManager,
                secondDepartmentPeriodBottomSheetDialog.tag
            )

            secondDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val secondMajorPeriod = secondDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.secondMajorPeriod.value = secondMajorPeriod?.name

                signUpBasicInfoViewModel.secondDepartmentGo.value = true
            }

            signViewModel.secondMajorPeriod.observe(this) {
                binding.textMyPageMajorinfoDoubleMajorTime.text = it
                binding.textMyPageMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textMyPageMajorinfoDoubleMajorMintTime.setText("변경")
            }

        }
        var secondMajorSelectionPeriodDatNot = mutableListOf(
            SelectableData(1, "미진입", false)
        )

        if (binding.textMyPageMajorinfoDoubleMajor.text == "미진입") {
            secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodDatNot)
        } else {
            secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodData)
        }
    }

    //질문 스위치 클릭
    private fun pressSwitchEvent() {
        binding.imgMyPageModifySwitch.isSelected = !binding.imgMyPageModifySwitch.isSelected
    }

}