package com.prac.market.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prac.market.R
import com.prac.market.databinding.FragmentSnsSignInBinding
import com.prac.market.ui.common.layoutBackground
import kotlinx.android.synthetic.main.fragment_sns_sign_in.*

class SnsSignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSnsSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutBackground(view,"https://user-images.githubusercontent.com/91457591/206108205-6fe39f5e-1674-4129-8b90-e1005a9870de.jpg")
        setNavigation()

    }
    private fun setNavigation() {
        email_login_btn.setOnClickListener {
           findNavController().navigate(R.id.action_snsSignInFragment_to_loginFragment)

        }
    }
}