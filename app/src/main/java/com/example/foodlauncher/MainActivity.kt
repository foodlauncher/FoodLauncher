package com.example.foodlauncher

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodlauncher.MapActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isServicesOK) {
            init()
        }
    }

    private fun init() {
        val btnMap = findViewById<View>(R.id.btnMap) as Button
        btnMap.setOnClickListener {
            val intent = Intent(this@MainActivity, MapActivity::class.java)
            startActivity(intent)
        }
    }// an error occurred but we can resolve it

    // everything is fine and the user can make map requests
    val isServicesOK: Boolean
        get() {
            Log.d(TAG, "isServicesOK(): Checking Google Play services version")
            val available: Int = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this@MainActivity)
            if (available == ConnectionResult.SUCCESS) { // everything is fine and the user can make map requests
                Log.d(TAG, "isServicesOK(): Google Play services is working!")
                return true
            } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) { // an error occurred but we can resolve it
                Log.d(TAG, "isServicesOK(): an error occurred but we can resolve it")
                val dialog: Dialog = GoogleApiAvailability.getInstance().getErrorDialog(this@MainActivity, available, ERROR_DIALOG_REQUEST)
                dialog.show()
            } else {
                Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show()
            }
            return false
        }

    companion object {
        private const val TAG = "MainActivity"
        private const val ERROR_DIALOG_REQUEST = 9001
    }
}