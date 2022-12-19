package com.prac.market.ui.account

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.prac.market.R
import com.prac.market.core.*
import com.prac.market.databinding.FragmentAccountBinding
import com.prac.market.model.Account

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val loginPref = requireActivity().getSharedPreferences(LOGIN, 0)
        val savedId = loginPref.getString(KEY_USER_ID, DEFAULT_STRING)
        moveToLoginFragment(savedId)

        binding.account = Account(savedId.toString(), "")

        logout(loginPref)

        //ToDo Viemodel 객체 accountResult observe 해서 값이 다른 fragment에서도 꺼내지는지 check -> mypage에서 관찰해서 null일시 sns signin page로

    }

    private fun logout(loginPref: SharedPreferences) {
        val TAG = "logout()"
        binding.logoutBtn.setOnClickListener {
            //savedId 정보 삭제

            val snsName = loginPref.getString(IS_SNS_LOGIN, DEFAULT_STRING)
            if (snsName == "kakao") {
                Log.d("logout()", "kakao")
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    } else {
                        Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    }
                }

            } else if (snsName == "google") {
                Log.d("logout()", "google")
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                val mGoogleSignInClient = this.let { GoogleSignIn.getClient(requireContext(), gso) }
                mGoogleSignInClient.signOut()
            }
            val editor = loginPref.edit()
            editor.clear()
            editor.commit()
            Toast.makeText(this.context, LOGOUT_SUCCESS, Toast.LENGTH_SHORT).show()
            moveToLoginFragment(null)
        }
    }

    private fun moveToLoginFragment(savedId: String?) {
        if (savedId.isNullOrEmpty()) {
            // 저장된 id 없을시 로그인페이지로 이동
            findNavController().navigate(R.id.action_navigation_account_to_loginFragment2)

        }
    }


}