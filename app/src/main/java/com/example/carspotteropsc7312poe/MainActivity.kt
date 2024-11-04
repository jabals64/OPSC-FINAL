package com.example.carspotteropsc7312poe

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.carspotteropsc7312poe.authentication.LoginActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.carspotteropsc7312poe.dataclass.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        fun scheduleSync(context: Context) {
            val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(24, TimeUnit.HOURS).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "SyncDataWork",
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest
            )
        }
    }
}