package com.example.zavenearbyshoppingassistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SearchQuery(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity
data class CachedStore(
    @PrimaryKey val placeId: String,
    val name: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val iconUrl: String
)

data class PlacesResponse(val results: List<PlaceResult>)
data class PlaceResult(
    @SerializedName("place_id") val placeId: String,
    val name: String,
    val vicinity: String,
    val geometry: Geometry,
    val icon: String
)
data class Geometry(val location: LocationData)
data class LocationData(val lat: Double, val lng: Double)
