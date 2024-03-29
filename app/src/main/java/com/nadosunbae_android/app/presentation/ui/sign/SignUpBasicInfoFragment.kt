package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentSignUpBasicInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.util.SignInCustomDialog
import com.nadosunbae_android.domain.model.sign.EmailDuplicationData
import com.nadosunbae_android.domain.model.sign.NicknameDuplicationData
import com.nadosunbae_android.domain.model.sign.SignUpData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpBasicInfoFragment : BaseFragment<FragmentSignUpBasicInfoBinding>(R.layout.fragment_sign_up_basic_info) {

    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nicknameTextWatcher()
        emailTextWatcher()
        pwTextWatcher()
        pwCheckTextWatcher()
        beforeBtnClick()
        closePage()
        nicknameDuplication()
        initUnivEmail()
    }

    //학교 이메일 정보 서버 통신
    private fun initUnivEmail(){
        signUpBasicInfoViewModel.getUnivEmail(signUpBasicInfoViewModel.univId.value ?: 1)
        signUpBasicInfoViewModel.univEmail.observe(viewLifecycleOwner) {
            binding.etSignupBasicinfoEmail.setHint(it.email)
        }
    }

    //닉네임 중복 체크 서버 통신
    private fun nicknameDuplication() {
        Timber.d("NicknameDuplication: 서버 통신 성공")
        signUpBasicInfoViewModel.nicknameDuplicationCheck.observe(viewLifecycleOwner) {
            if (!it.success) {
                Timber.d("닉네임 중복확인: 실패")
                binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
                binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.VISIBLE
            }
            if (it.success) {
                Timber.d("닉네임 중복확인: 성공")
                binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
                binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.VISIBLE
                activationNextBtn()
            }
        }
        if (binding.etSignupBasicinfoNickname.text.toString() == "") {
            binding.textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
            binding.textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
            nextBtnDisabled()
        }
    }


    //이메일 중복 체크 서버 통신
    private fun emailDuplication() {
        signUpBasicInfoViewModel.emailDuplicationCheck.observe(this) {
            if (!it.success) {
                Timber.d("이메일 중복확인: 실패")
                binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.VISIBLE
                binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
            } else {
                Timber.d("이메일 중복확인: 성공")
                Timber.d("이메일 중복확인: 성공")
                binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.VISIBLE
                binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
                activationNextBtn()
            }
        }
        if (binding.etSignupBasicinfoNickname.text.toString() == "") {
            binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
            binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
            nextBtnDisabled()
        }
    }


    //닉네임 textwatcher
    private fun nicknameTextWatcher() = with(binding) {
        etSignupBasicinfoNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                //닉네임 textfield 빈칸인지 체크
                if (etSignupBasicinfoNickname.text.toString() == "") {
                    imgSignupBasicinfoNicknameCancel.isSelected = false
                    textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
                    textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
                    nextBtnDisabled()
                } else {
                    isNickNamePattern()
                    imgSignupBasicinfoNicknameCancel.isSelected = true
                    textSignupBasicinfoNicknameDuplication.setOnClickListener {
                        signUpBasicInfoViewModel.nickName.value = p0.toString()
                        signUpBasicInfoViewModel.nickNameDuplication(NicknameDuplicationData(binding.etSignupBasicinfoNickname.text.toString()))
                        activationNextBtn()
                    }

                    val nickname = signUpBasicInfoViewModel.nickName.value

                    //닉네임 textfield 한글자라도 바뀐다면 하단 텍스트 사라지게
                    if (nickname != etSignupBasicinfoNickname.text.toString()) {
                        textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
                        textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
                    }
                    activationNextBtn()
                }
            }
        })

        //textfield에 생성되는 x버튼 클릭 리스너
        imgSignupBasicinfoNicknameCancel.setOnClickListener {
            etSignupBasicinfoNickname.setText(null)
            textSignupBasicinfoNicknameDuplicationOk.visibility = View.INVISIBLE
            textSignupBasicinfoNicknameDuplicationNo.visibility = View.INVISIBLE
            textSignupBasicinfoNicknameDuplication.isSelected = false
        }
    }


    //이메일 textwatcher
    private fun emailTextWatcher() = with(binding) {
        etSignupBasicinfoEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                isEmailPattern()

                //textfield 빈칸인지 체크
                if (etSignupBasicinfoEmail.text.toString() == "") {
                    imgSignupBasicinfoEmailCancel.isSelected = false
                    textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
                    textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
                    nextBtnDisabled()
                } else {
                    imgSignupBasicinfoEmailCancel.isSelected = true
                    textSignupBasicinfoEmailDuplication.setOnClickListener {
                        signUpBasicInfoViewModel.email.value = p0.toString()
                        signUpBasicInfoViewModel.emailDuplication(EmailDuplicationData(binding.etSignupBasicinfoEmail.text.toString()))
                        emailDuplication()
                        activationNextBtn()
                    }
                    val email = signUpBasicInfoViewModel.email.value

                    //이메일 textfield 한글자라도 바뀐다면 하단 텍스트 사라지게
                    if (email != etSignupBasicinfoEmail.text.toString()) {
                        textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
                        textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
                    }
                    activationNextBtn()
                }
            }
        })

        //textfield에 생성되는 x버튼 클릭 리스너
        imgSignupBasicinfoEmailCancel.setOnClickListener {
            etSignupBasicinfoEmail.setText(null)
            textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
            textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
        }
    }


    //비밀번호 textwatcher
    private fun pwTextWatcher() = with(binding) {
        etSignupBasicinfoPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                //textfield 빈칸인지 체크
                if (etSignupBasicinfoPw.text.toString() == "") {
                    initPwDuplicationSetting()
                    imgSignupBasicinfoPwCancel.isSelected = false
                    textSignupBasicinfoPwTitle.setTextColor(Color.parseColor("#94959E"))
                } else {
                    imgSignupBasicinfoPwCancel.isSelected = true

                    //비밀번호, 비밀번호 확인 일치하는지 체크
                    if (etSignupBasicinfoPw.text.toString() == etSignupBasicinfoPwCheck.text.toString()) {
                        textSignupBasicinfoPwDuplicationOk.visibility = View.VISIBLE
                        textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
                    } else {
                        textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
                        textSignupBasicinfoPwDuplicationNo.visibility = View.VISIBLE
                    }
                    activationNextBtn()
                }
                isValidRegistrationPw()


            }

        })
        //textfield에 생성되는 x버튼 클릭 리스너
        imgSignupBasicinfoPwCancel.setOnClickListener {
            etSignupBasicinfoPw.setText(null)
        }
    }


    //비밀번호 확인 textwatcher
    private fun pwCheckTextWatcher() = with(binding) {
        etSignupBasicinfoPwCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                //textfield 빈칸 체크
                if (etSignupBasicinfoPwCheck.text.toString() == "") {
                    initPwDuplicationSetting()
                    imgSignupBasicinfoPwCheckCancel.isSelected = false
                    textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
                    textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
                } else {
                    imgSignupBasicinfoPwCheckCancel.isSelected = true
                    //pw, pw확인 같은지 check
                    if (etSignupBasicinfoPw.text.toString() == etSignupBasicinfoPwCheck.text.toString()) {
                        textSignupBasicinfoPwDuplicationOk.visibility = View.VISIBLE
                        textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
                    } else {
                        textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
                        textSignupBasicinfoPwDuplicationNo.visibility = View.VISIBLE
                    }
                    activationNextBtn()
                }
                isValidRegistrationPw()
            }
        })

        //textfield에 생성되는 x버튼 클릭 리스너
        imgSignupBasicinfoPwCheckCancel.setOnClickListener {
            etSignupBasicinfoPwCheck.setText(null)
        }
    }


    //이전버튼 클릭 이벤트
    private fun beforeBtnClick() {
        binding.clSignupBasicinfoMoveBefore.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_SecondFragment)
            binding.textSignupBasicinfoEmailDuplicationOk.visibility = View.INVISIBLE
            binding.textSignupBasicinfoEmailDuplicationNo.visibility = View.INVISIBLE
        }
    }


    //닉네임 정규식
    private fun isNickNamePattern() = with(binding) {
        val nickname = etSignupBasicinfoNickname
        if (!Pattern.matches("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9|]{2,8}\$", nickname.text.toString())) {
            textSignupBasicinfoNicknameTitle.setTextColor(Color.parseColor("#FF4C40"))
            textSignupBasicinfoNicknameDuplication.isSelected = false
            textSignupBasicinfoNicknameDuplication.isEnabled = false
        } else {
            textSignupBasicinfoNicknameTitle.setTextColor(Color.parseColor("#94959E"))
            textSignupBasicinfoNicknameDuplication.isSelected = true
            textSignupBasicinfoNicknameDuplication.isEnabled = true
        }
    }


    //이메일 정규식
    private fun isEmailPattern() {
        val univEmail = signUpBasicInfoViewModel.univEmail.value?.email ?:""
        binding.textSignupBasicinfoEmailDuplication.isSelected = true
        binding.textSignupBasicinfoEmailDuplication.isEnabled = true
        if (binding.etSignupBasicinfoEmail.text.contains("$univEmail")) {
            binding.textSignupBasicinfoEmailDuplication.isSelected = true
            binding.textSignupBasicinfoEmailDuplication.isEnabled = true
        } else {
            binding.textSignupBasicinfoEmailDuplication.isSelected = false
            binding.textSignupBasicinfoEmailDuplication.isEnabled = false
        }
    }


    //비밀번호 정규식
    fun isValidRegistrationPw() = with(binding) {
        val password = etSignupBasicinfoPw

        if (!Pattern.matches(
                "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}\$",
                password.text.toString()
            )
        ) {
            textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
            textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
            textSignupBasicinfoPwTitle.setTextColor(Color.parseColor("#FF4C40"))
        } else {
            textSignupBasicinfoPwTitle.setTextColor(Color.parseColor("#94959E"))
        }
    }


    //상단 x눌렀을 시 알럿
    fun closePage() {
        binding.imgSignupBasicinfoDelete.setOnClickListener {
            val dialog = SignInCustomDialog(requireActivity())
            dialog.showDialog()

            dialog.setOnClickListener(object : SignInCustomDialog.ButtonClickListener {
                override fun onClicked(num: () -> Unit) {
                    startActivity(Intent(requireActivity(), SignInActivity::class.java))
                    activity?.finish()
                }
            })
        }
    }

    //비밀번호 중복 체크 처음에는 다 INVISIBLE로 세팅
    private fun initPwDuplicationSetting() = with(binding) {
        if (etSignupBasicinfoPw.text.toString() == "" || etSignupBasicinfoPwCheck.text.toString() == "") {
            textSignupBasicinfoPwDuplicationOk.visibility = View.INVISIBLE
            textSignupBasicinfoPwDuplicationNo.visibility = View.INVISIBLE
            nextBtnDisabled()
        }
    }


    //다음 페이지로 이동
    private fun nextPage() = with(binding) {
        if (clSignupBasicinfoMoveNext.isSelected && textSignupBasicinfoNext.isSelected) {
            signUpBasicInfoViewModel.requestSignUp.email = etSignupBasicinfoEmail.text.toString()
            signUpBasicInfoViewModel.requestSignUp.nickname =
                etSignupBasicinfoNickname.text.toString()
            signUpBasicInfoViewModel.requestSignUp.password = etSignupBasicinfoPw.text.toString()

            //다음 버튼 클릭 리스터
            clSignupBasicinfoMoveNext.setOnClickListener {
                signUpBasicInfoViewModel.signUp(
                    SignUpData(
                        signUpBasicInfoViewModel.requestSignUp.email,
                        signUpBasicInfoViewModel.requestSignUp.nickname,
                        signUpBasicInfoViewModel.requestSignUp.password,
                        signUpBasicInfoViewModel.univId.value ?: 0,
                        signUpBasicInfoViewModel.firstMajorId.value ?:-1,
                        signUpBasicInfoViewModel.firstMajorStart.value ?: "",
                        signUpBasicInfoViewModel.secondMajorId.value ?:-1,
                        signUpBasicInfoViewModel.secondMajorStart.value ?: "",
                    )
                )

                val intent = Intent(requireActivity(), SignUpFinishActivity::class.java)
                intent.putExtra("email", etSignupBasicinfoEmail.text.toString())
                intent.putExtra("password", etSignupBasicinfoPw.text.toString())

                startActivity(intent)
                //finish()
            }
        }
    }

    //다음 페이지로 가는 버튼 활성화
    private fun activationNextBtn() = with(binding) {
        if (textSignupBasicinfoNicknameDuplicationOk.isVisible && textSignupBasicinfoEmailDuplicationOk.isVisible && textSignupBasicinfoPwDuplicationOk.isVisible) {
            clSignupBasicinfoMoveNext.isSelected = true
            textSignupBasicinfoNext.isSelected = true
            nextPage()
        } else {
            nextBtnDisabled()
        }
    }

    //완료버튼 isSelected false
    private fun nextBtnDisabled() {
        binding.clSignupBasicinfoMoveNext.isSelected = false
        binding.textSignupBasicinfoNext.isSelected = false
    }

}