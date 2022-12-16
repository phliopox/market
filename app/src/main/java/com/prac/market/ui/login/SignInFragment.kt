package com.prac.market.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prac.market.EventObserver
import com.prac.market.R
import com.prac.market.core.SIGN_IN_SUCCESS
import com.prac.market.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel : AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signFgBtn.setOnClickListener {

            val editTextEmail = binding.signFgEmail.text.toString()
            val editTextPassword = binding.signFgPassword.text.toString()
            userInfoValidation(editTextEmail,editTextPassword)


        }
    }


    private fun userInfoValidation(editTextEmail: String, editTextPassword: String) {
        var message : String? = null

        if (editTextEmail.isEmpty() || editTextPassword.isEmpty()) {
            message = "email 과 비밀번호를 모두 입력해주세요."
        }else if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail).matches()){
            message = "올바른 email 형식을 사용해주세요."
        }else if(editTextPassword.length<6){
            message = "비밀번호는 6자리 이상이어야합니다."
        } else {
            createAccount(editTextEmail, editTextPassword)
        }
        message?.let{Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()}
    }


    private fun createAccount(validatedEmail: String, validatedPassword: String) {
        viewModel.addNewAccount(validatedEmail,validatedPassword)
        viewModel.message.observe(viewLifecycleOwner,EventObserver{ message ->
            Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show()
            if(message == SIGN_IN_SUCCESS){loginNavigation()}
        })
    }

    private fun loginNavigation(){
        findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
    }
}
