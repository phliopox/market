package com.prac.market.ui.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.prac.market.MainActivity
import com.prac.market.R
import com.prac.market.core.KEY_USER_ID
import com.prac.market.databinding.FragmentSnsSignInBinding
import com.prac.market.ui.common.layoutBackground

class SnsSignInFragment : Fragment() {

    private lateinit var binding: FragmentSnsSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSnsSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutBackground(
            view,
            "https://user-images.githubusercontent.com/91457591/206108205-6fe39f5e-1674-4129-8b90-e1005a9870de.jpg"
        )

        kakaoLogin()
        moveToEmailLogin()

    }
    //ToDo Viemodel 객체 accountResult observe 해서 값이 다른 fragment에서도 꺼내지는지 check -> mypage에서 관찰해서 null일시 sns signin page로

    private fun kakaoLogin() {
        val context = requireContext()
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                getUserInfo(context)
            }
        }
        binding.kakaoLoginBtn.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "로그인 성공 ${token.accessToken}")
                        getUserInfo(context)

                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }

    private fun getUserInfo(context: Context) {
        val TAG = "getUserInfo()"

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {

                //이미 동의한 적이 있는 회원
                if(user.kakaoAccount?.email!=null){
                    val email = user.kakaoAccount?.email.toString()
                    saveEmailInfo(email)
                }

                var scopes = mutableListOf<String>()
                if (user.kakaoAccount?.emailNeedsAgreement == true) {
                    scopes.add("account_email")
                }

                if (scopes.isNotEmpty()) {
                    Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")
                    //scope 목록을 전달하여 카카오 로그인 요청
                    UserApiClient.instance.loginWithNewScopes(context, scopes) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 추가 동의 실패", error)
                        } else {
                            // 사용자 정보 재요청
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", error)
                                } else if (user != null) {
                                    //사용자 정보 요청 성공
                                    val email = user.kakaoAccount?.email.toString()
                                    saveEmailInfo(email)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveEmailInfo(email: String) {
        //email pref에 보관 후 intent
        Log.d("KAKAO user", email)

        val pref = requireActivity().getSharedPreferences("LoginFragment", 0)
        val edit = pref.edit()
        edit.putString(KEY_USER_ID, email)
        edit.commit()

        val intent = Intent(requireContext(), MainActivity::class.java)
                                    startActivity(intent)
    }

    private fun moveToEmailLogin() {
        binding.emailLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_snsSignInFragment_to_loginFragment)

        }
    }
}