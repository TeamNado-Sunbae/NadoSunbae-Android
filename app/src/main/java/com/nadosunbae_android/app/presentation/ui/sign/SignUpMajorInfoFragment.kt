package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentSignUpMajorInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.SignInCustomDialog
import com.nadosunbae_android.domain.model.main.SelectableData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class SignUpMajorInfoFragment :
    BaseFragment<FragmentSignUpMajorInfoBinding>(R.layout.fragment_sign_up_major_info) {

    private val signViewModel: SignViewModel by activityViewModels()
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by activityViewModels()

    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    private val firstDepartmentPeriodBottomSheetDialog =
        CustomBottomSheetDialog("본전공 진입시기", false, null, false, true)
    private lateinit var secondDepartmentBottomSheetDialog: CustomBottomSheetDialog
    private val secondDepartmentPeriodBottomSheetDialog =
        CustomBottomSheetDialog("제2전공 진입시기", false, null, false, true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        majorBottomSheetDialog = CustomBottomSheetDialog(
            getString(R.string.signup_first_major),
            false,
            0,
            false,
            isSignUp = true
        )
        secondDepartmentBottomSheetDialog = CustomBottomSheetDialog(
            getString(R.string.signup_second_major),
            false,
            0,
            false,
            isSignUp = true
        )
        deleteAll()
        initTextfield()
        closePage()
        moveBeforePage()
        onClickbottomSheetUniv()
        changeNext()
        spinnerClickListener()
        initSelectUniv()
        initBottomSheet()
        updateMajorStatus()
        initSecondBottomSheet()
        updateSecondMajorStatus()
        isActive()

        with(binding) {
            makeUnivSpinner(rbUnivKorea, rbUnivSwu, rbUnivCau)
        }
    }

    //뒤로버튼으로 왔을 시 텍스트 필드 채우기
    private fun initTextfield() = with(binding) {
        val univ = signUpBasicInfoViewModel.univName.value ?: "선택하기"
        textSignupMajorinfoUniv.text = univ
        if (textSignupMajorinfoUniv.text.toString() != "선택하기") {
            textSignupMajorinfoUniv.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorInfoUnivMint.text = "변경"
        }

        val firstMajorTime = signUpBasicInfoViewModel.firstMajorStart.value ?: "선택하기"
        textSignupMajorinfoMajorTime.text = firstMajorTime
        if (textSignupMajorinfoMajorTime.text.toString() != "선택하기") {
            textSignupMajorinfoMajorTime.setTextColor(Color.parseColor("#001D19"))
            binding.textSignupMajorinfoMajorTimeMint.text = "변경"
        }

        val secondMajorTime = signUpBasicInfoViewModel.secondMajorStart.value ?: "선택하기"
        textSignupMajorinfoDoubleMajorTime.text = secondMajorTime
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
                    activity?.finish()
                }
            })
        }
    }

    private fun isActive() {
        signViewModel.isActive.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.clSignupMajorInfoMoveNext.setBackgroundResource(R.drawable.rectangle_fill_black_14)
                binding.textSignupMajorInfoNext.setTextColor(Color.parseColor("#00C8B0"))
                nextBtnActivate()
            } else {
                binding.clSignupMajorInfoMoveNext.setBackgroundResource(R.drawable.rectangle_fill_gray_14)
                binding.textSignupMajorInfoNext.setTextColor(Color.parseColor("#94959E"))
                binding.clSignupMajorInfoMoveNext.isClickable = false
            }
        }
    }

    private fun moveBeforePage() {
        binding.clSignupMajorInfoMoveBefore.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            signUpBasicInfoViewModel.univName.value = "선택하기"
            signUpBasicInfoViewModel.firstMajorStart.value = null
            signUpBasicInfoViewModel.secondMajorStart.value = null
            signViewModel.setFirstFilter(SelectableData.SIGNDEFAULT)
            signViewModel.setSecondFilter(SelectableData.SIGNDEFAULT)
            signViewModel.firstFilter.value.id = -1
            signViewModel.secondFilter.value.id= -1
            signViewModel.isActive.value = false
        }
    }

    private fun nextBtnActivate() = with(binding) {
        clSignupMajorInfoMoveNext.setOnClickListener {
            when (textSignupMajorinfoUniv.text.toString()) {
                "고려대학교" -> signUpBasicInfoViewModel.univId.value = 1
                "서울여자대학교" -> signUpBasicInfoViewModel.univId.value = 2
                "중앙대학교" -> signUpBasicInfoViewModel.univId.value = 3
                else -> signUpBasicInfoViewModel.univId.value = null
            }
            signUpBasicInfoViewModel.apply {
                univName.value = textSignupMajorinfoUniv.text.toString()
                firstMajorId.value = majorBottomSheetDialog.getSelectedData().id
                firstMajorName.value = textSignupMajorinfoMajor.text.toString()
                firstMajorStart.value = textSignupMajorinfoMajorTime.text.toString()
                secondMajorId.value = secondDepartmentBottomSheetDialog.getSelectedData().id
                secondMajorName.value = textSignupMajorinfoDoubleMajor.text.toString()
                secondMajorStart.value = textSignupMajorinfoDoubleMajorTime.text.toString()
                textSignupMajorinfoDoubleMajorTime.text.toString()
            }
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
            signViewModel.isActive.value = true
        }
    }


    private fun onClickbottomSheetUniv() {
        binding.clSignupMajorInfoUniv.setOnClickListener {
            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)
            majorBottomSheetDialog.binding.tvBottomsheeetTitle.text = "본 전공 진입시기"
        }
    }


    //제 1전공 학과 선택 바텀시트
    private fun firstMajor() {
        val showMajorBottomSheetDialog = {
            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)
        }

        binding.clSignupMajorInfoMajor.setOnClickListener {
            signViewModel.getMajorList(
                signUpBasicInfoViewModel.univSelect.value ?: 1, "firstMajor", "noMajor",
                MainGlobals.signInData?.userId ?: 0
            )
            showMajorBottomSheetDialog()
        }
    }

    private fun initBottomSheet() {
        signViewModel.getMajorList(
            signUpBasicInfoViewModel.univSelect.value ?: 1, "firstMajor", "noMajor",
            MainGlobals.signInData?.userId ?: 0
        )

        signViewModel.firstMajorList.observe(viewLifecycleOwner) {
            observeBottomSheet2(
                it,
                majorBottomSheetDialog
            )
        }
    }

    private fun updateMajorStatus() {
        majorBottomSheetDialog.setCompleteListener {
            signViewModel.setFirstFilter(majorBottomSheetDialog.getSelectedData())
        }
        signViewModel.firstFilter.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.id == -1 || it.name == "선택하기") {
                    it.id = signViewModel.firstMajorList.value?.get(0)?.majorId ?: -1
                    binding.textSignupMajorinfoMajor.setTextColor(Color.parseColor("#94959E"))
                    binding.textSignupMajorinfoMajorMint.text = "선택"
                }
                else {
                    binding.textSignupMajorinfoMajor.text = it.name
                    binding.textSignupMajorinfoMajor.setTextColor(Color.parseColor("#001D19"))
                    binding.textSignupMajorinfoMajorMint.text = "변경"
                }
            }
            .launchIn(lifecycleScope)
        signUpBasicInfoViewModel.firstDepartmentClick.value = true
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
        val showMajorBottomSheetDialog = {
            secondDepartmentBottomSheetDialog.show(
                parentFragmentManager,
                secondDepartmentBottomSheetDialog.tag
            )
        }

        binding.clSignupMajorInfoDoubleMajor.setOnClickListener {
            signViewModel.getMajorList(
                signUpBasicInfoViewModel.univSelect.value ?: 1, "secondMajor", null,
                MainGlobals.signInData?.userId ?: 0
            )
            showMajorBottomSheetDialog()
        }
    }


    private fun initSecondBottomSheet() {
        signViewModel.getMajorList(
            signUpBasicInfoViewModel.univSelect.value ?: 1, "secondMajor", null,
            MainGlobals.signInData?.userId ?: 0
        )

        signViewModel.secondMajorList.observe(viewLifecycleOwner) {
            observeBottomSheet2(
                it,
                secondDepartmentBottomSheetDialog
            )
        }
    }

    private fun updateSecondMajorStatus() {
        secondDepartmentBottomSheetDialog.setCompleteListener {
            signViewModel.setSecondFilter(secondDepartmentBottomSheetDialog.getSelectedData())
        }
        signViewModel.secondFilter.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.id == -1 || it.name == "선택하기") {
                    it.id = signViewModel.secondMajorList.value?.get(0)?.majorId ?: -1
                    binding.textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#94959E"))
                    binding.textSignupMajorinfoDoubleMajorMint.text = "선택"
                } else {
                    binding.textSignupMajorinfoDoubleMajor.text = it.name
                    binding.textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#001D19"))
                    binding.textSignupMajorinfoDoubleMajorMint.text = "변경"
                    if (it.name == "미진입") {
                        signUpBasicInfoViewModel.secondDepartmentClick.value = true
                        signUpBasicInfoViewModel.secondDepartmentGo.value = true
                        binding.clSignupMajorInfoDoubleMajorTime.isClickable = false
                        binding.textSignupMajorinfoDoubleMajorTime.text = "미진입"
                        binding.textSignupMajorinfoDoubleMajorMintTime.text = "선택"
                        binding.textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#C0C0CB"))
                    }
                    if (it.name != "미진입") {
                        signUpBasicInfoViewModel.secondDepartmentClick.value = true
                        signUpBasicInfoViewModel.secondDepartmentGo.value = false
                        binding.clSignupMajorInfoDoubleMajorTime.isClickable = true
                    }

                }
            }
            .launchIn(lifecycleScope)
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
            Timber.e("TEST1: ${it}")
            binding.clSignupMajorInfoMoveNext.isSelected = it
            binding.textSignupMajorInfoNext.isSelected = it
            checkMajor()
            nextBtnActivate()
            if (it == true) {
                binding.clSignupMajorInfoMoveNext.setBackgroundResource(R.drawable.rectangle_fill_black_14)
                binding.textSignupMajorInfoNext.setTextColor(Color.parseColor("#00C8B0"))
                nextBtnActivate()
            } else {
                binding.clSignupMajorInfoMoveNext.setBackgroundResource(R.drawable.rectangle_fill_gray_14)
                binding.textSignupMajorInfoNext.setTextColor(Color.parseColor("#94959E"))
                binding.clSignupMajorInfoMoveNext.isClickable = false
            }
        }
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
            signUpBasicInfoViewModel.univSelect.value = 1
            initUnivSetting()
            initSelectUniv()
            deleteAll()
        }
        view2.setOnClickListener {
            if (!view2.isSelected) {
                view1.isSelected = false
                view2.isSelected = true
                view3.isSelected = false
            }
            binding.textSignupMajorinfoUniv.text = "서울여자대학교"
            signUpBasicInfoViewModel.univSelect.value = 2
            initUnivSetting()
            initSelectUniv()
            deleteAll()
        }
        view3.setOnClickListener {
            if (!view3.isSelected) {
                view1.isSelected = false
                view2.isSelected = false
                view3.isSelected = true
            }
            binding.textSignupMajorinfoUniv.text = "중앙대학교"
            signUpBasicInfoViewModel.univSelect.value = 3
            initUnivSetting()
            initSelectUniv()
            deleteAll()
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

    //학교 선택 후에만 전공, 전공 진입시기 고를 수 있음
    private fun initSelectUniv() = with(binding) {
        if (textSignupMajorinfoUniv.text.toString() != "선택하기") {
            firstMajor()
            firstMajorPeriod()
            secondMajor()
            secondMajorPeriod()

            textSignupMajorinfoMajorMint.isSelected = true
            textSignupMajorinfoMajorTimeMint.isSelected = true
            textSignupMajorinfoDoubleMajorMint.isSelected = true
            textSignupMajorinfoDoubleMajorMintTime.isSelected = true
        }
    }

    //대학 선택 바꾸면 전공, 전공 진입시기 모두 초기화
    private fun deleteAll() = with(binding) {
        signUpBasicInfoViewModel.selectedAll.value = false
        textSignupMajorinfoMajor.text = "선택하기"
        textSignupMajorinfoMajor.setTextColor(Color.parseColor("#94959E"))

        textSignupMajorinfoMajorTime.text = "선택하기"
        textSignupMajorinfoMajorTime.setTextColor(Color.parseColor("#94959E"))

        textSignupMajorinfoDoubleMajor.text = "선택하기"
        textSignupMajorinfoDoubleMajor.setTextColor(Color.parseColor("#94959E"))

        textSignupMajorinfoDoubleMajorTime.text = "선택하기"
        textSignupMajorinfoDoubleMajorTime.setTextColor(Color.parseColor("#94959E"))

        textSignupMajorinfoMajorMint.text = "선택"
        textSignupMajorinfoMajorTimeMint.text = "선택"
        textSignupMajorinfoDoubleMajorMint.text = "선택"
        textSignupMajorinfoDoubleMajorMintTime.text = "선택"
    }
}