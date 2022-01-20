package com.nadosunbae_android.presentation.ui.review

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.main.ResponseMajorListData
import com.nadosunbae_android.data.model.response.review.ResponseBackgroundImageListData
import com.nadosunbae_android.data.model.response.sign.SelectableData
import com.nadosunbae_android.data.model.ui.MajorData
import com.nadosunbae_android.data.model.ui.SelectBackgroundBoxData
import com.nadosunbae_android.databinding.ActivityReviewWriteBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewSelectBackgroundAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewWriteViewModel
import com.nadosunbae_android.util.showCustomDropDown

class ReviewWriteActivity : BaseActivity<ActivityReviewWriteBinding>(R.layout.activity_review_write) {

    private lateinit var reviewSelectBackgroundAdapter: ReviewSelectBackgroundAdapter

    private val mainViewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }

        }
    }

    private val reviewWriteViewModel: ReviewWriteViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ReviewWriteViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initReviewSelectBackgroundAdapter()
        setOneLineTextWatcher()
        setWriteRequireTextWatcher()
        setOnClickListener()
        observeBackgroundImageList()
        observeMajorSelected()
        observeMajorList()
        observeDropDownSelect()
        loadBackgroundImage()
        loadMajorList()
        getMajorFromIntent()

    }

    private fun initBinding() {
        binding.lifecycleOwner = this
    }

    private fun initReviewSelectBackgroundAdapter() {
        reviewSelectBackgroundAdapter = ReviewSelectBackgroundAdapter()
        binding.rvSelectBackground.adapter = reviewSelectBackgroundAdapter
    }

    private fun setOneLineTextWatcher() {
        binding.etOneLine.addTextChangedListener(object : TextWatcher {

            private var prevString = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                prevString = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                // 최대 글자수 체크 및 개행 문자 방지 (한줄평이므로)
                if (s?.length!! > ONE_LINE_MAX_LENGTH || binding.etOneLine.text.contains("\n")) {
                    binding.etOneLine.setText(prevString)
                    binding.etOneLine.setSelection(prevString.length - 1)
                }

            }

        })
    }

    private fun setWriteRequireTextWatcher() {
        // 리뷰 작성 입력값을 검사하기 위한 TextWatcher
        val reviewRequireTextWatcher = ReviewRequireTextWatcher(binding)
        with (binding) {
            etOneLine.addTextChangedListener(reviewRequireTextWatcher)
            etProsCons.editText.addTextChangedListener(reviewRequireTextWatcher)
            etCurriculum.editText.addTextChangedListener(reviewRequireTextWatcher)
            etRecommendLecture.editText.addTextChangedListener(reviewRequireTextWatcher)
            etNonRecommendLecture.editText.addTextChangedListener(reviewRequireTextWatcher)
            etCareer.editText.addTextChangedListener(reviewRequireTextWatcher)
            etTip.editText.addTextChangedListener(reviewRequireTextWatcher)
        }
    }

    private fun setOnClickListener() {
        // 닫기 버튼
        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.clReviewWriteSelectMajor.setOnClickListener {
            // 학과 선택
            val selectableList = mutableListOf<SelectableData>()

            // 본전공 추가
            val firstMajor = mainViewModel.firstMajor.value
            if (firstMajor != null)
                selectableList.add(SelectableData(firstMajor.majorId, firstMajor.majorName, false))

            // 제2전공 추가
            val secondMajor = mainViewModel.secondMajor.value
            if (secondMajor != null)
                selectableList.add(SelectableData(secondMajor.majorId, secondMajor.majorName, false))

            // 드롭다윤 메뉴 띄우기
            showCustomDropDown(reviewWriteViewModel, binding.clReviewWriteSelectMajor, binding.clReviewWriteSelectMajor.width, mainViewModel.selectedMajor.value!!.majorId, selectableList)
        }

    }

    private fun observeBackgroundImageList() {
        reviewWriteViewModel.backgroundImageList.observe(this) {

            val responseBackgroundList: ResponseBackgroundImageListData? = reviewWriteViewModel.backgroundImageList.value

            // null check
            if (responseBackgroundList != null) {
                val dataList = reviewSelectBackgroundAdapter.dataList

                // 갱신을 위해 기존 data clear
                dataList.clear()
                // response data add
                for (bg in responseBackgroundList.data.backgroundImageList) {
                    dataList.add(SelectBackgroundBoxData(bg.imageId, bg.imageUrl, false))
                }
                reviewSelectBackgroundAdapter.notifyDataSetChanged()

            }
        }
    }

    private fun observeMajorSelected() {
        mainViewModel.selectedMajor.observe(this) {
            val selectedMajor = mainViewModel.selectedMajor.value

            // null check
            if (selectedMajor != null) {
                binding.tvWriteSelectedMajor.text = selectedMajor.majorName
            }
        }
    }

    private fun observeMajorList() {
        mainViewModel.majorList.observe(this) {
            val majorList: ResponseMajorListData? = mainViewModel.majorList.value

            // null check
            if (majorList != null) {
                // 스피너에 적용

            }

        }
    }

    private fun observeDropDownSelect() {
        reviewWriteViewModel.dropDownSelected.observe(this) {
            val selected = reviewWriteViewModel.dropDownSelected.value

            // null check
            if (selected != null) {
                mainViewModel.setSelectedMajor(MajorData(selected.id, selected.name))
            }
            
        }
    }

    private fun getMajorFromIntent() {
        val selectedMajor = intent.getSerializableExtra("selectedMajor") as MajorData?
        val firstMajor = intent.getSerializableExtra("firstMajor") as MajorData?
        val secondMajor = intent.getSerializableExtra("secondMajor") as MajorData?

        // null check
        if (selectedMajor != null)
            mainViewModel.setSelectedMajor(selectedMajor)

        if (firstMajor != null)
            mainViewModel.setFirstMajor(firstMajor)

        if (secondMajor != null)
            mainViewModel.setSecondMajor(secondMajor)
    }

    private fun loadBackgroundImage() = reviewWriteViewModel.getBackgroundImageList()

    private fun loadMajorList() = mainViewModel.getMajorList(1)

    companion object {
        const val ONE_LINE_MAX_LENGTH = 40
    }

}