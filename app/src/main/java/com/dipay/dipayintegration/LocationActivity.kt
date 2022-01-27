package com.example.dipayintegration

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import com.google.android.material.button.MaterialButton


class LocationActivity : AppCompatActivity() {

    private var mLocationRequest: LocationRequest? = null

    private val UPDATE_INTERVAL = (10 * 1000 /* 10 secs */).toLong()
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    private val btnGetLocation by lazy {
        findViewById<MaterialButton>(R.id.btnGetLocation)
    }

    private val tvLocation by lazy {
        findViewById<TextView>(R.id.tvLocation)
    }

    private fun checkPlayServices(): Boolean {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(this)
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, 1000)
                    .show()
            }
            return false
        }
        return true
    }

    protected fun startLocationUpdates() {

        // Create the location request to start receiving updates

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val isGmsAvailable = checkPlayServices()

        if (!isGmsAvailable) {
            tvLocation.text = "GMS not available"
            btnGetLocation.isEnabled = false
        }
        btnGetLocation.setOnClickListener {
            getLocation()
       }


    }

    private fun OnGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton(
            "Yes"
        ) { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.cancel() }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            mLocationRequest = LocationRequest.create()
            mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest?.interval = UPDATE_INTERVAL
            mLocationRequest?.fastestInterval = FASTEST_INTERVAL

            // Create LocationSettingsRequest object using location request
            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(mLocationRequest!!)
            val locationSettingsRequest = builder.build()

            // Check whether location settings are satisfied
            // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
            val settingsClient = LocationServices.getSettingsClient(this)
            settingsClient.checkLocationSettings(locationSettingsRequest)

            // new Google API SDK v11 uses getFusedLocationProviderClient(this)
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(
                mLocationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        // do work here
                        onLocationChanged(locationResult.lastLocation)
                    }
                },
                Looper.getMainLooper()
            )
        }
    }

    fun onLocationChanged(location: Location) {
        // New location has now been determined
        val lat: Double = location.latitude
        val longi: Double = location.longitude
        tvLocation.text = """
                    Success get Location
                    Lat = $lat
                    Lng = $longi """
    }

    companion object {
        const val REQUEST_LOCATION = 1

    }
}