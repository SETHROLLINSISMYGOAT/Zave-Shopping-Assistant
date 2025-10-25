package com.example.zavenearbyshoppingassistant

import android.app.Application
import com.google.android.libraries.places.api.Places

class ZaveApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(applicationContext, apiKey)
    }
}
