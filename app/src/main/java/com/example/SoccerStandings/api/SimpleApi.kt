package com.example.SoccerStandings.api

import com.example.SoccerStandings.models.Game
import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {
    //we use coroutines
    @GET("re/v2/coding_exercise/soccer_data.json")
    suspend fun getPost(): Response<List<Game>>



}