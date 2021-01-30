package com.example.SoccerStandings.Views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.SoccerStandings.api.Repository
import com.example.SoccerStandings.models.Game
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {
    val myResponse: MutableLiveData<Response<List<Game>>> = MutableLiveData()

    fun getGames() {
        viewModelScope.launch {
            val response = repository.getGames()
            myResponse.value = response
        }
    }
}