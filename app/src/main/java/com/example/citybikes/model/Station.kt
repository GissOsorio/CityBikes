package com.example.citybikes.model

data class Station(
    val empty_slots: Any,
    val extra: Extra,
    val free_bikes: Int,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val timestamp: String
)