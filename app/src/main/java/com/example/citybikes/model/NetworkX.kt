package com.example.citybikes.model

data class NetworkX(
    val company: List<String>,
    val href: String,
    val id: String,
    val location: LocationX,
    val name: String,
    val stations: List<Station>
)