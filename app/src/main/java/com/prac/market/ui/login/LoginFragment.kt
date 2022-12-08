package com.prac.market.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.prac.market.R
import com.prac.market.databinding.FragmentLoginBinding
import com.prac.market.model.FragmentText
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

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
        val createAccountLayout = FragmentText("Go with your flow", "Create account") // 계정 만들기
        val loginLayout = FragmentText("Welcome back!", "Login") // 로그인하기
        binding.fragmentText = createAccountLayout


        sign_fg_btn.setOnClickListener {
            val editTextEmail = sign_fg_email.text.toString()
            val editTextPassword = sign_fg_password.text.toString()

            if (editTextEmail.isEmpty() || editTextPassword.isEmpty()) {
                Toast.makeText(this.context, "email 과 비밀번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {

                createAccount(editTextEmail, editTextPassword)
                sign_fg_email.setText("")
                sign_fg_password.setText("")
                binding.fragmentText = loginLayout

            }
        }
    }


    private fun createAccount(email: String, password: String) {
        email

    }
}