package com.example.zavenearbyshoppingassistant.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

object PlacesService {
    private val client = OkHttpClient()
    private val gson = Gson()
    private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"

    @Throws(Exception::class)
    fun nearbySearch(location: String, radius: Int, keyword: String, apiKey: String): PlacesResponse {
        val encodedKeyword = URLEncoder.encode(keyword, "utf-8")
        val url = "$BASE_URL?location=$location&radius=$radius&type=store&keyword=$encodedKeyword&key=$apiKey"

        val request = Request.Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            val body = response.body?.string() ?: throw Exception("Empty response from Places API")
            if (!response.isSuccessful) throw Exception("Places API error: ${response.message}")
            return gson.fromJson(body, PlacesResponse::class.java)
        }
    }
}
