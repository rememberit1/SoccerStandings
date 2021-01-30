package com.example.SoccerStandings.Views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SoccerStandings.R
import com.example.SoccerStandings.adapters.TeamAdapter
import com.example.SoccerStandings.models.OpposingTeam
import com.example.SoccerStandings.models.Team
import kotlinx.android.synthetic.main.activity_team.*

class TeamActivity : AppCompatActivity() {

    private lateinit var team: Team
    var mapOfOpposingTeams: HashMap<String, OpposingTeam> = HashMap()
    private lateinit var sortedTeams: List<OpposingTeam>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        team = (intent.getSerializableExtra("team") as? Team)!!
        teamName_activity_tv.text = team.teamName
        showOpposingTeams()
    }

    private fun showOpposingTeams() {
        for (i in 0 until team.games.count()) {
            var opposingTeam = OpposingTeam()
            var thisGame = team.games[i]
            var opposingAddADraw = 0
            var opposingAddAWin = 0
            var thisTeamAddAWin = 0
            if (thisGame.homeScore == thisGame.awayScore) {
                opposingAddADraw = 1
            }

            if (thisGame.awayTeamName != team.teamName) {
                opposingTeam.name = thisGame.awayTeamName
                if (thisGame.homeScore > thisGame.awayScore) {
                    thisTeamAddAWin = 1
                } else if (thisGame.homeScore < thisGame.awayScore) {
                    opposingAddAWin = 1
                }
            } else {
                opposingTeam.name = thisGame.homeTeamName
                if (thisGame.homeScore > thisGame.awayScore) {
                    thisTeamAddAWin = 1
                } else if (thisGame.homeScore < thisGame.awayScore) {
                    opposingAddAWin = 1
                }
            }

            if (mapOfOpposingTeams.containsKey(opposingTeam.name)) {
                mapOfOpposingTeams.get(opposingTeam.name)!!.winsAgainst += thisTeamAddAWin
                mapOfOpposingTeams.get(opposingTeam.name)!!.lossesAgainst += opposingAddAWin
                mapOfOpposingTeams.get(opposingTeam.name)!!.drawsAgainst += opposingAddADraw
            } else {
                opposingTeam.winsAgainst = thisTeamAddAWin
                opposingTeam.lossesAgainst = opposingAddAWin
                opposingTeam.drawsAgainst = opposingAddADraw
                mapOfOpposingTeams.put(opposingTeam.name, opposingTeam)
            }
        }
        showData()
    }

    private fun showData() {
        opposing_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@TeamActivity)
            sortedTeams = mapOfOpposingTeams.values.sortedByDescending { it.winsAgainst }
            for (mapEntry in sortedTeams) {
                mapEntry.totalGamesAgainst =
                    mapEntry.winsAgainst + mapEntry.drawsAgainst + mapEntry.lossesAgainst
            }
            adapter = TeamAdapter(ArrayList(sortedTeams))
        }
    }
}

