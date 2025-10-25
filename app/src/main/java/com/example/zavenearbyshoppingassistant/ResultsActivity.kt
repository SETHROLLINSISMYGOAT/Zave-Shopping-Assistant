package com.example.zavenearbyshoppingassistant

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        val rv = findViewById<RecyclerView>(R.id.rv_stores)
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = StoreAdapter(ResultsHolder.stores) { store ->
            val uri = Uri.parse("geo:${store.lat},${store.lng}?q=${Uri.encode(store.name)}")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply { setPackage("com.google.android.apps.maps") }
            startActivity(intent)
        }
        rv.adapter = adapter
    }
}
