package com.example.SoccerStandings.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Game (
    @SerializedName("GameId")val gameId: String,
    @SerializedName("AwayTeamId")val awayTeamId: String,
    @SerializedName("AwayTeamName")val awayTeamName: String,
    @SerializedName("HomeTeamId")val homeTeamId: String,
    @SerializedName("HomeTeamName")val homeTeamName: String,
    @SerializedName("AwayScore")val awayScore: Int,
    @SerializedName("HomeScore")val homeScore: Int
):Serializable
