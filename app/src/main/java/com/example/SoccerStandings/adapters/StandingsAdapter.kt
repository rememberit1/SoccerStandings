package com.example.SoccerStandings.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.SoccerStandings.api.OnItemClickListenerSoccer
import com.example.SoccerStandings.R
import com.example.SoccerStandings.models.Team
import kotlinx.android.synthetic.main.standings_list_item.view.*

class StandingsAdapter(private var teams: List<Team>, private val listener: OnItemClickListenerSoccer) :
    RecyclerView.Adapter<StandingsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            var position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.standings_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var team = teams[position]
        holder.itemView.teamName_tv.text = team.teamName
        holder.itemView.wins_tv.text = team.wins.toString()
        holder.itemView.losses_tv.text = team.losses.toString()
        holder.itemView.draws_tv.text = team.draws.toString()
        holder.itemView.percent_tv.text = String.format("%.0f", team.percentage) + "%"
    }


}