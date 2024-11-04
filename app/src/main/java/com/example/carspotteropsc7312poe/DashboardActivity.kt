package com.example.carspotteropsc7312poe

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.carspotteropsc7312poe.activities.FirstActivity
import com.example.carspotteropsc7312poe.dataclass.Car
import com.example.carspotteropsc7312poe.location.MapboxActivity
import com.example.carspotteropsc7312poe.observation.ObservationActivity
import com.example.carspotteropsc7312poe.settings.SettingsActivity

class DashboardActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize UI elements
        val btnLogouts = findViewById<Button>(R.id.btnLogout)
        val btnLocateCars = findViewById<Button>(R.id.btnLocateCars)
        val btnObservations = findViewById<Button>(R.id.btnObservations)
        val btnExplore = findViewById<Button>(R.id.btnExplore)
        val btnKnowledge = findViewById<Button>(R.id.btnKnowledge)

        // Button click listeners

        // Logout button click listener
        btnLogouts.setOnClickListener()
        {
            btnSignOut() // Calls the function to sign out the user
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Redirects to the main activity (login page)
        }

        // Locate Cars button click listener
        btnLocateCars.setOnClickListener()
        {
            val intent = Intent(this, MapboxActivity::class.java)
            startActivity(intent) // Redirects to the Mapbox activity for locating cars
        }

        // Observations button click listener
        btnObservations.setOnClickListener()
        {
            val intent = Intent(this, ObservationActivity::class.java)
            startActivity(intent) // Redirects to the Observation activity
        }

        // Explore button click listener
        btnExplore.setOnClickListener()
        {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent) // Redirects to the settings activity
        }

        // Knowledge button click listener
        btnKnowledge.setOnClickListener()
        {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent) // Redirects to the knowledge activity
        }

        // Display a random car when the activity is created
        displayRandomCar()
    }

    // Car data
    private val carList = listOf(
        Car("Porsche Cayenne", "The Cayenne S...", R.drawable.porsche),
        Car("Mercedes GLE", "The Mercedes-Benz GLE...", R.drawable.mercedes_gle),
        Car("Ferrari F430", "The Ferrari F430...", R.drawable.ferrari),
        Car("Rolls Royce Phantom", "The Phantom is a 5 seater...", R.drawable.rolls_royce),
        Car("Bentley Bentayga", "This stunning Bentley...", R.drawable.bentley_bentayga)
    )


    // Function to display a random car
    private fun displayRandomCar() {
        val randomIndex = (carList.indices).random()
        val randomCar = carList[randomIndex]

        // Update the ImageView
        val carImageView = findViewById<ImageView>(R.id.carImageView)
        carImageView.setImageResource(randomCar.imageResId)

        // Update the TextViews using the correct IDs
        val carNameTextView = findViewById<TextView>(R.id.carNameTextView) // Correct ID from XML
        val carDescriptionTextView = findViewById<TextView>(R.id.carDescriptionTextView) // Correct ID from XML
        carNameTextView.text = randomCar.name
        carDescriptionTextView.text = randomCar.description
    }

    // Function to sign out the user
    private fun btnSignOut() {
        Firebase.auth.signOut() // Sign out the user using Firebase Authentication
        Toast.makeText(this, "Goodbye User: Signed Out", Toast.LENGTH_LONG).show() // Display a toast message indicating the user is signed out
    }
}