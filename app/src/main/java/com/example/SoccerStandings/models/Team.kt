package com.example.SoccerStandings.models

import java.io.Serializable

data class Team(
    var games: ArrayList<Game>,
    var wins: Int,
    var losses: Int,
    var draws: Int,
    var teamName: String,
    var percentage: Double
) : Serializable

