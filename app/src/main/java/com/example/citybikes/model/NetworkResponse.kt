package com.example.citybikes.model

data class NetworkResponse(
    val company: List<String>?,
    val ebikes: Boolean?,
    val gbfs_href: String?,
    val href: String?,
    val id: String,
    val license: License?,
    val location: Location,
    val name: String,
    val source: String?
)