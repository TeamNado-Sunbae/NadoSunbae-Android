package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentSignUpMajorInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.SignInCustomDialog
import com.nadosunbae_android.domain.model.main.SelectableData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpMajorInfoFragment : BaseFragment<FragmentSignUpMajorInfoBinding>(R.layout.fragment_sign_up_major_info) {
    private val signViewModel: SignViewModel by viewModels()
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModels()

    val firstDepartmentBottomSheetDialog = CustomBottomSheetDialog("본전공")
    val firstDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("본전공 진입시기")
    val secondDepartmentBottomSheetDialog = CustomBottomSheetDialog("제2전공")
    val secondDepartmentPeriodBottomSheetDialog = CustomBottomSheetDialog("제2전공 진입시기")
    private val bottomSheetDialog = CustomBottomSheetDialog("본전공")

    private var firstMajorId = 0
    private var secondMajorId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
        changeNext()
        spinnerClickListener()

        with(binding) {
            makeUnivSpinner(rbUnivKorea, rbUnivSwu, rbUnivCau)
        }


    }

    //뒤로버튼으로 왔을 시 텍스트 필드 채우기
    private fun initTextfield() = with(binding) {
        val firstMajor = signUpBasicInfoViewModel.firstMajorName.value ?: "선택하기"
        textSignupMajorinfoMajor.text = firstMajor

        if (textSignupMajorinfoMajor.text.toString() == "선택하기") {
            textSignupMajorinfoMajor.setTextColor(Color.parseColor("#94959E"))
        }

        signViewModel.firstMajor.value = firstMajor
        firstMajorId = signUpBasicInfoViewModel.firstMajorId.value ?: 0
        textSignupMajorinfoMajorTime.text = signUpBasicInfoViewModel.firstMajorStart.value ?: "선택하기"


        //두번째 전공
        val secondMajor = signUpBasicInfoViewModel.secondMajorName.value ?: "선택하기"
        textSignupMajorinfoDoubleMajor.text = secondMajor

        if (textSignupMajorinfoDoubleMajor.text.toString() == "선택하기") {
            textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#94959E"))
        } else {
            textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#001D19"))
        }
        signViewModel.secondMajor.value = secondMajor

        secondMajorId = signUpBasicInfoViewModel.secondMajorId.value ?: 0
        textSignupMajorinfoDoubleMajorTime.text = signUpBasicInfoViewModel.secondMajorStart.value ?: "선택하기"
        if (signViewModel.secondMajor.value.toString() == "미진입") {

            signUpBasicInfoViewModel.secondDepartmentClick.value = true
            signUpBasicInfoViewModel.secondDepartmentGo.value = true

            clSignupMajorInfoDoubleMajorTime.isClickable = false
            textSignupMajorinfoDoubleMajorTime.text = "미진입"
            textSignupMajorinfoDoubleMajorMintTime.text = "선택"
            textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))

        }

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

        if (textSignupMajorinfoMajor.text.toString() != "선택하기") {
            textSignupMajorinfoMajor.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoMajorMint.text = "변경"
        }

        if (textSignupMajorinfoDoubleMajor.text.toString() == "미진입") {
            textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoDoubleMajorMint.text = "변경"
        }

        if (textSignupMajorinfoDoubleMajorTime.text.toString() != "선택하기") {
            textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoDoubleMajorMintTime.text = "변경"
        }

        if (textSignupMajorinfoDoubleMajorTime.text.toString() == "미진입") {
            textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#94959E"))
            binding.textSignupMajorinfoDoubleMajorMintTime.text = "변경"
        }
    }

    //X버튼 클릭 리스너
    private fun closePage() {
        binding.imgSignupMajorinfoDelete.setOnClickListener {
            val dialog = SignInCustomDialog(requireActivity())
            dialog.showDialog()

            dialog.setOnClickListener(object : SignInCustomDialog.ButtonClickListener {
                override fun onClicked(num: () -> Unit) {
                    startActivity(Intent(requireActivity(), SignInActivity::class.java))
                }
            })
        }
    }

    private fun moveBeforePage() {
        binding.clSignupMajorInfoMoveBefore.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }


    private fun nextBtnActivate() = with(binding){
        clSignupMajorInfoMoveNext.setOnClickListener {
            when(textSignupMajorinfoUniv.text){
                "고려대학교" -> signUpBasicInfoViewModel.univId.value = 1
                "서울여자대학교" -> signUpBasicInfoViewModel.univId.value = 2
                "중앙대학교" -> signUpBasicInfoViewModel.univId.value = 3
                else -> signUpBasicInfoViewModel.univId.value = null
            }
            signUpBasicInfoViewModel.apply {
                firstMajorId.value = firstDepartmentBottomSheetDialog.getSelectedData().id
                firstMajorName.value = textSignupMajorinfoMajor.text.toString()
                firstMajorStart.value = textSignupMajorinfoMajorTime.text.toString()
                secondMajorId.value = secondDepartmentBottomSheetDialog.getSelectedData().id
                secondMajorName.value = textSignupMajorinfoDoubleMajor.text.toString()
                textSignupMajorinfoDoubleMajorTime.text.toString()
            }
                findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
        }
    }


    private fun onClickbottomSheetUniv() {
        binding.clSignupMajorInfoUniv.setOnClickListener {
            bottomSheetDialog.show(parentFragmentManager, bottomSheetDialog.tag)
            bottomSheetDialog.binding.tvBottomsheeetTitle.text = "본 전공 진입시기"
        }
    }

    //제 1전공 학과 선택 바텀시트
    private fun firstMajor() {
        binding.clSignupMajorInfoMajor.setOnClickListener {
            firstDepartmentBottomSheetDialog.show(
                parentFragmentManager,
                firstDepartmentBottomSheetDialog.tag
            )
        }
        signUpBasicInfoViewModel.getFirstDepartment(1, "firstMajor")
        signUpBasicInfoViewModel.firstDepartment.observe(viewLifecycleOwner) {

            firstDepartmentBottomSheetDialog.setDataList(it.data.filter { it.isFirstMajor }
                .map { SelectableData(it.majorId, it.majorName, false) }.toMutableList())
        }

        //데이터 넣기
        firstDepartmentBottomSheetDialog.setCompleteListener {
            val firstMajor = firstDepartmentBottomSheetDialog.getSelectedData()
            signViewModel.firstMajor.value = firstMajor.name
            signViewModel.firstMajor
                .observe(viewLifecycleOwner) {
                    binding.textSignupMajorinfoMajor.text = it
                    binding.textSignupMajorinfoMajor.text = it

                    binding.textSignupMajorinfoMajor.setTextColor(Color.parseColor("#001D19"))
                    binding.textSignupMajorinfoMajorMint.text = "변경"
                }

            signUpBasicInfoViewModel.firstDepartmentClick.value = true
        }
    }

    //제 1전공 진입시기 선택 바텀시트
    private fun firstMajorPeriod() {
        // local data
        var firstMajorSelectionPeriodData = mutableListOf(
            SelectableData(1, "22-2", false),
            SelectableData(2, "22-1", false),
            SelectableData(3, "21-2", false),
            SelectableData(4, "21-1", false),
            SelectableData(5, "20-2", false),
            SelectableData(6, "20-1", false),
            SelectableData(7, "19-2", false),
            SelectableData(8, "19-1", false),
            SelectableData(9, "18-2", false),
            SelectableData(10, "18-1", false),
            SelectableData(11, "17-2", false),
            SelectableData(12, "17-1", false),
            SelectableData(13, "16-2", false),
            SelectableData(14, "16-1", false),
            SelectableData(15, "15-2", false),
            SelectableData(16, "15-1", false),
            SelectableData(17, "15년 이전", false)
        )
        firstDepartmentPeriodBottomSheetDialog.setDataList(firstMajorSelectionPeriodData)

        binding.clSignupMajorInfoMajorTime.setOnClickListener {
            firstDepartmentPeriodBottomSheetDialog.show(
                parentFragmentManager,
                firstDepartmentPeriodBottomSheetDialog.tag
            )

            firstDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val firstMajorPeriod = firstDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.firstMajorPeriod.value = firstMajorPeriod.name
                signUpBasicInfoViewModel.firstDepartmentGo.value = true
            }
            signViewModel.firstMajorPeriod.observe(viewLifecycleOwner) {
                binding.textSignupMajorinfoMajorTime.text = it
                binding.textSignupMajorinfoMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoMajorTimeMint.text = "변경"
            }
        }
    }


    //제 2전공 학과 선택 바텀시트
    private fun secondMajor() {
        binding.clSignupMajorInfoDoubleMajor.setOnClickListener {
            secondDepartmentBottomSheetDialog.show(
                parentFragmentManager,
                secondDepartmentBottomSheetDialog.tag
            )
            signUpBasicInfoViewModel.getSecondDepartment(1, "secondMajor")
        }

        signUpBasicInfoViewModel.secondDepartment.observe(viewLifecycleOwner) {

            secondDepartmentBottomSheetDialog.setDataList(it.data.filter { it.isSecondMajor }
                .map { SelectableData(it.majorId, it.majorName, false) }.toMutableList())
        }

        secondDepartmentBottomSheetDialog.setCompleteListener {
            val secondMajor = secondDepartmentBottomSheetDialog.getSelectedData()
            signViewModel.secondMajor.value = secondMajor.name

            signViewModel.secondMajor
                .observe(viewLifecycleOwner) {
                    binding.textSignupMajorinfoDoubleMajor.text = it
                    binding.textSignupMajorinfoDoubleMajor.text = it
                    binding.textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#001D19"))
                    binding.textSignupMajorinfoDoubleMajorMint.text = "변경"
                }


            if (signViewModel.secondMajor.value.toString() == "미진입") {

                signUpBasicInfoViewModel.secondDepartmentClick.value = true
                signUpBasicInfoViewModel.secondDepartmentGo.value = true

                binding.clSignupMajorInfoDoubleMajorTime.isClickable = false
                binding.textSignupMajorinfoDoubleMajorTime.text = "미진입"
                binding.textSignupMajorinfoDoubleMajorMintTime.text = "선택"
                binding.textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
            }
            if (signViewModel.secondMajor.value.toString() != "미진입") {
                signUpBasicInfoViewModel.secondDepartmentClick.value = true
                signUpBasicInfoViewModel.secondDepartmentGo.value = false
                binding.clSignupMajorInfoDoubleMajorTime.isClickable = true
            }
        }
    }


    //제 2전공 진입시기 바텀시트
    private fun secondMajorPeriod() {
        // test data
        var secondMajorSelectionPeriodData = mutableListOf(
            SelectableData(1, "22-2", false),
            SelectableData(2, "22-1", false),
            SelectableData(3, "21-2", false),
            SelectableData(4, "21-1", false),
            SelectableData(5, "20-2", false),
            SelectableData(6, "20-1", false),
            SelectableData(7, "19-2", false),
            SelectableData(8, "19-1", false),
            SelectableData(9, "18-2", false),
            SelectableData(10, "18-1", false),
            SelectableData(11, "17-2", false),
            SelectableData(12, "17-1", false),
            SelectableData(13, "16-2", false),
            SelectableData(14, "16-1", false),
            SelectableData(15, "15-2", false),
            SelectableData(16, "15-1", false),
            SelectableData(17, "15년 이전", false)
        )

        binding.clSignupMajorInfoDoubleMajorTime.setOnClickListener {

            secondDepartmentPeriodBottomSheetDialog.show(
                parentFragmentManager,
                secondDepartmentPeriodBottomSheetDialog.tag
            )

            secondDepartmentPeriodBottomSheetDialog.setCompleteListener {
                val secondMajorPeriod =
                    secondDepartmentPeriodBottomSheetDialog.getSelectedData()
                signViewModel.secondMajorPeriod.value = secondMajorPeriod.name

                signUpBasicInfoViewModel.secondDepartmentGo.value = true
            }

            signViewModel.secondMajorPeriod.observe(viewLifecycleOwner) {
                binding.textSignupMajorinfoDoubleMajorTime.text = it
                binding.textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#001D19"))
                binding.textSignupMajorinfoDoubleMajorMintTime.text = "변경"
            }
        }
        secondDepartmentPeriodBottomSheetDialog.setDataList(secondMajorSelectionPeriodData)
    }


    //전공 중복 체크
    private fun checkMajor() {
        if (signViewModel.firstMajor.value.toString() == signViewModel.secondMajor.value.toString()) {
            binding.clSignupMajorInfoMoveNext.isSelected = false
            binding.textSignupMajorInfoNext.isSelected = false
        }
    }


    //다음 버튼 변경
    private fun changeNext() {
        signUpBasicInfoViewModel.selectedAll.observe(viewLifecycleOwner) {
            binding.clSignupMajorInfoMoveNext.isSelected = it
            binding.textSignupMajorInfoNext.isSelected = it
            checkMajor()

            if (binding.clSignupMajorInfoMoveNext.isSelected && binding.textSignupMajorInfoNext.isSelected) {
                nextBtnActivate()
            } else {
                binding.clSignupMajorInfoMoveNext.isClickable = false
            }
        }
    }


    private fun setupSpinner() {
        val list = listOf("고려대학교", "서울여자대학교", "중앙대학교", "타 대학은 현재 준비중입니다")

        //val spinnerAdapter = SpinnerAdapter(this, R.layout.spinner_item, list)
        //binding.spinnerSignupMajorinfoUniv.adapter = spinnerAdapter


        // val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list)
        //  adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        //adapter.setDropDownViewResource(R.layout.list_id_select)
//        binding.spinnerSignupMajorinfoUniv.setAdapter(adapter)
//        binding.spinnerSignupMajorinfoUniv.dropDownVerticalOffset = PixelRatio().dpToPx(52)
    }


    private fun setupSpinnerHandler() {
//        binding.spinnerSignupMajorinfoUniv.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
////                    (parent!!.getChildAt(0) as TextView).setTextColor(Color.BLUE)
////                    (parent!!.getChildAt(0) as TextView).textSize = 5f
//
//                }
//
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                }
//            }
    }

    //라디오 버튼으로 세팅 및 해당 학교로 연결
    //TODO : 스피너로 활용하기......
    private fun makeUnivSpinner(view1: View, view2: View, view3: View) {
        view1.setOnClickListener {
            if (!view1.isSelected) {
                view1.isSelected = true
                view2.isSelected = false
                view3.isSelected = false
            }
            binding.textSignupMajorinfoUniv.text = "고려대학교"
            initUnivSetting()
        }
        view2.setOnClickListener {
            if (!view2.isSelected) {
                view1.isSelected = false
                view2.isSelected = true
                view3.isSelected = false
            }
            binding.textSignupMajorinfoUniv.text = "서울여자대학교"
            initUnivSetting()
        }
        view3.setOnClickListener {
            if (!view3.isSelected) {
                view1.isSelected = false
                view2.isSelected = false
                view3.isSelected = true
            }
            binding.textSignupMajorinfoUniv.text = "중앙대학교"
            initUnivSetting()
        }
    }

    //라디오버튼 클릭 리스너가 일어날 때 나타날 이벤트들
    private fun initUnivSetting() {
        binding.textSignupMajorinfoUniv.setTextColor(Color.parseColor("#001D19"))
        binding.clRbUniv.visibility = View.GONE
        binding.textSignupMajorInfoUnivMint.text = "변경"
    }

    private fun spinnerClickListener() {
        //대학 선택하는 레이아웃 클릭 시 나오는 뷰 설정
        binding.clSignupMajorInfoUniv.setOnClickListener {
            binding.clRbUniv.visibility = View.VISIBLE
        }

        //외부 영역 클릭시 스피너 안 보이게
        binding.clSign.setOnClickListener {
            binding.clRbUniv.visibility = View.GONE
        }
    }


}