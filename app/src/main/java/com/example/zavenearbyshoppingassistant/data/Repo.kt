package com.example.zavenearbyshoppingassistant.data

import android.content.Context
import android.location.Location
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

data class NominatimResult(
    val display_name: String,
    val lat: String,
    val lon: String,
    val type: String
)

class Repo(private val context: Context, private val rc: RemoteConfigHelper) {
    private val db = AppDatabase.getInstance(context)
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun searchNearbyBlocking(location: Location, query: String) = withContext(Dispatchers.IO) {
        val radiusKm = rc.getInt(RemoteConfigHelper.KEY_RADIUS, 40)
        val delta = radiusKm.toDouble() / 111.0

        val viewbox = "${location.longitude - delta},${location.latitude - delta}," +
                "${location.longitude + delta},${location.latitude + delta}"

        val url = "https://nominatim.openstreetmap.org/search?format=json&q=${java.net.URLEncoder.encode(query, "utf-8")}" +
                "&bounded=1&viewbox=$viewbox"

        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "ZaveNearbyApp")
            .build()

        val response = client.newCall(request).execute()
        val body = response.body?.string() ?: "[]"
        val results: List<NominatimResult> = gson.fromJson(body, Array<NominatimResult>::class.java).toList()

        val stores = results.mapIndexed { index, it ->
            CachedStore(
                placeId = "osm-${index}",
                name = it.display_name,
                address = it.display_name,
                lat = it.lat.toDouble(),
                lng = it.lon.toDouble(),
                iconUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7c/OOjs_UI_icon_shop.svg"
            )
        }

        stores.forEach { db.appDao().insertStore(it) }
        stores
    }

    suspend fun getRecentSearches() = withContext(Dispatchers.IO) {
        db.appDao().getRecentSearches()
    }
}
