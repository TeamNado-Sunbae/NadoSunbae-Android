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
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.request.sign.RequestSignEmail
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.request.sign.RequestSignUp
import com.nadosunbae_android.databinding.ActivitySignUpBasicInfoBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.util.SignInCustomDialog
import java.util.regex.Pattern


class SignUpBasicInfoActivity :
    BaseActivity<ActivitySignUpBasicInfoBinding>(R.layout.activity_sign_up_basic_info) {
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModels {
        object : ViewModelProvider.Factory {
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

    }

    private fun nicknameDuplication() {
        //닉네임 중복 체크 서버 통신
        signUpBasicInfoViewModel.nickName.observe(this) {
            signUpBasicInfoViewModel.nickNameDuplication(RequestSignNickname(it))
        }

        signUpBasicInfoViewModel.nickNameDuplication.observe(this) {
            if (it) {
                binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
                binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.VISIBLE
            } else {
                binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
                binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.VISIBLE
            }
        }
        if (binding.etSignupBasicinfoNickname.text.toString() == "") {
            binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
            binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
        }
    }


    private fun emailDuplication() {
        //이메일 중복 체크 서버 통신
        signUpBasicInfoViewModel.email.observe(this) {
            signUpBasicInfoViewModel.emailDuplication(RequestSignEmail(it))
        }

        signUpBasicInfoViewModel.emailDuplication.observe(this) {
            if (it) {
                binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.VISIBLE
                binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
            } else {
                binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.VISIBLE
                binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
            }
        }
        if (binding.etSignupBasicinfoNickname.text.toString() == "") {
            binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
            binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
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
                //닉네임 textfield 빈칸인지 체크
                if (binding.etSignupBasicinfoNickname.text.toString() == "") {
                    binding.imgSignupBasicinfoNicknameCancel.isSelected = false
                    binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
                    binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
                } else {
                    isNickNamePattern()
                    binding.imgSignupBasicinfoNicknameCancel.isSelected = true
                    binding.textSignupBasicinfoNicknameDuplication.setOnClickListener {
                        signUpBasicInfoViewModel.nickName.value = p0.toString()
                        nicknameDuplication()
                        activationNextBtn()
                    }

                    val nickname = signUpBasicInfoViewModel.nickName.value

                    //닉네임 textfield 한글자라도 바뀐다면 하단 텍스트 사라지게
                    if (nickname != binding.etSignupBasicinfoNickname.text.toString()) {
                        binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
                        binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
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
                    binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
                    binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
                } else {
                    binding.imgSignupBasicinfoEmailCancel.isSelected = true

                    binding.textSignupBasicinfoEmailDuplication.setOnClickListener {
                        signUpBasicInfoViewModel.email.value = p0.toString()
                        emailDuplication()
                        activationNextBtn()
                    }


                    val email = signUpBasicInfoViewModel.email.value

                    //이메일 textfield 한글자라도 바뀐다면 하단 텍스트 사라지게
                    if (email != binding.etSignupBasicinfoEmail.text.toString()) {
                        binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
                        binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
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
        if (binding.etSignupBasicinfoPw.text.toString() == "" || binding.etSignupBasicinfoPwCheck.text.toString() == "") {
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
                        activationNextBtn()
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

    //닉네임 정규식
    private fun isNickNamePattern() {
        val nickname = binding.etSignupBasicinfoNickname

        if (!Pattern.matches("^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]{2,8}\$", nickname.text.toString())) {
            binding.textSignupBasicinfoNicknameTitle.setTextColor(Color.parseColor("#FF4C40"))
            binding.textSignupBasicinfoNicknameDuplication.isSelected = false
        } else {
            binding.textSignupBasicinfoNicknameTitle.setTextColor(Color.parseColor("#94959E"))
            binding.textSignupBasicinfoNicknameDuplication.isSelected = true
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
        signUpBasicInfoViewModel.requestSignUp.email =
            binding.etSignupBasicinfoEmail.text.toString()
        signUpBasicInfoViewModel.requestSignUp.nickname =
            binding.etSignupBasicinfoNickname.text.toString()
        signUpBasicInfoViewModel.requestSignUp.password =
            binding.etSignupBasicinfoPw.text.toString()
        val signData = signUpBasicInfoViewModel.signUp.value
        if (signData != null) {
//            val request = RequestSignUp(
//                signUpBasicInfoViewModel.requestSignUp.email,
//                signUpBasicInfoViewModel.requestSignUp.nickname,
//                signUpBasicInfoViewModel.requestSignUp.password,
//                1,
//                signUpBasicInfoViewModel.requestSignUp.firstMajorId,
//                signUpBasicInfoViewModel.requestSignUp.firstMajorStart,
//                signUpBasicInfoViewModel.requestSignUp.secondMajorId,
//                signUpBasicInfoViewModel.requestSignUp.secondMajorStart
//            )

        }

        binding.clSignupBasicinfoMoveNext.setOnClickListener {
            Log.d("signUp", "post0")
            signUpBasicInfoViewModel.signUp(
                RequestSignUp(
                    signUpBasicInfoViewModel.requestSignUp.email,
                    signUpBasicInfoViewModel.requestSignUp.nickname,
                    signUpBasicInfoViewModel.requestSignUp.password,
                    1,
                    signUpBasicInfoViewModel.requestSignUp.firstMajorId,
                    signUpBasicInfoViewModel.requestSignUp.firstMajorStart,
                    signUpBasicInfoViewModel.requestSignUp.secondMajorId,
                    signUpBasicInfoViewModel.requestSignUp.secondMajorStart
                )
            )
            Log.d("signUp", "post1")
            startActivity(Intent(this@SignUpBasicInfoActivity, SignUpFinishActivity::class.java))
            finish()
        }
    }

    private fun activationNextBtn() {
        if (binding.textSignupBasicinfoNicknameDuplicationOk.isVisible && binding.textSignupBasicinfoEmailDuplicationOk.isVisible && binding.textSignupBasicinfoPwDuplicationOk.isVisible) {
            binding.clSignupBasicinfoMoveNext.isSelected = true
            binding.textSignupBasicinfoNext.isSelected = true

            nextPage()

        } else {
            binding.clSignupBasicinfoMoveNext.isSelected = false
            binding.textSignupBasicinfoNext.isSelected = false
            Log.d("hyebin log1", "${binding.textSignupBasicinfoNicknameDuplicationOk.isVisible}")
            Log.d("hyebin log2", "${binding.textSignupBasicinfoEmailDuplicationOk.isVisible}")
            Log.d("hyebin log3", "${binding.textSignupBasicinfoNicknameDuplicationOk.isVisible}")
        }
    }
}