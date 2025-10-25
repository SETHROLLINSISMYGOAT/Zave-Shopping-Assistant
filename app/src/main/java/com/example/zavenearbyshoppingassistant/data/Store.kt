package com.example.zavenearbyshoppingassistant.data

data class Store(
    val placeId: String,
    val name: String,
    val vicinity: String,
    val lat: Double,
    val lng: Double,
    val icon: String
)
