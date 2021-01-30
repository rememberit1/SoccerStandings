package com.example.SoccerStandings.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.SoccerStandings.R
import com.example.SoccerStandings.models.OpposingTeam
import kotlinx.android.synthetic.main.opposing_list_item.view.*

class TeamAdapter(private var opposingTeams: List<OpposingTeam>) :
    RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.opposing_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return opposingTeams.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var team = opposingTeams[position]
        holder.itemView.opp_teamName_tv.text = team.name
        holder.itemView.opp_wins_tv.text = team.winsAgainst.toString()
        holder.itemView.opp_losses_tv.text = team.lossesAgainst.toString()
        holder.itemView.opp_draws_tv.text = team.drawsAgainst.toString()
        holder.itemView.opp_total_tv.text = team.totalGamesAgainst.toString()
    }
}