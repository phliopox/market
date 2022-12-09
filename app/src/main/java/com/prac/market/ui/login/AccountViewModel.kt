package com.prac.market.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.market.database.AccountRepository
import com.prac.market.model.FragmentText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val repository: AccountRepository) : ViewModel() {
    private val _layoutText = FragmentText("Welcome back!", "Login")
    fun addNewAccount(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.addNewAccount(email, password)
            Log.d("AccountViewModel : Api response result",result.toString())

        }
    }
}