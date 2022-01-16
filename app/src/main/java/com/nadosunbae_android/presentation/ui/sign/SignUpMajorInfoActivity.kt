package com.nadosunbae_android.presentation.ui.sign


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.nadosunbae_android.presentation.ui.sign.adapter.SpinnerAdapter
import com.nadosunbae_android.util.PixelRatio
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.ActivitySignUpMajorInfoBinding
import com.nadosunbae_android.databinding.SpinnerItemBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.sign.adapter.MajorSelectAdapter
import com.nadosunbae_android.util.SignInCustomDialog
import kotlinx.android.synthetic.main.spinner_item.view.*


class SignUpMajorInfoActivity :
    BaseActivity<ActivitySignUpMajorInfoBinding>(R.layout.activity_sign_up_major_info) {
    private lateinit var customBottomSheetDialog: CustomBottomSheetDialog
    private lateinit var majorSelectAdapter: MajorSelectAdapter
    private lateinit var spinnerItemBinding: SpinnerItemBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        closePage()
        moveBeforePage()
        nextBtnActivate()
        onClickbottomSheetUniv()

        firstMajorPeriod()
        setupSpinner()
        setupSpinnerHandler()
        textUpdate()
    }


    //X버튼 클릭 리스너
    private fun closePage() {
        binding.imgSignupMajorinfoDelete.setOnClickListener {
            val dialog = SignInCustomDialog(this)
            dialog.showDialog()

            dialog.setOnClickListener(object : SignInCustomDialog.ButtonClickListener {
                override fun onClicked(num: () -> Unit) {
                    startActivity(Intent(this@SignUpMajorInfoActivity, SignInActivity::class.java))
                    finish()
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
            startActivity(Intent(this, SignUpBasicInfoActivity::class.java))
        }
    }

    private fun onClickbottomSheetUniv() {
        binding.clSignupMajorInfoUniv.setOnClickListener {

            val bottomSheetFragment = CustomBottomSheetDialog()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

        }
    }

    private fun firstMajorPeriod() {
        binding.clSignupMajorInfoMajorTime.setOnClickListener {
            val bottomSheetFragment = CustomBottomSheetDialog()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun univSelection() {

    }

    private fun textUpdate() {

    }


    private fun setupSpinner() {
        val years = resources.getStringArray(R.array.spinner_univ)
        val list = listOf("고려대학교", "타 대학은 현재 준비중입니다")
        val spinnerAdapter = SpinnerAdapter(this, R.layout.spinner_item, list)
        binding.spinnerSignupMajorinfoUniv.adapter = spinnerAdapter
        binding.spinnerSignupMajorinfoUniv.dropDownVerticalOffset = PixelRatio().dpToPx(52)


        //우선은 남겨놔주세여..
//        object : ArrayAdapter<String>(this, R.layout.spinner_item,R.id.tv_spinner, years){
//            @SuppressLint("ResourceAsColor")
//            override fun getDropDownView(
//                position: Int,
//                convertView: View?,
//                parent: ViewGroup
//            ): View {
//                val view: ConstraintLayout = super.getDropDownView(position, convertView, parent) as ConstraintLayout
//
//
//                if (position == 1){
//                    view.tv_spinner.isSelected = true
//                } else {
//                    view.img_spinner_check.isSelected = true
//                }
//
//                return view
//            }
//
//            override fun isEnabled(position: Int): Boolean {
//                return position == 0
//
//            }
//        }
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