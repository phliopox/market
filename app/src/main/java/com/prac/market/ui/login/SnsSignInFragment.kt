package com.prac.market.ui.login

import android.content.ContentValues.TAG
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
import com.prac.market.R
import com.prac.market.databinding.FragmentSnsSignInBinding
import com.prac.market.ui.common.layoutBackground

class SnsSignInFragment : Fragment() {

    private lateinit var binding : FragmentSnsSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSnsSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutBackground(view,"https://user-images.githubusercontent.com/91457591/206108205-6fe39f5e-1674-4129-8b90-e1005a9870de.jpg")

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
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                var scopes = mutableListOf<String>()

                if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }
                if (user.kakaoAccount?.birthdayNeedsAgreement == true) { scopes.add("birthday") }
                if (user.kakaoAccount?.birthyearNeedsAgreement == true) { scopes.add("birthyear") }
                if (user.kakaoAccount?.genderNeedsAgreement == true) { scopes.add("gender") }
                if (user.kakaoAccount?.phoneNumberNeedsAgreement == true) { scopes.add("phone_number") }
                if (user.kakaoAccount?.profileNeedsAgreement == true) { scopes.add("profile") }
                if (user.kakaoAccount?.ageRangeNeedsAgreement == true) { scopes.add("age_range") }
                if (user.kakaoAccount?.ciNeedsAgreement == true) { scopes.add("account_ci") }

                if (scopes.count() > 0) {
                    Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

                    // OpenID Connect 사용 시
                    // scope 목록에 "openid" 문자열을 추가하고 요청해야 함
                    // 해당 문자열을 포함하지 않은 경우, ID 토큰이 재발급되지 않음
                    // scopes.add("openid")

                    //scope 목록을 전달하여 카카오 로그인 요청
                    UserApiClient.instance.loginWithNewScopes(context, scopes) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 추가 동의 실패", error)
                        } else {
                            Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                            // 사용자 정보 재요청
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", error)
                                }
                                else if (user != null) {
                                    Log.i(TAG, "사용자 정보 요청 성공")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun moveToEmailLogin() {
        binding.emailLoginBtn.setOnClickListener {
           findNavController().navigate(R.id.action_snsSignInFragment_to_loginFragment)

        }
    }
}