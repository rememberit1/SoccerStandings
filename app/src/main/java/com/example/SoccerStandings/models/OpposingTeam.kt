package com.example.SoccerStandings.models

import java.io.Serializable

data class OpposingTeam (
    var name: String = "",
    var winsAgainst: Int = 0,
    var lossesAgainst: Int = 0,
    var drawsAgainst: Int = 0,
    var totalGamesAgainst: Int = 0
): Serializable

