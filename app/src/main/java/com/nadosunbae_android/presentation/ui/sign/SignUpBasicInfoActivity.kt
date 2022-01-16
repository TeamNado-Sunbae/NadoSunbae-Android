package com.nadosunbae_android.presentation.ui.sign

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.ActivitySignUpBasicInfoBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.util.SignInCustomDialog
import java.util.regex.Pattern


class SignUpBasicInfoActivity :
    BaseActivity<ActivitySignUpBasicInfoBinding>(R.layout.activity_sign_up_basic_info) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSetting()
        nicknameTextWatcher()
        emailTextWatcher()
        pwTextWatcher()
        pwCheckTextWatcher()
        beforeBtnClick()
        closePage()
        nextPage()
    }

    //닉네임 textwatcher
    private fun nicknameTextWatcher() {
        binding.etSignupBasicinfoNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etSignupBasicinfoNickname.text.toString() == "") {
                    binding.imgSignupBasicinfoNicknameCancel.isSelected = false
                    binding.textSignupBasicinfoNicknameDuplication.isSelected = false
                } else {
                    binding.imgSignupBasicinfoNicknameCancel.isSelected = true
                    binding.textSignupBasicinfoNicknameDuplication.isSelected = true
                }

            }

        })

        binding.imgSignupBasicinfoNicknameCancel.setOnClickListener {
            binding.etSignupBasicinfoNickname.setText(null)
        }
    }


    //이메일 textwatcher
    private fun emailTextWatcher() {
        binding.etSignupBasicinfoEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                isEmailPattern()

                if (binding.etSignupBasicinfoEmail.text.toString() == "") {
                    binding.imgSignupBasicinfoEmailCancel.isSelected = false
                } else {
                    binding.imgSignupBasicinfoEmailCancel.isSelected = true
                }

            }

        })

        binding.imgSignupBasicinfoEmailCancel.setOnClickListener {
            binding.etSignupBasicinfoEmail.setText(null)
        }
    }


    private fun initSetting() {
//        //Test
//        binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.VISIBLE
//        binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.VISIBLE

        binding.textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
        binding.textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
    }


    //비밀번호 textwatcher
    private fun pwTextWatcher() {
        binding.etSignupBasicinfoPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

                isValidRegistrationPw()

                if (binding.etSignupBasicinfoPw.text.toString() == "") {
                    binding.imgSignupBasicinfoPwCancel.isSelected = false
                    binding.textSignupBasicinfoPwDuplicationOk.setVisibility(View.INVISIBLE)
                    binding.textSignupBasicinfoPwDuplicationNo.setVisibility(View.INVISIBLE)
                    binding.textSignupBasicinfoPwTitle.setTextColor(Color.parseColor("#94959E"))
                } else {
                    binding.imgSignupBasicinfoPwCancel.isSelected = true
                }

                if (binding.etSignupBasicinfoPw.text.toString() == binding.etSignupBasicinfoPwCheck.text.toString()) {
                    binding.textSignupBasicinfoPwDuplicationOk.visibility = View.VISIBLE
                    binding.textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
                } else {
                    binding.textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
                    binding.textSignupBasicinfoPwDuplicationNo.visibility = View.VISIBLE
                }
            }

        })

        binding.imgSignupBasicinfoPwCancel.setOnClickListener {
            binding.etSignupBasicinfoPw.setText(null)
        }
    }


    //비밀번호 확인 textwatcher
    private fun pwCheckTextWatcher() {
        binding.etSignupBasicinfoPwCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                //빈칸 체크 -> X 아이콘 selector
                if (binding.etSignupBasicinfoPwCheck.text.toString() == "") {
                    binding.imgSignupBasicinfoPwCheckCancel.isSelected = false
                    binding.textSignupBasicinfoPwDuplicationOk.setVisibility(View.INVISIBLE)
                    binding.textSignupBasicinfoPwDuplicationNo.setVisibility(View.INVISIBLE)
                } else {
                    binding.imgSignupBasicinfoPwCheckCancel.isSelected = true
                }

                //pw, pw확인 같은지 check
                if (binding.etSignupBasicinfoPw.text.toString() == binding.etSignupBasicinfoPwCheck.text.toString()) {
                    binding.textSignupBasicinfoPwDuplicationOk.visibility = View.VISIBLE
                    binding.textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
                } else {
                    binding.textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
                    binding.textSignupBasicinfoPwDuplicationNo.visibility = View.VISIBLE
                }
            }

        })

        binding.imgSignupBasicinfoPwCheckCancel.setOnClickListener {
            binding.etSignupBasicinfoPwCheck.setText(null)
        }
    }


    //이전버튼 클릭 이벤트
    private fun beforeBtnClick() {
        binding.clSignupBasicinfoMoveBefore.setOnClickListener {
            startActivity(Intent(this, SignUpMajorInfoActivity::class.java))
            finish()
        }
    }

    //비밀번호 정규식
    fun isValidRegistrationPw() {
        val password = binding.etSignupBasicinfoPw

        if (!Pattern.matches(
                "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}\$",
                password.text.toString()
            )
        ) {
            //binding.textSignupBasicinfoPwTitle.isSelected = true
            binding.textSignupBasicinfoPwTitle.setTextColor(Color.parseColor("#FF4C40"))
        } else {
            //binding.textSignupBasicinfoPwTitle.isSelected = false
            binding.textSignupBasicinfoPwTitle.setTextColor(Color.parseColor("#94959E"))
        }
    }

    //이메일 정규식
    private fun isEmailPattern() {
        val pattern = Patterns.EMAIL_ADDRESS
        binding.textSignupBasicinfoEmailDuplication.isSelected =
            pattern.matcher(binding.etSignupBasicinfoEmail.text).matches()
    }


    //상단 x누르면 로그인으로 이동
    fun closePage() {
        binding.imgSignupBasicinfoDelete.setOnClickListener {
            val dialog = SignInCustomDialog(this)
            dialog.showDialog()

            dialog.setOnClickListener(object : SignInCustomDialog.ButtonClickListener {
                override fun onClicked(num: () -> Unit) {
                    startActivity(Intent(this@SignUpBasicInfoActivity, SignInActivity::class.java))
                    finish()
                }

            })

        }
    }

    private fun nextPage() {
        //맞는 로직인지는 잘 모르겠는데 서버 들어오고 수정해야할 듯
//        if(binding.textSignupBasicinfoNicknameDuplicationNo.visibility == View.VISIBLE ||
//                    binding.textSignupBasicinfoEmailDuplicationNo.visibility == View.VISIBLE) {
//            binding.clSignupBasicinfoMoveNext.isSelected = false
//        }
//
//        else if(binding.textSignupBasicinfoNicknameDuplicationNo.visibility == View.VISIBLE ||
//            binding.textSignupBasicinfoPwDuplicationNo.visibility == View.VISIBLE) {
//            binding.clSignupBasicinfoMoveNext.isSelected = false
//        }
//
//        else if(binding.textSignupBasicinfoEmailDuplicationNo.visibility == View.VISIBLE ||
//            binding.textSignupBasicinfoPwDuplicationNo.visibility == View.VISIBLE){
//            binding.clSignupBasicinfoMoveNext.isSelected = false
//        }
//
//        else {
//            binding.clSignupBasicinfoMoveNext.isSelected = true
//        }
    }


}