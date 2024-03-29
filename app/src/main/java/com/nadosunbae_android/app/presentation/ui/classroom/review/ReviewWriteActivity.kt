package com.nadosunbae_android.app.presentation.ui.classroom.review

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityReviewWriteBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.classroom.review.adapter.ReviewSelectBackgroundAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.review.viewmodel.ReviewWriteViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.review.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ReviewWriteActivity :
    BaseActivity<ActivityReviewWriteBinding>(R.layout.activity_review_write) {

    private lateinit var reviewSelectBackgroundAdapter: ReviewSelectBackgroundAdapter
    private lateinit var reviewRequireTextWatcher: ReviewRequireTextWatcher

    private var mode = WriteMode.NEW
    private lateinit var modifyData: ReviewDetailData

    private val mainViewModel: MainViewModel by viewModels()

    private val reviewWriteViewModel: ReviewWriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        setTextLengthWatcher()
        initWriteMode()
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
        observeLoadingEnd()
        observeWriteFinish()
        loadBackgroundImage()
        setDropDownDefault()
    }

    override fun onBackPressed() {
        if (existInput()) {
            val confirm = confirmExit()
            confirm.observe(this) {
                if (confirm.value!!)
                    super.onBackPressed()
            }
        } else
            super.onBackPressed()
    }

    private fun initWriteMode() {
        mode = (intent.getSerializableExtra("mode") as WriteMode)

        // 수정하기 일 때 기존 데이터 불러오기
        if (mode == WriteMode.MODIFY) {
            // 기존 글 정보
            modifyData =
                intent.getParcelableExtra<ReviewDetailData>("modifyData") as ReviewDetailData

            // 불러온 데이터로 텍스트 상자 채워넣기
            with(binding) {
                etOneLine.setText(modifyData.oneLineReview)

                // content list -> 해당 되는 edit text의 value로
                for (c in modifyData.contentList) {
                    when (c.title) {
                        getString(R.string.review_pros_cons) -> etProsCons.editText.setText(c.content)
                        getString(R.string.review_curriculum) -> etCurriculum.editText.setText(c.content)
                        getString(R.string.review_recommend_lecture) -> etRecommendLecture.editText.setText(
                            c.content
                        )
                        getString(R.string.review_non_recommend_lecture) -> etNonRecommendLecture.editText.setText(
                            c.content
                        )
                        getString(R.string.review_career) -> etCareer.editText.setText(c.content)
                        getString(R.string.review_tip) -> etTip.editText.setText(c.content)
                    }
                }

                // 학과 선택 불가
                tvWriteChange.visibility = View.INVISIBLE
                tvWriteSelectedMajor.setTextColor(getColor(R.color.gray_3))
                // 고정적으로 선택된 학과 표시

                if (ReviewGlobals.selectedMajor != null)
                    tvWriteSelectedMajor.text = ReviewGlobals.selectedMajor!!.majorName


            }

        }

    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = reviewWriteViewModel
    }

    private fun initReviewSelectBackgroundAdapter() {
        reviewSelectBackgroundAdapter = ReviewSelectBackgroundAdapter()
        binding.rvSelectBackground.adapter = reviewSelectBackgroundAdapter

        // 배경선택 변경 여부 -> 수정 가능하도록
        reviewSelectBackgroundAdapter.backgroundSelected.observe(this) {
            applyInputValid()
        }
    }

    private fun initReviewRequireTextWatcher() {
        reviewRequireTextWatcher = ReviewRequireTextWatcher(binding)
    }


    private fun setOneLineTextWatcher() {
        binding.etOneLine.addTextChangedListener {
            if (it != null) {
                // 최대 글자수 체크
                if (it.length > ONE_LINE_MAX_LENGTH) {
                    val newStr = it.toString().substring(0, ONE_LINE_MAX_LENGTH)
                    binding.etOneLine.setText(newStr)
                }
                // 개행 문자 방지 (한줄평이므로)
                if (it.contains("\n")) {
                    val newStr = it.toString().replace("\n", "")
                    binding.etOneLine.setText(newStr)
                }
            }
        }
    }

    private fun setWriteRequireTextWatcher() {
        // 리뷰 작성 입력값을 검사하기 위한 TextWatcher
        with(binding) {
            etOneLine.addTextChangedListener(reviewRequireTextWatcher)
            etProsCons.editText.addTextChangedListener(reviewRequireTextWatcher)
            etCurriculum.editText.addTextChangedListener(reviewRequireTextWatcher)
            etRecommendLecture.editText.addTextChangedListener(reviewRequireTextWatcher)
            etNonRecommendLecture.editText.addTextChangedListener(reviewRequireTextWatcher)
            etCareer.editText.addTextChangedListener(reviewRequireTextWatcher)
            etTip.editText.addTextChangedListener(reviewRequireTextWatcher)
        }

    }

    private fun setTextLengthWatcher() {
        val textLengthWatcher = TextLengthWatcher()
        // 글자수 표시를 위한 TextWatcher
        with(binding) {
            etOneLine.addTextChangedListener(textLengthWatcher)
            etProsCons.editText.addTextChangedListener(textLengthWatcher)
            etCurriculum.editText.addTextChangedListener(textLengthWatcher)
            etRecommendLecture.editText.addTextChangedListener(textLengthWatcher)
            etNonRecommendLecture.editText.addTextChangedListener(textLengthWatcher)
            etCareer.editText.addTextChangedListener(textLengthWatcher)
            etTip.editText.addTextChangedListener(textLengthWatcher)
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
            } else
                finish()
        }

        // 학과 선택
        binding.clReviewWriteSelectMajor.setOnClickListener {

            if (mode != WriteMode.MODIFY) {

                val selectableList = mutableListOf<SelectableData>()

                // 본전공 추가
                val firstMajor = ReviewGlobals.firstMajor
                if (firstMajor != null && isValidMajor(firstMajor.majorId))
                    selectableList.add(
                        SelectableData(
                            firstMajor.majorId,
                            firstMajor.majorName,
                            false
                        )
                    )

                // 제2전공 추가
                val secondMajor = ReviewGlobals.secondMajor
                if (secondMajor != null && isValidMajor(secondMajor.majorId))
                    selectableList.add(
                        SelectableData(
                            secondMajor.majorId,
                            secondMajor.majorName,
                            false
                        )
                    )

                // 드롭다윤 메뉴 띄우기
                showCustomDropDown(
                    reviewWriteViewModel,
                    binding.clReviewWriteSelectMajor,
                    binding.clReviewWriteSelectMajor.width,
                    reviewWriteViewModel.dropDownSelected.value?.id ?: 1,
                    selectableList,
                    checkVisibility = true
                )
            }
        }

        // 작성 완료
        binding.btnWriteComplete.setOnClickListener {

            // 새로 작성
            var dialogTitle = getString(R.string.alert_write_review_title)
            var dialogComplete = getString(R.string.alert_write_review_complete)
            var dialogCancel = getString(R.string.alert_write_review_cancel)

            if (mode == WriteMode.MODIFY) {
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
                        WriteMode.NEW -> completeWriteNewReview()
                        WriteMode.MODIFY -> completeModifyReview()
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

            showLoading()
            reviewWriteViewModel.postReview(requestBody)

        }

    }


    // 후기글 수정
    private fun completeModifyReview() {

        val selectedBackgroundId = reviewSelectBackgroundAdapter.getSelectedBackgroundId()

        // null check
        if (selectedBackgroundId != null) {

            with(binding) {

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

                showLoading()
                reviewWriteViewModel.putReview(modifyData.postId, requestBody)
            }

        }

    }

    private fun observeBackgroundImageList() {
        reviewWriteViewModel.backgroundImageList.observe(this) {

            val backgroundList: List<BackgroundImageData>? =
                reviewWriteViewModel.backgroundImageList.value

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
                    WriteMode.NEW -> reviewSelectBackgroundAdapter.setSelectedBackground(
                        DEFAULT_BACKGROUND
                    )
                    WriteMode.MODIFY -> reviewSelectBackgroundAdapter.setSelectedBackground(modifyData.backgroundImageId)
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
        mainViewModel.majorList.flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)
            .onEach {
                val majorList = it
                // null check
                if (majorList != null) {
                    // 스피너에 적과용

                }
            }
            .launchIn(lifecycleScope)
        }



    private fun observeDropDownSelect() {
        reviewWriteViewModel.dropDownSelected.observe(this) {
            val selected = reviewWriteViewModel.dropDownSelected.value

            // null check
            if (selected != null) {
                mainViewModel.setSelectedMajor(MajorSelectData(selected.id, selected.name))
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

    private fun observeLoadingEnd() {
        reviewWriteViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    private fun observeWriteFinish() {
        reviewWriteViewModel.writeFinish.observe(this) {
            if (it == true)
                finish()
        }
    }

    private fun applyInputValid() {
        val validTextInput = reviewRequireTextWatcher.validTextInput.value
        val validBackground = reviewSelectBackgroundAdapter.getSelectedBackgroundId()

        // null check 및 배경 선택 여부 검사
        val valid =
            validTextInput != null && validTextInput && validBackground != null && reviewSelectBackgroundAdapter.getSelectedBackgroundId() != null
        binding.btnWriteComplete.isEnabled =
            if (mode == WriteMode.MODIFY) valid || (reviewSelectBackgroundAdapter.backgroundSelected.value == true && reviewSelectBackgroundAdapter.getSelectedBackgroundId() != null)
            else valid

    }

    private fun setDropDownDefault() {


        val firstMajor = ReviewGlobals.firstMajor
        if (firstMajor != null)
            reviewWriteViewModel.dropDownSelected.value =
                SelectableData(firstMajor.majorId, firstMajor.majorName, true)

    }

    private fun confirmExit(): MutableLiveData<Boolean> {
        var title = getString(R.string.alert_cancel_write_title)
        if (mode == WriteMode.MODIFY)
            title = getString(R.string.alert_cancel_edit_title)

        val confirm = MutableLiveData<Boolean>()
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                title,
                getString(R.string.alert_cancel_write_complete),
                getString(R.string.alert_cancel_write_cancel)
            ),
            complete = {
                val paramValue = if(ReviewGlobals.isReviewed){
                    "review_additional"
                }else{
                    "review_new"
                }
                FirebaseAnalyticsUtil.firebaseLog("review_write","type",paramValue)
                FirebaseAnalyticsUtil.setReviewProcess("review_upload")
                confirm.value = false
            },
            cancel = {
                FirebaseAnalyticsUtil.setReviewProcess("review_exit")
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

    private fun loadBackgroundImage() {
        /*  api 배포 중단됨
        showLoading()
        reviewWriteViewModel.getBackgroundImageList()

         */
        val imageList = mutableListOf<BackgroundImageData>()
        for (i in 6..12) {
            imageList.add(BackgroundImageData(i, ""))
        }

        reviewWriteViewModel.setBackgroundImageList(imageList)
    }


    inner class TextLengthWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            with(reviewWriteViewModel) {
                with(binding) {
                    oneLineLength.value = etOneLine.text.length
                    prosConsLength.value = etProsCons.editText.text.length
                    curriculumLength.value = etCurriculum.editText.text.length
                    recommendLectureLength.value = etRecommendLecture.editText.text.length
                    nonRecommendLectureLength.value = etNonRecommendLecture.editText.text.length
                    careerLength.value = etCareer.editText.text.length
                    tipLength.value = etTip.editText.text.length
                }
            }
        }
    }

    companion object {
        const val ONE_LINE_MAX_LENGTH = 40

        // 학과 - 미진입
        const val NOT_ENTERED = 1

        // 학과 - 정보없음
        const val NO_INFORMATION = 127

        // default 배경 id
        const val DEFAULT_BACKGROUND = 6
    }

    enum class WriteMode(val num: Int) {
        NEW(1), // 새로 작성하기
        MODIFY(2)   // 수정하기
    }

}