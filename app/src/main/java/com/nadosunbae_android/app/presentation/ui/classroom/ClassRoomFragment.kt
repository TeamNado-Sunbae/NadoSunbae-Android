package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentClassRoomBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.domain.model.ui.MajorKeyData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ClassRoomFragment : BaseFragment<FragmentClassRoomBinding>(R.layout.fragment_class_room) {
    //메인뷰모델 초기화
    private val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectTitle()
        clickBottomSheet()
        initBottomSheet()
        changeTitle()
    }




    //질문 정보 탭 클릭시에
    private fun selectTitle(){
        binding.textClassroomQuestionTitle.setOnClickListener {
            mainViewModel.classRoomNum.value = 1
        }
        binding.textClassroomInfoTitle.setOnClickListener {
            mainViewModel.classRoomNum.value = 2
        }

        mainViewModel.classRoomNum.observe(viewLifecycleOwner){
            if(it == 1){
                binding.textClassroomQuestionTitle.isSelected = true
                binding.textClassroomInfoTitle.isSelected = false
                binding.textClassroomQuestionTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_semibold)
                binding.textClassroomInfoTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_regular)
                changeFragment(QuestionFragment())
            }else{
                binding.textClassroomQuestionTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_regular)
                binding.textClassroomInfoTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_semibold)
                binding.textClassroomInfoTitle.isSelected = true
                binding.textClassroomQuestionTitle.isSelected = false
                changeFragment(InformationFragment())
            }
        }
    }




    //질문 정보 프래그먼트 변경
    private fun changeFragment(fragment : Fragment){
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_classroom, fragment)
            .commit()

    }
    //바텀시트 선택
    private fun initBottomSheet(){
        majorBottomSheetDialog = CustomBottomSheetDialog(resources.getString(R.string.bottom_sheet_title_major))
        observeBottomSheet(mainViewModel, majorBottomSheetDialog)

        majorBottomSheetDialog.setCompleteListener {
            val selectedData = majorBottomSheetDialog.getSelectedData()
            if (selectedData != null) {
                val majorData = MajorKeyData(selectedData.id, selectedData.name)
                mainViewModel.setSelectedMajor(majorData)
            }

        }
    }
    //바텀시트 클릭
    private fun clickBottomSheet(){
        val showMajorBottomSheetDialog = {
            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)

            // (학과 선택) 기본 선택값 적용 (MainActivity setDefaultMajor에서 관리)
            majorBottomSheetDialog.setSelectedData(mainViewModel.selectedMajor.value!!.majorId )
        }
        binding.textClassroomTitle.setOnClickListener { showMajorBottomSheetDialog() }
        binding.imgClassroomQuestionTitle.setOnClickListener { showMajorBottomSheetDialog() }
    }

    //타이틀 변경
    private fun changeTitle(){
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            binding.textClassroomTitle.text = it.majorName
        }
    }

}