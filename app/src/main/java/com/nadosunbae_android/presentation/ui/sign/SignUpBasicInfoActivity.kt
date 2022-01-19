package com.nadosunbae_android.presentation.ui.sign

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.request.sign.RequestSignEmail
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.databinding.ActivitySignUpBasicInfoBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.util.SignInCustomDialog
import java.util.regex.Pattern


class SignUpBasicInfoActivity :
    BaseActivity<ActivitySignUpBasicInfoBinding>(R.layout.activity_sign_up_basic_info) {
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignUpBasicInfoViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nicknameTextWatcher()
        emailTextWatcher()
        pwTextWatcher()
        pwCheckTextWatcher()
        beforeBtnClick()
        closePage()
        nextPage()

//        nicknameDuplication()
    }

    private fun nicknameDuplication() {
        //닉네임 중복 체크 서버 통신
        signUpBasicInfoViewModel.nickName.observe(this){
            signUpBasicInfoViewModel.nickNameDuplication(RequestSignNickname(it))
        }

        signUpBasicInfoViewModel.nickNameDuplication.observe(this){
            if(it){
                binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.VISIBLE
                binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
            } else {
                binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
                binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.VISIBLE

            }
        }
    }


    private fun emailDuplication() {
        //이메일 중복 체크 서버 통신
        signUpBasicInfoViewModel.email.observe(this){
            signUpBasicInfoViewModel.emailDuplication(RequestSignEmail(it))
            Log.d("1111", "123123")
        }

        signUpBasicInfoViewModel.emailDuplication.observe(this){
            Log.d("1111", "111111")
            if(it){
                binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.VISIBLE
                binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
            } else {
                binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
                binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.VISIBLE

            }
        }
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

                    binding.textSignupBasicinfoNicknameDuplication.setOnClickListener {
                        signUpBasicInfoViewModel.nickName.value = p0.toString()
                        nicknameDuplication()
                    }
                }

            }

        })

        binding.imgSignupBasicinfoNicknameCancel.setOnClickListener {
            binding.etSignupBasicinfoNickname.setText(null)
            binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
            binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
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

                    binding.textSignupBasicinfoEmailDuplication.setOnClickListener {
                        signUpBasicInfoViewModel.email.value = p0.toString()
                        emailDuplication()
                    }
                }

            }

        })

        binding.imgSignupBasicinfoEmailCancel.setOnClickListener {
            binding.etSignupBasicinfoEmail.setText(null)
            binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
            binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
        }
    }


    private fun initSetting() {
        if(binding.etSignupBasicinfoPw.text.toString() == "" || binding.etSignupBasicinfoPwCheck.text.toString()=="") {
            binding.textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
            binding.textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
        }
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
                initSetting()

                if (binding.etSignupBasicinfoPw.text.toString() == "") {
                    binding.imgSignupBasicinfoPwCancel.isSelected = false
                    binding.textSignupBasicinfoPwTitle.setTextColor(Color.parseColor("#94959E"))
                } else {
                    binding.imgSignupBasicinfoPwCancel.isSelected = true
                    if (binding.etSignupBasicinfoPw.text.toString() == binding.etSignupBasicinfoPwCheck.text.toString()) {
                        binding.textSignupBasicinfoPwDuplicationOk.visibility = View.VISIBLE
                        binding.textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
                    } else {
                        binding.textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
                        binding.textSignupBasicinfoPwDuplicationNo.visibility = View.VISIBLE
                    }
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
                initSetting()
                if (binding.etSignupBasicinfoPwCheck.text.toString() == "") {
                    binding.imgSignupBasicinfoPwCheckCancel.isSelected = false
                } else {
                    binding.imgSignupBasicinfoPwCheckCancel.isSelected = true
                    //pw, pw확인 같은지 check
                    if (binding.etSignupBasicinfoPw.text.toString() == binding.etSignupBasicinfoPwCheck.text.toString()) {
                        binding.textSignupBasicinfoPwDuplicationOk.visibility = View.VISIBLE
                        binding.textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
                    } else {
                        binding.textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
                        binding.textSignupBasicinfoPwDuplicationNo.visibility = View.VISIBLE
                    }
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
            binding.textSignupBasicinfoPwTitle.setTextColor(Color.parseColor("#FF4C40"))
        } else {
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
        binding.clSignupBasicinfoMoveNext.setOnClickListener {
            startActivity(Intent(this@SignUpBasicInfoActivity, SignUpFinishActivity::class.java))
            finish()
        }
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