package com.prac.market.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.market.database.HomeRepository
import com.prac.market.model.Banners
import com.prac.market.model.HomeSections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {

    private val _homeData = MutableLiveData<List<Banners>>()
    val homeData : LiveData<List<Banners>> = _homeData

    init {
        loadData()
    }

    private fun loadData(){
        viewModelScope.launch {
            val data = repository.getHomeSections()
                _homeData.value = data
        }
    }
}