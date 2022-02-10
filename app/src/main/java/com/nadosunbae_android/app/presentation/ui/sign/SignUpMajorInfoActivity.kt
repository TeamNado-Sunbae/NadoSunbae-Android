package com.nadosunbae_android.app.presentation.ui.sign


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.app.databinding.ActivitySignUpMajorInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.sign.adapter.SpinnerAdapter
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.PixelRatio
import com.nadosunbae_android.app.util.SignInCustomDialog


class SignUpMajorInfoActivity :
    BaseActivity<ActivitySignUpMajorInfoBinding>(R.layout.activity_sign_up_major_info) {
    private val signViewModel: SignViewModel by viewModels()

    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModels()

    val firstDepartmentBottomSheetDialog = CustomBottomSheetDialog("본전공")
    val firstDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("본전공 진입시기")

    val secondDepartmentBottomSheetDialog = CustomBottomSheetDialog("제2전공")
    val secondDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("제2전공 진입시기")

    private val bottomSheetDialog = CustomBottomSheetDialog("본전공")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        closePage()
        moveBeforePage()
        nextBtnActivate()
        onClickbottomSheetUniv()

        firstMajorPeriod()
        setupSpinner()
        setupSpinnerHandler()

        firstMajor()
        secondMajor()
        secondMajorPeriod()
    }

    //X버튼 클릭 리스너
    private fun closePage() {
        binding.imgSignupMajorinfoDelete.setOnClickListener {
            val dialog = SignInCustomDialog(this)
            dialog.showDialog()

            dialog.setOnClickListener(object : SignInCustomDialog.ButtonClickListener {
                override fun onClicked(num: () -> Unit) {
                    startActivity(Intent(this@SignUpMajorInfoActivity, SignInActivity::class.java))
                    finish()
                }

            })

        }
    }

    private fun moveBeforePage() {
        binding.clSignupMajorInfoMoveBefore.setOnClickListener {
            startActivity(Intent(this, SignUpAgreementActivity::class.java))
            finish()
        }
    }


    private fun nextBtnActivate() {
//        if(binding.textSignupMajorinfoMajorMint.text == "변경") {
//            binding.clSignupMajorInfoMoveNext.isSelected = true
//        }
//        else {
//            binding.clSignupMajorInfoMoveNext.isSelected = false
//        }
        //binding.clSignupMajorInfoMoveNext.isSelected = true
        if(binding.clSignupMajorInfoMoveNext.isSelected == true) {
            binding.clSignupMajorInfoMoveNext.setOnClickListener {

                // 버튼 활성화 CHECK
//            if (binding.clSignupMajorInfoMoveNext.isSelected) {
                //signUpBasicInfoViewModel.requestSignUp.firstMajorId = binding.textSignupMajorInfoMajor.id
                signUpBasicInfoViewModel.requestSignUp.firstMajorStart =
                    binding.textSignupMajorInfoMajorTime.text.toString()
                signUpBasicInfoViewModel.requestSignUp.secondMajorId =
                    binding.textSignupMajorInfoMajor.id
                signUpBasicInfoViewModel.requestSignUp.secondMajorStart =
                    binding.textSignupMajorInfoDoubleMajorTime.text.toString()

                startActivity(Intent(this, SignUpBasicInfoActivity::class.java))
                //}

            }

        }
    }

    private fun onClickbottomSheetUniv() {
        binding.clSignupMajorInfoUniv.setOnClickListener {

            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)

        }
    }

    //제 1전공 학과 선택 바텀시트
    private fun firstMajor() {
        binding.clSignupMajorInfoMajor.setOnClickListener {
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
                binding.textSignupMajorinfoMajor.setText(it)
                binding.textSignupMajorinfoMajor.text = it

                binding.textSignupMajorinfoMajor.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoMajorMint.text = "변경"

                //val firstDepartment = signUpBasicInfoViewModel.firstDepartment.value
            }
//        signUpBasicInfoViewModel.signUp.observe(this) {
//        }
    }

    //제 1전공 진입시기 선택 바텀시트
    private fun firstMajorPeriod() {
        bottomSheetDialog.binding.tvBottomsheeetTitle.text = "본 전공 진입시기"
        binding.clSignupMajorInfoMajorTime.setOnClickListener {
            firstDepartmentPeriodBottomSheetDialog.show(
                supportFragmentManager,
                firstDepartmentPeriodBottomSheetDialog.tag
            )
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

            firstDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val firstMajorPeriod = firstDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.firstMajorPeriod.value = firstMajorPeriod?.name
            }
            signViewModel.firstMajorPeriod.observe(this) {
                binding.textSignupMajorinfoMajorTime.setText(it)
                binding.textSignupMajorinfoMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoMajorTimeMint.text= "변경"
            }
        }
    }


    //제 2전공 학과 선택 바텀시트
    private fun secondMajor() {
        binding.clSignupMajorInfoDoubleMajor.setOnClickListener {
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
                binding.clSignupMajorInfoDoubleMajorTime.isClickable = false
                binding.textSignupMajorinfoDoubleMajorTime.text = "선택하기"
                binding.textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
                binding.textSignupMajorinfoDoubleMajorMintTime.setText("선택")
            } else {
                binding.clSignupMajorInfoDoubleMajorTime.isClickable = true
            }
        }
        signViewModel.secondMajor
            .observe(this) {
                binding.textSignupMajorinfoDoubleMajor.setText(it)
                binding.textSignupMajorinfoDoubleMajor.text = it
                binding.textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoDoubleMajorMint.text = "변경"
            }

    }


    //제 2전공 진입시기 바텀시트
    private fun secondMajorPeriod() {
        binding.clSignupMajorInfoDoubleMajorTime.setOnClickListener {

            secondDepartmentPeriodBottomSheetDialog.show(
                supportFragmentManager,
                secondDepartmentPeriodBottomSheetDialog.tag
            )
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

            var secondMajorSelectionPeriodDatNot = mutableListOf(
                SelectableData(1, "미진입", false)
            )

            secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodData)

            if (binding.textSignupMajorinfoDoubleMajor.text == "미진입") {
                secondDepartmentPeriodBottomSheetDialog.setDataList(
                    secondMajorSelectionPeriodDatNot
                )
            } else {
                secondDepartmentPeriodBottomSheetDialog.setDataList(
                    secondMajorSelectionPeriodData
                )
            }

            secondDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val secondMajorPeriod =
                    secondDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.secondMajorPeriod.value = secondMajorPeriod?.name
            }

            signViewModel.secondMajorPeriod.observe(this) {
                binding.textSignupMajorinfoDoubleMajorTime.text = it
                binding.textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoDoubleMajorMintTime.setText("변경")
            }

        }
        initNextBtnClick()
    }

    //다음으로 가는 버튼 활성화
    private fun initNextBtnClick() {
        binding.apply {
            if(textSignupMajorInfoDoubleMajor.text.toString() == "미진입") {
                if (textSignupMajorInfoMajor.text.toString() == "선택하기" || textSignupMajorInfoMajorTime.text.toString() == "선택하기") {
                    clSignupMajorInfoMoveNext.isSelected = false
                }
            }
            else {
                if (textSignupMajorInfoMajor.text.toString() == "선택하기" || textSignupMajorInfoMajorTime.text.toString() == "선택하기"
                    || textSignupMajorInfoDoubleMajor.text.toString() == "선택하기" || textSignupMajorInfoDoubleMajorTime.text.toString() == "선택하기") {
                    clSignupMajorInfoMoveNext.isSelected = false
                } else {
                    clSignupMajorInfoMoveNext.isSelected = false
                }
            }

        }
    }

    private fun setupSpinner() {
        val list = listOf("고려대학교", "타 대학은 현재 준비중입니다")
        val spinnerAdapter = SpinnerAdapter(this, R.layout.spinner_item, list)
        binding.spinnerSignupMajorinfoUniv.adapter = spinnerAdapter
        binding.spinnerSignupMajorinfoUniv.dropDownVerticalOffset = PixelRatio().dpToPx(52)
    }


    private fun setupSpinnerHandler() {
        binding.spinnerSignupMajorinfoUniv.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

    }


}


