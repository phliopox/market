package com.prac.market.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prac.market.EventObserver
import com.prac.market.MainActivity
import com.prac.market.R
import com.prac.market.core.DEFAULT_STRING
import com.prac.market.core.KEY_USER_ID
import com.prac.market.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AccountViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailLogin()
        moveToEmailSignInFragment()



        /*//sharedPreferences check Log
        val string = requireActivity().getSharedPreferences("LoginFragment",0).getString(KEY_USER_ID, DEFAULT_STRING)

        Log.d("LoginFragment 1-1", requireActivity()::class.java.toString())
        Log.d("LoginFragment 1-2", string.toString())
        Log.d("LoginFragment 1-3", requireActivity().getPreferences(0).getBoolean("isFirst",true).toString())*/
    }

    private fun moveToEmailSignInFragment() {
        binding.moveSignInFg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }
    }

    private fun emailLogin() {
        binding.loginFgBtn.setOnClickListener {
            val loginEmail = binding.loginFgEmail.text.toString()
            val loginPassword = binding.loginFgPassword.text.toString()

            viewModel.loginCheck(loginEmail, loginPassword)
            viewModel.message.observe(viewLifecycleOwner, EventObserver { message ->
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                if (message == LOGIN_SUCCESS) {
                    viewModel.accountResult.value?.login_token?.let {
                        val pref = requireActivity().getSharedPreferences("LoginFragment", 0)
                        val edit = pref.edit()
                        edit.putString(KEY_USER_ID, it)
                        edit.commit()

                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
        }
    }
}