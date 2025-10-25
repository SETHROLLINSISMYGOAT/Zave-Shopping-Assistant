package com.example.zavenearbyshoppingassistant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zavenearbyshoppingassistant.data.RemoteConfigHelper

class SettingsActivity : AppCompatActivity() {

    private lateinit var rc: RemoteConfigHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        rc = RemoteConfigHelper(this)

        val etRadius = findViewById<EditText>(R.id.et_radius)
        val etCategory = findViewById<EditText>(R.id.et_category)
        val btnSave = findViewById<Button>(R.id.btn_save_settings)

        etRadius.setText(rc.getInt(RemoteConfigHelper.KEY_RADIUS).toString())
        etCategory.setText(rc.getString(RemoteConfigHelper.KEY_FEATURED))

        btnSave.setOnClickListener {
            val radiusStr = etRadius.text.toString()
            val category = etCategory.text.toString().trim()

            if (radiusStr.isNotEmpty()) rc.setInt(RemoteConfigHelper.KEY_RADIUS, radiusStr.toInt())
            if (category.isNotEmpty()) rc.setString(RemoteConfigHelper.KEY_FEATURED, category)

            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show()


            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}
