package com.prac.market.ui.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.prac.market.R
import com.prac.market.databinding.FragmentLoginBinding
import com.prac.market.model.FragmentText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel : AccountViewModel by viewModels()
    private val createAccountLayout = FragmentText("Go with your flow", "Create account") // 계정 만들기
    private val loginLayout = FragmentText("Welcome back!", "Login") // 로그인하기


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentText = createAccountLayout


        sign_fg_btn.setOnClickListener {
            val editTextEmail = sign_fg_email.text.toString()
            val editTextPassword = sign_fg_password.text.toString()

            userInfoValidation(editTextEmail,editTextPassword)

        }
    }


    private fun userInfoValidation(editTextEmail: String, editTextPassword: String) {
        if (editTextEmail.isEmpty() || editTextPassword.isEmpty()) {
            Toast.makeText(this.context, "email 과 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail).matches()){
            Toast.makeText(this.context, "올바른 email 형식을 사용해주세요.", Toast.LENGTH_SHORT).show()
        }else if(editTextPassword.length<6){
            Toast.makeText(this.context, "비밀번호는 6자리 이상이어야합니다.", Toast.LENGTH_SHORT).show()
        } else {
            createAccount(editTextEmail, editTextPassword)
        }
    }


    private fun createAccount(validatedEmail: String, validatedPassword: String) {

        viewModel.addNewAccount(validatedEmail,validatedPassword)
        viewModel.addAccountResult.observe(viewLifecycleOwner){response->
            if(response.success){
                sign_fg_email.setText("")
                sign_fg_password.setText("")
                binding.fragmentText = loginLayout
            }else{
                Toast.makeText(this.context, "죄송합니다 서비스를 개선중입니다.", Toast.LENGTH_SHORT).show()

            }
        }
    }
}