package com.prac.market.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.market.Event
import com.prac.market.core.*
import com.prac.market.database.AccountRepository
import com.prac.market.database.hosting.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(private val repository: AccountRepository) : ViewModel() {

    private val _accountResult = MutableLiveData<Result>()
    val accountResult :LiveData<Result> =  _accountResult

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private val _tokenValue = MutableLiveData<String>()
    val tokenValue : LiveData<String> = _tokenValue

    fun addNewAccount(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.addNewAccount(email, password)
            _accountResult.value= result

            if (result.success && !result.existAccount) {
                _message.value = Event(SIGN_IN_SUCCESS)
            } else if (result.existAccount) {
                _message.value = Event(EXIST_ID)
            } else {
                _message.value = Event(UNKNOWN_ERROR)
            }
        }
    }

    fun loginCheck(email:String, password:String){
        viewModelScope.launch {
            val result = repository.login(email, password)

            if(result.success){
                _accountResult.value = result
                _message.value = Event(LOGIN_SUCCESS)
            }else if(!result.existAccount){
                _message.value = Event(NOT_EXIST_ID)
            }else if(!result.success&&result.existAccount){
                _message.value = Event(PASSWORD_ERROR)
            }


        }
    }

}
