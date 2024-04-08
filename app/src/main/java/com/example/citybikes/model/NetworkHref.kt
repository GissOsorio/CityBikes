package com.example.citybikes.model

data class NetworkHref(
    val company: List<String>,
    val href: String,
    val id: String,
    val city: String,
    val country: String,
    val name: String,
    val stations: List<Station>,
)