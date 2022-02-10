package com.nadosunbae_android.app.presentation.ui.review

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityReviewWriteBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.review.adapter.ReviewSelectBackgroundAdapter
import com.nadosunbae_android.app.presentation.ui.review.viewmodel.ReviewWriteViewModel
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.main.MajorData
import com.nadosunbae_android.data.model.response.sign.SelectableData
import com.nadosunbae_android.domain.model.review.BackgroundImageData
import com.nadosunbae_android.domain.model.review.ReviewDetailData
import com.nadosunbae_android.domain.model.review.ReviewEditItem
import com.nadosunbae_android.domain.model.review.ReviewWriteItem
import com.nadosunbae_android.data.model.ui.MajorKeyData
import com.nadosunbae_android.data.model.ui.SelectBackgroundBoxData
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewWriteActivity : BaseActivity<ActivityReviewWriteBinding>(R.layout.activity_review_write) {

    private lateinit var reviewSelectBackgroundAdapter: ReviewSelectBackgroundAdapter
    private lateinit var reviewRequireTextWatcher: ReviewRequireTextWatcher

    private var mode = MODE_NEW
    private lateinit var modifyData: ReviewDetailData

    private val mainViewModel: MainViewModel by viewModel()

    private val reviewWriteViewModel: ReviewWriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWriteMode()
        initBinding()
        initReviewSelectBackgroundAdapter()
        initReviewRequireTextWatcher()
        setOneLineTextWatcher()
        setWriteRequireTextWatcher()
        setOnClickListener()
        observeBackgroundImageList()
        observeValidInput()
        observeMajorSelected()
        observeMajorList()
        observeDropDownSelect()
        loadBackgroundImage()
        loadMajorList()
        setDropDownDefault()

    }

    override fun onBackPressed() {
        if (existInput()) {
            val confirm = confirmExit()
            confirm.observe(this) {
                if (confirm.value!!)
                    super.onBackPressed()
            }
        }
        else
            super.onBackPressed()
    }

    private fun initWriteMode() {
        mode = intent.getIntExtra("mode", MODE_NEW)

        // 수정하기 일 때 기존 데이터 불러오기
        if (mode == MODE_MODIFY) {
            modifyData = intent.getSerializableExtra("modifyData") as ReviewDetailData

            // 불러온 데이터로 텍스트 상자 채워넣기
            with (binding) {
                etOneLine.setText(modifyData.oneLineReview)

                // content list -> 해당 되는 edit text의 value로
                for (c in modifyData.contentList) {
                    when (c.title) {
                        getString(R.string.review_pros_cons) -> etProsCons.editText.setText(c.content)
                        getString(R.string.review_curriculum) -> etCurriculum.editText.setText(c.content)
                        getString(R.string.review_recommend_lecture) -> etRecommendLecture.editText.setText(c.content)
                        getString(R.string.review_non_recommend_lecture) -> etNonRecommendLecture.editText.setText(c.content)
                        getString(R.string.review_career) -> etCareer.editText.setText(c.content)
                        getString(R.string.review_tip) -> etTip.editText.setText(c.content)
                    }
                }

                // 학과 선택 숨기기
                clReviewWriteSelectMajorBox.visibility = View.GONE

            }

        }

    }

    private fun initBinding() {
        binding.lifecycleOwner = this
    }

    private fun initReviewSelectBackgroundAdapter() {
        reviewSelectBackgroundAdapter = ReviewSelectBackgroundAdapter()
        binding.rvSelectBackground.adapter = reviewSelectBackgroundAdapter
    }

    private fun initReviewRequireTextWatcher() {
        reviewRequireTextWatcher = ReviewRequireTextWatcher(binding)
    }


    private fun setOneLineTextWatcher() {
        binding.etOneLine.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                if (s != null) {

                    // 최대 글자수 체크
                    if (s.length > ONE_LINE_MAX_LENGTH) {
                        val newStr = s.toString().substring(0, ONE_LINE_MAX_LENGTH)
                        binding.etOneLine.setText(newStr)
                    }

                    // 개행 문자 방지 (한줄평이므로)
                    if (s.contains("\n")) {
                        val newStr = s.toString().replace("\n", "")
                        binding.etOneLine.setText(newStr)
                    }
                }


            }

        })
    }

    private fun setWriteRequireTextWatcher() {
        // 리뷰 작성 입력값을 검사하기 위한 TextWatcher
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

            if (existInput()) {

                val confirm = confirmExit()
                confirm.observe(this) {
                    if (confirm.value!!)
                        finish()
                }
            }
            else
                finish()
        }

        // 학과 선택
        binding.clReviewWriteSelectMajor.setOnClickListener {

            val selectableList = mutableListOf<SelectableData>()

            // 본전공 추가
            val firstMajor = ReviewGlobals.firstMajor
            if (firstMajor != null && isValidMajor(firstMajor.data.majorList)
                selectableList.add(SelectableData(firstMajor.majorId, firstMajor.majorName, false))

            // 제2전공 추가
            val secondMajor = ReviewGlobals.secondMajor
            if (secondMajor != null && isValidMajor(secondMajor.majorId))
                selectableList.add(SelectableData(secondMajor.majorId, secondMajor.majorName, false))

            // 드롭다윤 메뉴 띄우기
            showCustomDropDown(reviewWriteViewModel, binding.clReviewWriteSelectMajor,
                binding.clReviewWriteSelectMajor.width, reviewWriteViewModel.dropDownSelected.value!!.id,
                selectableList)
        }

        // 작성 완료
        binding.btnWriteComplete.setOnClickListener {

            // 새로 작성
            var dialogTitle = getString(R.string.alert_write_review_title)
            var dialogComplete = getString(R.string.alert_write_review_complete)
            var dialogCancel = getString(R.string.alert_write_review_cancel)

            if (mode == MODE_MODIFY) {
                dialogTitle = getString(R.string.alert_modify_review_title)
                dialogComplete = getString(R.string.alert_modify_review_complete)
                dialogCancel = getString(R.string.alert_modify_review_cancel)
            }

            CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                dialogTitle,
                dialogComplete,
                dialogCancel
            ),
            complete = {
                when (mode) {
                    MODE_NEW -> completeWriteNewReview()
                    MODE_MODIFY -> completeModifyReview()
                }
            },
            cancel = {

            }
            )


        }

    }

    // 후기글 새로 등록
    private fun completeWriteNewReview() {

        val selectedMajor = reviewWriteViewModel.dropDownSelected.value
        val selectedBackgroundId = reviewSelectBackgroundAdapter.getSelectedBackgroundId()

        // null check
        if (selectedMajor != null && selectedBackgroundId != null) {


            val requestBody = ReviewWriteItem(
                selectedMajor.id,
                reviewSelectBackgroundAdapter.getSelectedBackgroundId()!!,
                binding.etOneLine.text.toString(),
                binding.etProsCons.editText.text.toString(),
                binding.etCurriculum.editText.text.toString(),
                binding.etRecommendLecture.editText.text.toString(),
                binding.etNonRecommendLecture.editText.text.toString(),
                binding.etCareer.editText.text.toString(),
                binding.etTip.editText.text.toString()
            )

            reviewWriteViewModel.postReview(requestBody)


            }

        // 액티비티 종료
        finish()

    }

    // 후기글 수정
    private fun completeModifyReview() {

        val selectedBackgroundId = reviewSelectBackgroundAdapter.getSelectedBackgroundId()

        // null check
        if (selectedBackgroundId != null) {

            with (binding) {

                val requestBody = ReviewEditItem(
                    selectedBackgroundId,
                    etOneLine.text.toString(),
                    etProsCons.editText.text.toString(),
                    etCurriculum.editText.text.toString(),
                    etCareer.editText.text.toString(),
                    etRecommendLecture.editText.text.toString(),
                    etNonRecommendLecture.editText.text.toString(),
                    etTip.editText.text.toString()
                )

                reviewWriteViewModel.putReview(modifyData.postId, requestBody)
            }

        }

        finish()
    }

    private fun observeBackgroundImageList() {
        reviewWriteViewModel.backgroundImageList.observe(this) {

            val backgroundList: List<BackgroundImageData>? = reviewWriteViewModel.backgroundImageList.value

            // null check
            if (backgroundList != null) {
                val dataList = reviewSelectBackgroundAdapter.dataList

                // 갱신을 위해 기존 data clear
                dataList.clear()
                // response data add
                for (bg in backgroundList) {
                    dataList.add(SelectBackgroundBoxData(bg.imageId, bg.imageUrl, false))
                }

                // default 설정
                when (mode) {
                    MODE_NEW -> reviewSelectBackgroundAdapter.setSelectedBackground(DEFAULT_BACKGROUND)
                    MODE_MODIFY -> reviewSelectBackgroundAdapter.setSelectedBackground(modifyData.backgroundImageId)
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
            val majorList: List<MajorData>? = mainViewModel.majorList.value

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
                mainViewModel.setSelectedMajor(MajorKeyData(selected.id, selected.name))
            }

        }
    }

    private fun observeValidInput() {

        reviewRequireTextWatcher.validTextInput.observe(this) {
            applyInputValid()
        }

        reviewSelectBackgroundAdapter.mSelectedPos.observe(this) {
            applyInputValid()
        }

    }

    private fun applyInputValid() {
        val validTextInput = reviewRequireTextWatcher.validTextInput.value
        val validBackground = reviewSelectBackgroundAdapter.getSelectedBackgroundId()

        // null check 및 배경 선택 여부 검사
        binding.btnWriteComplete.isEnabled = validTextInput != null && validTextInput && validBackground != null

    }

    private fun setDropDownDefault() {


        val firstMajor = ReviewGlobals.firstMajor
        if (firstMajor != null)
           reviewWriteViewModel.dropDownSelected.value = SelectableData(firstMajor.majorId, firstMajor.majorName, true)

    }

    private fun confirmExit(): MutableLiveData<Boolean> {

        val confirm = MutableLiveData<Boolean>()
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                getString(R.string.alert_cancel_write_title),
                getString(R.string.alert_cancel_write_complete),
                getString(R.string.alert_cancel_write_cancel)
            ),
            complete = {
                confirm.value = false
            },
            cancel = {
                confirm.value = true
            }
        )
        return confirm
    }

    private fun existInput() = binding.etOneLine.text.isNotEmpty()
            || binding.etProsCons.editText.text.isNotEmpty()
            || binding.etCareer.editText.text.isNotEmpty()
            || binding.etCurriculum.editText.text.isNotEmpty()
            || binding.etRecommendLecture.editText.text.isNotEmpty()
            || binding.etNonRecommendLecture.editText.text.isNotEmpty()
            || binding.etTip.editText.text.isNotEmpty()

    private fun isValidMajor(majorId: Int) =
            majorId != NOT_ENTERED && majorId != NO_INFORMATION

    private fun loadBackgroundImage() = reviewWriteViewModel.getBackgroundImageList()

    private fun loadMajorList() = mainViewModel.getMajorList(1)

    companion object {
        const val ONE_LINE_MAX_LENGTH = 40

        // 학과 - 미진입
        const val NOT_ENTERED = 126
        // 학과 - 정보없음
        const val NO_INFORMATION = 127

        // 새로 작성하기
        const val MODE_NEW = 1
        // 기존 후기 수정하기
        const val MODE_MODIFY = 2

        // default 배경 id
        const val DEFAULT_BACKGROUND = 6
    }

}