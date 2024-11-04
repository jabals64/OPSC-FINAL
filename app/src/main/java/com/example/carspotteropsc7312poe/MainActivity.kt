package com.example.carspotteropsc7312poe

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.carspotteropsc7312poe.authentication.LoginActivity
import com.example.carspotteropsc7312poe.dataclass.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Request notification permission if the device is running Android 13 (API level 33) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }

        // Find the login button in the layout by its ID
        val btnLogin = findViewById<Button>(R.id.loginButton)

        // Set an OnClickListener to handle the button click
        btnLogin.setOnClickListener {
            // Create an Intent to navigate to the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)

            // Start the LoginActivity when the button is clicked
            try {
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()

                // Handle the exception by displaying a toast message with an error message
                val errorMessage = "An error occurred: ${e.message}"
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        // Schedule Sync Worker
        scheduleSync(this)
    }

    private fun scheduleSync(context: Context) {
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(24, TimeUnit.HOURS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "SyncDataWork",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    // Function to request notification permission for Android 13 or higher
    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Register the request permission launcher to ask for the POST_NOTIFICATIONS permission
            val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                    if (isGranted) {
                        // Permission is granted, you can proceed with notifications
                        Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
                    } else {
                        // Permission denied, inform the user that notifications will be disabled
                        Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
                    }
                }

            // Launch the permission request
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
