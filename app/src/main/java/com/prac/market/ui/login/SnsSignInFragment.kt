package com.prac.market.ui.login

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.prac.market.MainActivity
import com.prac.market.R
import com.prac.market.core.*
import com.prac.market.databinding.FragmentSnsSignInBinding
import com.prac.market.ui.common.layoutBackground


class SnsSignInFragment : Fragment() {

    private lateinit var binding: FragmentSnsSignInBinding
    private lateinit var callback : OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView(true)
        binding = FragmentSnsSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutBackground(
            view,
            "https://user-images.githubusercontent.com/91457591/206108205-6fe39f5e-1674-4129-8b90-e1005a9870de.jpg"
        )
        googleBtnCustom()
        kakaoLogin()
        googleLogin()
        moveToEmailLogin()

    }

    private fun googleBtnCustom() {
    binding.googleLoginBtn.setSize(1)
    }

    private fun googleLogin() {
        val TAG = "googleLogin()"
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        Log.d(TAG, account.toString())

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Log.d(TAG, result.toString())
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(data)
                    handleSignInResults(task)
                }
            }

        binding.googleLoginBtn.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            //  startActivityForResult(signInIntent, RC_SIGN_IN)
            resultLauncher.launch(signInIntent)
        }


    }

    private fun handleSignInResults(completedTask: Task<GoogleSignInAccount>) {
        try {
            val snsName = "google"
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            saveEmailInfo(email, snsName)
        } catch (e: ApiException) {
            Log.w("failed", "signInResult:failed code" + e.statusCode)
        }
    }

    private fun kakaoLogin() {
        val context = requireContext()
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "????????????????????? ????????? ??????", error)
            } else if (token != null) {
                Log.i(TAG, "????????????????????? ????????? ?????? ${token.accessToken}")
                getUserInfoFromKakaoAccount(context)
            }
        }
        binding.kakaoLoginBtn.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "????????? ??????", error)

                        // ???????????? ???????????? ?????? ??? ???????????? ?????? ?????? ???????????? ???????????? ????????? ??????,
                        // ???????????? ????????? ????????? ?????? ????????????????????? ????????? ?????? ?????? ????????? ????????? ?????? (???: ?????? ??????)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // ??????????????? ????????? ?????????????????? ?????? ??????, ????????????????????? ????????? ??????
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "????????? ?????? ${token.accessToken}")
                        getUserInfoFromKakaoAccount(context)

                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }

    private fun getUserInfoFromKakaoAccount(context: Context) {
        val TAG = "getUserInfo()"
        val snsName = "kakao"
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "????????? ?????? ?????? ??????", error)
            } else if (user != null) {

                //?????? ????????? ?????? ?????? ??????
                if (user.kakaoAccount?.email != null) {
                    val email = user.kakaoAccount?.email.toString()
                    saveEmailInfo(email, snsName)
                }

                val scopes = mutableListOf<String>()
                if (user.kakaoAccount?.emailNeedsAgreement == true) {
                    scopes.add("account_email")
                }

                if (scopes.isNotEmpty()) {
                    Log.d(TAG, "??????????????? ?????? ????????? ????????? ?????????.")
                    //scope ????????? ???????????? ????????? ????????? ??????
                    UserApiClient.instance.loginWithNewScopes(context, scopes) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "????????? ?????? ?????? ??????", error)
                        } else {
                            // ????????? ?????? ?????????
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e(TAG, "????????? ?????? ?????? ??????", error)
                                } else if (user != null) {
                                    //????????? ?????? ?????? ??????

                                    val email = user.kakaoAccount?.email.toString()


                                    saveEmailInfo(email, snsName)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveEmailInfo(email: String, snsName: String) {
        //email pref??? ?????? ??? intent
        Log.d("KAKAO user", email)

        val pref = requireActivity().getSharedPreferences(LOGIN, 0)
        val edit = pref.edit()
        edit.putString(KEY_USER_ID, email)

        /*val snsLoginPref = requireActivity().getSharedPreferences(IS_SNS_LOGIN,0)
        val snsEdit = snsLoginPref.edit()*/
        edit.putString(IS_SNS_LOGIN, snsName)
        edit.commit()

        Toast.makeText(this.context, LOGIN_SUCCESS, Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    private fun moveToEmailLogin() {
        binding.emailLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_snsSignInFragment_to_loginFragment)

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_snsSignInFragment_to_navigation_home)
                hideBottomNavigationView(false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun hideBottomNavigationView(bool :Boolean){
        val bottomNaviView =
            requireActivity().findViewById<BottomNavigationView>(R.id.navigation_main)
        if(bool) {
            bottomNaviView.visibility = View.GONE
        }else{
            bottomNaviView.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideBottomNavigationView(false)
    }
}