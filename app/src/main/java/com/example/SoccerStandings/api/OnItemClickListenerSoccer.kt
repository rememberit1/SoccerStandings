package com.example.SoccerStandings.api

//we create a interface for the click listener so that the adapter can be used in the future in many
// and isn't tightly coupled.
interface OnItemClickListenerSoccer {
    fun onItemClick(position: Int)
}