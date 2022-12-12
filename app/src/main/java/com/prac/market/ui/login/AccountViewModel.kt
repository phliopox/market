package com.prac.market.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.market.Event
import com.prac.market.database.AccountRepository
import com.prac.market.database.hosting.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SIGN_IN_SUCCESS = "가입이 완료되었습니다."
const val EXIST_ID = "이미 가입된 이메일입니다."
const val UNKNOWN_ERROR="죄송합니다. 서비스를 개선중입니다."
const val NOT_EXIST_ID ="존재하지않는 아이디입니다."

@HiltViewModel
class AccountViewModel @Inject constructor(private val repository: AccountRepository) : ViewModel() {

    private val _accountResult = MutableLiveData<Result>()
    val accountResult :LiveData<Result> =  _accountResult

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message


    fun addNewAccount(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.addNewAccount(email, password)
            _accountResult.value= result

            if (result.success && !result.existAccount) {
                _message.value = Event(SIGN_IN_SUCCESS)
            } else if (result.existAccount) {
                Log.d("CreateAccount Method", result.existAccount.toString())
                _message.value = Event(EXIST_ID)
            } else {
                _message.value = Event(UNKNOWN_ERROR)
            }
        }
    }

    fun loginCheck(email:String, password:String){
        viewModelScope.launch {
            val result = repository.login(email, password)
            _accountResult.value= result

            if(result.success){
                //TODO 세션
            }else if(!result.existAccount){
                _message.value = Event(NOT_EXIST_ID)
            }
        }
    }

}
