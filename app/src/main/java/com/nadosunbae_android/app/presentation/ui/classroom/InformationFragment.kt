package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentInformationBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomInfoMainAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.InformationViewModel
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.model.main.SelectableData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {
    private lateinit var classRoomInfoMainAdapter : ClassRoomInfoMainAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private val informationViewModel : InformationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initQuestionSort()
        initInfoMain()
        observeArray()
        questionSort()
        observeLoadingEnd()
        submitAnalytics()
    }


    //로딩 종료
    private fun observeLoadingEnd() {
        mainViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.getClassRoomMain(2,mainViewModel.selectedMajor.value!!.majorId, "recent")
    }

    private fun initInfoMain(){
        val userId = mainViewModel.userId.value ?: 0
        classRoomInfoMainAdapter = ClassRoomInfoMainAdapter(userId)
        binding.rcClassroomInfo.adapter = classRoomInfoMainAdapter
       mainViewModel.classRoomMain.observe(viewLifecycleOwner){
           Timber.d("classRoomInfo: $it")
           if(it.isEmpty()){
               mainViewModel.classRoomInfoEmpty.value = EMPTY
               binding.rcClassroomInfo.visibility = View.GONE
               binding.btnClassroomInfoArray.visibility = View.GONE
           }else{
               mainViewModel.classRoomInfoEmpty.value = NOTEMPTY
               binding.rcClassroomInfo.visibility = View.VISIBLE
               binding.btnClassroomInfoArray.visibility = View.VISIBLE
           }
            Timber.d("classRoomInfo empty : ${mainViewModel.classRoomInfoEmpty.value}")
           classRoomInfoMainAdapter.setQuestionMain(it as MutableList<ClassRoomData>)
       }
    }

    //최신순, 도움순 정렬
    private fun questionSort(){
        binding.btnClassroomInfoArray.setOnClickListener {
            val questionDropDownList = mutableListOf<SelectableData>(
                SelectableData(1, getString(R.string.review_latest_order), true),
                SelectableData(2, getString(R.string.review_likes_order), false)
            )


            showCustomDropDown(informationViewModel, binding.btnClassroomInfoArray, 160f.dpToPx, null, -1 * 16f.dpToPx, null, true,informationViewModel.dropDownSelected.value!!.id, questionDropDownList)

        }
    }
    //첫 화면 최신순
    private fun initQuestionSort(){
        informationViewModel.dropDownSelected.value = SelectableData(1,"최신순",true)
    }



    //정보 데이터 받기
    private fun infoServer(sort : String){
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            showLoading()
            mainViewModel.getClassRoomMain(2,it.majorId, sort)
        }
    }


    //최신순, 도움순 변경
    private fun observeArray(){
        informationViewModel.dropDownSelected.observe(viewLifecycleOwner) {
            val sortData = informationViewModel.dropDownSelected.value
            if (sortData != null) {
                if (sortData.id == 1)
                    binding.btnClassroomInfoArray.text = getString(R.string.review_latest_order)
                else
                    binding.btnClassroomInfoArray.text = getString(R.string.review_likes_order)
            }
            var sort = "recent"
            if (informationViewModel.dropDownSelected.value != null) {
                sort = if (informationViewModel.dropDownSelected.value!!.id == 1)
                    "recent"
                else
                    "like"
            }

            infoServer(sort)
        }
    }

    private fun submitAnalytics() {
        FirebaseAnalyticsUtil.selectTab(FirebaseAnalyticsUtil.Tab.REVIEW)
    }

    companion object{
        const val write = 0
        const val update = 1

        const val EMPTY = 0
        const val NOTEMPTY = 1
    }
}