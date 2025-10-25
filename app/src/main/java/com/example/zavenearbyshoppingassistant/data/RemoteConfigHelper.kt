package com.example.zavenearbyshoppingassistant.data

import android.content.Context

class RemoteConfigHelper(context: Context) {
    private val prefs = context.getSharedPreferences("app_config", Context.MODE_PRIVATE)

    companion object {
        const val KEY_RADIUS = "default_radius_km"
        const val KEY_BANNER = "banner_message"
        const val KEY_FEATURED = "featured_category"
    }

    init {
        if (!prefs.contains(KEY_RADIUS)) {
            prefs.edit()
                .putInt(KEY_RADIUS, 3)
                .putString(KEY_BANNER, "Explore deals near you!")
                .putString(KEY_FEATURED, "electronics")
                .apply()
        }
    }

    fun getInt(key: String, default: Int = 0) = prefs.getInt(key, default)
    fun getString(key: String, default: String = "") = prefs.getString(key, default) ?: default
    fun setInt(key: String, value: Int) = prefs.edit().putInt(key, value).apply()
    fun setString(key: String, value: String) = prefs.edit().putString(key, value).apply()
}
