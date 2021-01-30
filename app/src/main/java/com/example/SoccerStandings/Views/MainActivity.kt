package com.example.SoccerStandings.Views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SoccerStandings.*
import com.example.SoccerStandings.adapters.StandingsAdapter
import com.example.SoccerStandings.api.OnItemClickListenerSoccer
import com.example.SoccerStandings.api.Repository
import com.example.SoccerStandings.models.Game
import com.example.SoccerStandings.models.Team
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        OnItemClickListenerSoccer {

    private lateinit var viewModel: MainViewModel
    var mapOfTeams: HashMap<String, Team> = HashMap()
    private lateinit var sortedTeams: List<Team>

    //For each game object that we get, we will create two team objects (if they were not made already)
    // for each team object we will add this game to their List of games, and also add either a win, a loss or a draw.

    //Then for the second page we will iterate through this teams games and create a  class called OpposingTeam
    //that will have the opposing teams, name, wins, losses, draws and totals games against.
//==============================================================================================================

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory =
                MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getGames()
        getNetworkResponse()
    }

    private fun getNetworkResponse() {
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val games = response.body()
                if (games != null) {
                    for (i in 0 until games.count()) {
                        var thisGame = Game(
                                games[i].gameId,
                                games[i].awayTeamId,
                                games[i].awayTeamName,
                                games[i].homeTeamId,
                                games[i].homeTeamName,
                                games[i].awayScore,
                                games[i].homeScore
                        )

                        var addOneIfHomeWon = if (games[i].homeScore > games[i].awayScore) 1 else 0
                        var addOneIfHomeLost = if (games[i].homeScore < games[i].awayScore) 1 else 0
                        var addDrawIfTie = if (games[i].homeScore == games[i].awayScore) 1 else 0
                        //for the home team
                        if (mapOfTeams.containsKey(thisGame.homeTeamName)) {
                            mapOfTeams.get(thisGame.homeTeamName)!!.games.add(thisGame)
                            mapOfTeams.get(thisGame.homeTeamName)!!.wins += addOneIfHomeWon
                            mapOfTeams.get(thisGame.homeTeamName)!!.losses += addOneIfHomeLost
                            mapOfTeams.get(thisGame.homeTeamName)!!.draws += addDrawIfTie
                        } else {
                            var listOfOneGame = arrayListOf<Game>()
                            listOfOneGame.add(thisGame)
                            mapOfTeams.put(
                                    thisGame.homeTeamName, Team(
                                    listOfOneGame, addOneIfHomeWon, addOneIfHomeLost,
                                    addDrawIfTie, games[i].homeTeamName, 0.0
                            )
                            )
                        }
                        //for the away team
                        if (mapOfTeams.containsKey(thisGame.awayTeamName)) {
                            mapOfTeams.get(thisGame.awayTeamName)!!.games.add(thisGame)
                            mapOfTeams.get(thisGame.awayTeamName)!!.wins += addOneIfHomeLost
                            mapOfTeams.get(thisGame.awayTeamName)!!.losses += addOneIfHomeWon
                            mapOfTeams.get(thisGame.awayTeamName)!!.draws += addDrawIfTie
                        } else {
                            var listOfOneGame = arrayListOf<Game>()
                            listOfOneGame.add(thisGame)
                            mapOfTeams.put(
                                    thisGame.awayTeamName, Team(
                                    listOfOneGame, addOneIfHomeWon, addOneIfHomeLost,
                                    addDrawIfTie, games[i].awayTeamName, 0.0
                            )
                            )
                        }
                    }
                }
                showData()
            } else {
                Log.d("ben", "this is an error")
            }
        })
    }

    private fun showData() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            sortedTeams = mapOfTeams.values.sortedByDescending { it.wins }


            for (mapEntry in sortedTeams) {
                var tempPercent =
                    mapEntry.wins.toDouble() / (mapEntry.wins.toDouble() + mapEntry.losses.toDouble() + mapEntry.draws.toDouble())
                mapEntry.percentage = tempPercent * 100.0
            }
            //ToDo: *UnComment out the next line if you want it to be sorted by win percentage
//            sortedTeams = mapOfTeams.values.sortedByDescending { it.percentage }

            adapter = StandingsAdapter(ArrayList(sortedTeams), this@MainActivity)
        }
    }

    override fun onItemClick(position: Int) {
        var clickedTeam = sortedTeams.get(position)
        Log.d(
                "ben", "item ${clickedTeam.teamName} was clicked with ${clickedTeam.wins} " +
                "wins and ${clickedTeam.losses} losses"
        )

        var intent = Intent(this, TeamActivity::class.java)
        intent.putExtra("team", clickedTeam)
        startActivity(intent)
    }
}