package com.example.citybikes.model

data class Network(
    val company: List<String>?,
    val href: String?,
    val id: String,
    val city: String,
    val country: String,
    val countryId: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val source: String?
)