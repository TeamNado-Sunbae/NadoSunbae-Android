package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentClassRoomBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.domain.model.main.MajorSelectData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassRoomFragment : BaseFragment<FragmentClassRoomBinding>(R.layout.fragment_class_room) {
    //메인뷰모델 초기화
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectTitle()
        clickBottomSheet()
        initBottomSheet()
        changeTitle()
        goInfoWrite()
        hideInfo()
    }




    //질문 정보 탭 클릭시에
    private fun selectTitle(){

//        binding.textClassroomQuestionTitle.setOnClickListener {
//            mainViewModel.classRoomNum.value = 1
//        }
//        binding.textClassroomInfoTitle.setOnClickListener {
//            mainViewModel.classRoomNum.value = 2
//        }
//
//        mainViewModel.classRoomNum.observe(viewLifecycleOwner){
//            Timber.d("classRoomNum : $it")
//            if(it == 1){
//                binding.textClassroomQuestionTitle.isSelected = true
//                binding.textClassroomInfoTitle.isSelected = false
//                binding.textClassroomQuestionTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_semibold)
//                binding.textClassroomInfoTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_regular)
//                binding.btnGoInformationWrite.visibility = View.GONE
//                changeFragment(QuestionFragment())
//                binding.textInfoEmpty.visibility = View.GONE
//            }else{
//                binding.textClassroomQuestionTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_regular)
//                binding.textClassroomInfoTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_semibold)
//                binding.textClassroomInfoTitle.isSelected = true
//                binding.textClassroomQuestionTitle.isSelected = false
//                binding.btnGoInformationWrite.visibility = View.VISIBLE
//                changeFragment(InformationFragment())
//            }
//        }
    }
    //정보 등록글 없을 경우
    private fun hideInfo(){
//        mainViewModel.classRoomInfoEmpty.observe(viewLifecycleOwner){
//            if(it == 0){
//                binding.textInfoEmpty.apply{
//                    visibility = View.VISIBLE
//                    bringToFront()
//                }
//            }else{
//                binding.textInfoEmpty.apply{
//                    visibility = View.GONE
//                }
//
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
//        if(mainViewModel.classRoomNum.value == 1){
//            binding.textInfoEmpty.visibility = View.GONE
//        }
    }

    //정보 작성창 이동
    private fun goInfoWrite() {
//        binding.btnGoInformationWrite.setOnClickListener {
//            CustomDialog(requireActivity()).restrictDialog(
//                requireActivity(),
//                ReviewGlobals.isReviewed,
//                MainGlobals.signInData!!.isUserReported,
//                MainGlobals.signInData!!.isReviewInappropriate,
//                MainGlobals.signInData?.message.toString(),
//                behavior = {
//                    val intent = Intent(requireActivity(), QuestionWriteActivity::class.java)
//                    intent.apply {
//                        putExtra("postTypeId", 2)
//                        putExtra("majorId", mainViewModel.selectedMajor.value?.majorId)
//                        putExtra("title", "정보글 작성")
//                        putExtra("division", InformationFragment.write)
//                        putExtra("hintContent", getString(R.string.information_hint_write))
//                    }
//                    startActivity(intent)
//                })
//        }
    }



    //질문 정보 프래그먼트 변경
    private fun changeFragment(fragment : Fragment){
//        childFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container_classroom, fragment)
//            .commit()

    }
    //바텀시트 선택
    private fun initBottomSheet(){
        majorBottomSheetDialog = CustomBottomSheetDialog(resources.getString(R.string.bottom_sheet_title_major))
        observeBottomSheet(mainViewModel, majorBottomSheetDialog)

        majorBottomSheetDialog.setCompleteListener {
            val selectedData = majorBottomSheetDialog.getSelectedData()
            if (selectedData != null) {
                val majorData = MajorSelectData(selectedData.id, selectedData.name)
                mainViewModel.setSelectedMajor(majorData)
            }

        }
    }
    //바텀시트 클릭
    private fun clickBottomSheet(){
//        val showMajorBottomSheetDialog = {
//            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)
//
//            // (학과 선택) 기본 선택값 적용 (MainActivity setDefaultMajor에서 관리)
//            majorBottomSheetDialog.setSelectedData(mainViewModel.selectedMajor.value!!.majorId )
//        }
//        binding.textClassroomTitle.setOnClickListener { showMajorBottomSheetDialog() }
//        binding.imgClassroomQuestionTitle.setOnClickListener { showMajorBottomSheetDialog() }
    }

    //타이틀 변경
    private fun changeTitle(){
//        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
//            binding.textClassroomTitle.text = it.majorName
//        }
    }

}