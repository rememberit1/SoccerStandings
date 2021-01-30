package com.example.SoccerStandings.api

import com.example.SoccerStandings.models.Game
import retrofit2.Response

class Repository{
    suspend fun getGames(): Response<List<Game>>  {
        return RetrofitInstance.api.getPost()
    }
}