package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentModifyImgBottomSheetBinding
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ModifyImgBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var _binding: FragmentModifyImgBottomSheetBinding
    val binding get() = _binding!!

    private val myPageViewModel: MyPageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentModifyImgBottomSheetBinding.inflate(inflater, container, false).run {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_modify_img_bottom_sheet,
            container,
            false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e("TEST ; ${myPageViewModel.selectImgId}")
        with(binding) {
            makeRadioButton2(
                imgBottomsheet1,
                imgBottomsheet2,
                imgBottomsheet3,
                imgBottomsheet4,
                imgBottomsheet5
            )
        }
        initSetting()
        cancelListener()
    }

    //X버튼 클릭 리스너
    private fun cancelListener() {
        binding.btnBottomsheetCancel.setOnClickListener { dismiss() }
    }

    //라디오 버튼 전환
    private fun makeRadioButton2(view1: View, view2: View, view3: View, view4: View, view5: View) =
        with(binding) {
            view1.setOnClickListener {
                myPageViewModel.selectImgId.value = 1

                ivChecked1.visibility = View.VISIBLE
                ivChecked2.visibility = View.INVISIBLE
                ivChecked3.visibility = View.INVISIBLE
                ivChecked4.visibility = View.INVISIBLE
                ivChecked5.visibility = View.INVISIBLE

                Timber.e("클릭 ?")
            }
            view2.setOnClickListener {
                myPageViewModel.selectImgId.value = 2

                ivChecked1.visibility = View.INVISIBLE
                ivChecked2.visibility = View.VISIBLE
                ivChecked3.visibility = View.INVISIBLE
                ivChecked4.visibility = View.INVISIBLE
                ivChecked5.visibility = View.INVISIBLE

            }
            view3.setOnClickListener {
                myPageViewModel.selectImgId.value = 3

                ivChecked1.visibility = View.INVISIBLE
                ivChecked2.visibility = View.INVISIBLE
                ivChecked3.visibility = View.VISIBLE
                ivChecked4.visibility = View.INVISIBLE
                ivChecked5.visibility = View.INVISIBLE

            }
            view4.setOnClickListener {
                myPageViewModel.selectImgId.value = 4
                ivChecked1.visibility = View.INVISIBLE
                ivChecked2.visibility = View.INVISIBLE
                ivChecked3.visibility = View.INVISIBLE
                ivChecked4.visibility = View.VISIBLE
                ivChecked5.visibility = View.INVISIBLE

            }
            view5.setOnClickListener {
                myPageViewModel.selectImgId.value = 5
                ivChecked1.visibility = View.INVISIBLE
                ivChecked2.visibility = View.INVISIBLE
                ivChecked3.visibility = View.INVISIBLE
                ivChecked4.visibility = View.INVISIBLE
                ivChecked5.visibility = View.VISIBLE

            }

        }

    //처음에 이미지 선택되어 나오게
    private fun initSetting() = with(binding) {
        when(myPageViewModel.selectImgId.value) {
            1 -> {
                ivChecked1.visibility = View.VISIBLE
                ivChecked2.visibility = View.INVISIBLE
                ivChecked3.visibility = View.INVISIBLE
                ivChecked4.visibility = View.INVISIBLE
                ivChecked5.visibility = View.INVISIBLE
            }
            2-> {
                ivChecked1.visibility = View.INVISIBLE
                ivChecked2.visibility = View.VISIBLE
                ivChecked3.visibility = View.INVISIBLE
                ivChecked4.visibility = View.INVISIBLE
                ivChecked5.visibility = View.INVISIBLE
            }
            3-> {
                ivChecked1.visibility = View.INVISIBLE
                ivChecked2.visibility = View.INVISIBLE
                ivChecked3.visibility = View.VISIBLE
                ivChecked4.visibility = View.INVISIBLE
                ivChecked5.visibility = View.INVISIBLE
            }
            4-> {
                ivChecked1.visibility = View.INVISIBLE
                ivChecked2.visibility = View.INVISIBLE
                ivChecked3.visibility = View.INVISIBLE
                ivChecked4.visibility = View.VISIBLE
                ivChecked5.visibility = View.INVISIBLE
            }
            5 -> {
                ivChecked1.visibility = View.INVISIBLE
                ivChecked2.visibility = View.INVISIBLE
                ivChecked3.visibility = View.INVISIBLE
                ivChecked4.visibility = View.INVISIBLE
                ivChecked5.visibility = View.VISIBLE
            }
        }
    }
}