package com.prac.market.ui.account

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prac.market.core.DEFAULT_STRING
import com.prac.market.core.KEY_USER_ID
import com.prac.market.core.LOGIN
import com.prac.market.databinding.FragmentAccountBinding
import com.prac.market.model.Account
import com.prac.market.ui.welcome.WelcomeActivity

class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val loginPref = requireActivity().getSharedPreferences(LOGIN, 0)
        val savedId = loginPref.getString(KEY_USER_ID,DEFAULT_STRING)
        moveToLoginFragment(savedId)

        binding.account = Account(savedId.toString(),"")

        logout(loginPref)


    }

    private fun logout(loginPref: SharedPreferences) {
        binding.logoutBtn.setOnClickListener {
            //savedId 정보 삭제
            val editor = loginPref.edit()
            editor.clear()
            editor.commit()
            moveToLoginFragment(null)
        }
    }

    private fun moveToLoginFragment(savedId:String?) {
        if (savedId.isNullOrEmpty()) {
            // 저장된 id 없을시 로그인페이지로 이동
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}