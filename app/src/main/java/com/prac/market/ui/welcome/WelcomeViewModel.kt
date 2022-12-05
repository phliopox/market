package com.prac.market.ui.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.market.database.WelcomeRepository
import com.prac.market.model.WelcomeBanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val repository: WelcomeRepository):ViewModel()  {
    private val _welcomeBanner = MutableLiveData<List<WelcomeBanner>>()
    val welcomeBanner=_welcomeBanner

    init{
        loadData()
    }
    private fun loadData(){
        viewModelScope.launch {
            val data= repository.getWelcomePage()
            _welcomeBanner.value= data
        }
    }

}