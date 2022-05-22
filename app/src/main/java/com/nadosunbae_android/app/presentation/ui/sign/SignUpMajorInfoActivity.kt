package com.nadosunbae_android.app.presentation.ui.sign


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySignUpMajorInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.sign.adapter.SpinnerAdapter
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.PixelRatio
import com.nadosunbae_android.app.util.SignInCustomDialog
import com.nadosunbae_android.domain.model.main.SelectableData
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpMajorInfoActivity :
    BaseActivity<ActivitySignUpMajorInfoBinding>(R.layout.activity_sign_up_major_info) {
    private val signViewModel: SignViewModel by viewModel()
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModel()

    val firstDepartmentBottomSheetDialog = CustomBottomSheetDialog("본전공")
    val firstDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("본전공 진입시기")
    val secondDepartmentBottomSheetDialog = CustomBottomSheetDialog("제2전공")
    val secondDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("제2전공 진입시기")
    private val bottomSheetDialog = CustomBottomSheetDialog("본전공")

    private var firstMajorId = 0
    private var secondMajorId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTextfield()
        closePage()
        moveBeforePage()
        onClickbottomSheetUniv()
        firstMajorPeriod()
        setupSpinner()
        setupSpinnerHandler()
        secondMajorPeriod()
        firstMajor()
        secondMajor()
        //checkMajor()
        changeNext()
    }

    //뒤로버튼으로 왔을 시 텍스트 필드 채우기
    private fun initTextfield() = with(binding) {
        textSignupMajorinfoMajor.setText(intent.getStringExtra("firstMajorName") ?: "선택하기")
        firstMajorId = intent.getIntExtra("firstMajorId", 0)
        textSignupMajorinfoMajorTime.setText(intent.getStringExtra("firstMajorStart") ?: "선택하기")

        //두번째 전공
        textSignupMajorinfoDoubleMajor.setText(intent.getStringExtra("secondMajorName") ?: "선택하기")
        secondMajorId = intent.getIntExtra("secondMajorId", 0)
        textSignupMajorinfoDoubleMajorTime.setText(
            intent.getStringExtra("secondMajorStart") ?: "선택하기"
        )

        //선택하기 분기처리
        if (textSignupMajorinfoMajor.text.toString() != "선택하기") {
            textSignupMajorinfoMajor.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoMajorMint.text = "변경"

            signUpBasicInfoViewModel.firstDepartmentClick.value = true
            signUpBasicInfoViewModel.firstDepartmentGo.value = true
            signUpBasicInfoViewModel.secondDepartmentClick.value = true
            signUpBasicInfoViewModel.secondDepartmentGo.value = true

            changeNext()
        }

        if (textSignupMajorinfoMajorTime.text.toString() != "선택하기") {
            textSignupMajorinfoMajorTime.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoMajorTimeMint.text = "변경"
        }

        if (textSignupMajorinfoDoubleMajor.text.toString() != "선택하기") {
            textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoDoubleMajorMint.text = "변경"
        }
        if (textSignupMajorinfoDoubleMajor.text.toString() == "미진입") {
            textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoDoubleMajorMint.text = "변경"
        }


        if (textSignupMajorinfoDoubleMajorTime.text.toString() != "선택하기") {
            textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoDoubleMajorMintTime.setText("변경")
        }

        if (textSignupMajorinfoDoubleMajorTime.text.toString() == "미진입") {
            textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#94959E"))
            binding.textSignupMajorinfoDoubleMajorMintTime.setText("변경")
        }
    }

    //X버튼 클릭 리스너
    private fun closePage() {
        binding.imgSignupMajorinfoDelete.setOnClickListener {
            val dialog = SignInCustomDialog(this)
            dialog.showDialog()

            dialog.setOnClickListener(object : SignInCustomDialog.ButtonClickListener {
                override fun onClicked(num: () -> Unit) {
                    startActivity(Intent(this@SignUpMajorInfoActivity, SignInActivity::class.java))
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
        binding.clSignupMajorInfoMoveNext.setOnClickListener {
            val intent = Intent(this, SignUpBasicInfoActivity::class.java)

            intent.putExtra(
                "firstMajorId",
                firstDepartmentBottomSheetDialog.getSelectedData()?.id ?: firstMajorId
            )
            intent.putExtra("firstMajorStart", binding.textSignupMajorinfoMajorTime.text.toString())
            intent.putExtra(
                "secondMajorId",
                secondDepartmentBottomSheetDialog.getSelectedData()?.id ?: secondMajorId
            )
            intent.putExtra(
                "secondMajorStart",
                binding.textSignupMajorinfoDoubleMajorTime.text.toString()
            )
            intent.putExtra("firstMajorName", binding.textSignupMajorinfoMajor.text.toString())
            intent.putExtra(
                "secondMajorName",
                binding.textSignupMajorinfoDoubleMajor.text.toString()
            )
            startActivity(intent)
            finish()
        }
    }


    private fun onClickbottomSheetUniv() {
        binding.clSignupMajorInfoUniv.setOnClickListener {
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
            bottomSheetDialog.binding.tvBottomsheeetTitle.text = "본 전공 진입시기"
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
            signUpBasicInfoViewModel.firstDepartmentClick.value = true
        }

        signViewModel.firstMajor
            .observe(this) {
                binding.textSignupMajorinfoMajor.setText(it)
                binding.textSignupMajorinfoMajor.text = it

                binding.textSignupMajorinfoMajor.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoMajorMint.text = "변경"
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

        binding.clSignupMajorInfoMajorTime.setOnClickListener {
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
                binding.textSignupMajorinfoMajorTime.setText(it)
                binding.textSignupMajorinfoMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoMajorTimeMint.text = "변경"
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
                signUpBasicInfoViewModel.secondDepartmentClick.value = true
                signUpBasicInfoViewModel.secondDepartmentGo.value = true

                binding.clSignupMajorInfoDoubleMajorTime.isClickable = false
                binding.textSignupMajorinfoDoubleMajorTime.text = "미진입"
                binding.textSignupMajorinfoDoubleMajorMintTime.setText("선택")
                binding.textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))

            } else if (signUpBasicInfoViewModel.secondDepartmentClick.value == true && binding.textSignupMajorInfoDoubleMajorTime.text.toString() != "") {
                signUpBasicInfoViewModel.secondDepartmentClick.value = true
                signUpBasicInfoViewModel.secondDepartmentGo.value = true
            } else {
                signUpBasicInfoViewModel.secondDepartmentClick.value = true
                signUpBasicInfoViewModel.secondDepartmentGo.value = false
                //binding.clSignupMajorInfoDoubleMajorTime.isClickable = true
            }

            if (binding.textSignupMajorinfoMajor.text.toString() == binding.textSignupMajorinfoDoubleMajor.text.toString()) {
                binding.clSignupMajorInfoMoveNext.isSelected = false
                binding.textSignupMajorInfoNext.isSelected = false
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

        binding.clSignupMajorInfoDoubleMajorTime.setOnClickListener {

            secondDepartmentPeriodBottomSheetDialog.show(
                supportFragmentManager,
                secondDepartmentPeriodBottomSheetDialog.tag
            )

            secondDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val secondMajorPeriod =
                    secondDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.secondMajorPeriod.value = secondMajorPeriod?.name

                signUpBasicInfoViewModel.secondDepartmentGo.value = true
            }

            signViewModel.secondMajorPeriod.observe(this) {
                binding.textSignupMajorinfoDoubleMajorTime.text = it
                binding.textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoDoubleMajorMintTime.setText("변경")
            }
        }
        secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodData)
//        var secondMajorSelectionPeriodDatNot = mutableListOf(
//            SelectableData(1, "미진입", false)
//        )
//
//        if (binding.textSignupMajorinfoDoubleMajor.text == "미진입") {
//            secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodDatNot)
//        } else {
//            secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodData)
//        }
    }


    //전공 중복 체크
    private fun checkMajor() {
        if (signViewModel.firstMajor.value.toString() == signViewModel.secondMajor.value.toString()) {
            binding.clSignupMajorInfoMoveNext.isSelected = false
            binding.textSignupMajorInfoNext.isSelected = false
        } else if (binding.textSignupMajorinfoMajor.text.toString() == binding.textSignupMajorinfoDoubleMajor.text.toString()) {
            binding.clSignupMajorInfoMoveNext.isSelected = false
            binding.textSignupMajorInfoNext.isSelected = false
        }

    }


    //다음 버튼 변경
    private fun changeNext() {
        signUpBasicInfoViewModel.selectedAll.observe(this) {

            checkMajor()

            binding.clSignupMajorInfoMoveNext.isSelected = it
            binding.textSignupMajorInfoNext.isSelected = it



            if (binding.clSignupMajorInfoMoveNext.isSelected && binding.textSignupMajorInfoNext.isSelected) {
                nextBtnActivate()
            } else {
                binding.clSignupMajorInfoMoveNext.isClickable = false
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