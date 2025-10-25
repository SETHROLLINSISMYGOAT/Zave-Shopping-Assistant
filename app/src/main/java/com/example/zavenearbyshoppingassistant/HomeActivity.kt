package com.example.zavenearbyshoppingassistant

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zavenearbyshoppingassistant.data.Repo
import com.example.zavenearbyshoppingassistant.data.RemoteConfigHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var rc: RemoteConfigHelper
    private lateinit var repo: Repo
    private lateinit var fused: FusedLocationProviderClient

    private val requestPerms =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            Toast.makeText(this, "Permissions updated. Retry search.", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rc = RemoteConfigHelper(this)
        repo = Repo(this, rc)
        fused = LocationServices.getFusedLocationProviderClient(this)

        val tvBanner = findViewById<TextView>(R.id.tv_banner)
        val etQuery = findViewById<EditText>(R.id.et_query)
        val btnSearch = findViewById<Button>(R.id.btn_search)
        val llRecent = findViewById<LinearLayout>(R.id.ll_recent)
        val btnSettings = findViewById<Button>(R.id.btn_settings)

        tvBanner.text = rc.getString(RemoteConfigHelper.KEY_BANNER)


        CoroutineScope(Dispatchers.Main).launch {
            val recent = repo.getRecentSearches()
            llRecent.removeAllViews()
            recent.forEach { q ->
                val tv = TextView(this@HomeActivity)
                tv.text = "- ${q.query}"
                tv.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                llRecent.addView(tv)
            }
        }

    
        btnSearch.setOnClickListener {
            val query = etQuery.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(this, "Enter query", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!hasLocationPermission()) {
                requestPerms.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
                Toast.makeText(this, "Grant location and retry", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { loc: Location? ->
                        if (loc == null) {
                            Toast.makeText(
                                this,
                                "Unable to get location. Make sure GPS is ON",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@addOnSuccessListener
                        }

                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                val results = repo.searchNearbyBlocking(loc, query)
                                if (results.isEmpty()) {
                                    Toast.makeText(
                                        this@HomeActivity,
                                        "No stores found nearby",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@launch
                                }
                                ResultsHolder.stores = results
                                startActivity(Intent(this@HomeActivity, ResultsActivity::class.java))
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(
                                    this@HomeActivity,
                                    "Search failed: ${e.localizedMessage}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
            } catch (se: SecurityException) {
                Toast.makeText(
                    this,
                    "Location permission denied. Cannot perform search.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        return fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED
    }
}
