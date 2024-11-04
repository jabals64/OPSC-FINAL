// Updated AddObservationActivity.kt

package com.example.carspotteropsc7312poe.observation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.carspotteropsc7312poe.DashboardActivity
import com.example.carspotteropsc7312poe.R
import com.example.carspotteropsc7312poe.databinding.ActivityAddObservationBinding
import com.example.carspotteropsc7312poe.dataclass.UserObservation

class AddObservationActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityAddObservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddObservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        binding.btnSave.setOnClickListener {
            saveObservation()
        }

        binding.btnback.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    private fun saveObservation() {
        val carName = binding.etCarName.text.toString()
        val model = binding.etModel.text.toString()
        val carDescription = binding.etCarDescription.text.toString()
        val longitudeText = binding.etLongitude.text.toString()
        val latitudeText = binding.etLatitude.text.toString()
        val imageUrl = binding.etImageUrl.text.toString()

        binding.etCarName.text.clear()
        binding.etModel.text.clear()
        binding.etCarDescription.text.clear()
        binding.etLongitude.text.clear()
        binding.etLatitude.text.clear()
        binding.etImageUrl.text.clear()


        // Input validation
        if (carName.isBlank() || model.isBlank() || carDescription.isBlank() || imageUrl.isBlank()) {
            showToast("Please fill in all the required fields.")
            return
        }

        val longitude = longitudeText.toDoubleOrNull()
        val latitude = latitudeText.toDoubleOrNull()

        if (longitude == null || latitude == null) {
            showToast("Invalid longitude or latitude. Please enter valid numbers.")
            return
        }

        val observation = UserObservation(
            CarName = carName,
            Model = model,
            CarDescription = carDescription,
            longitude = longitude,
            latitude = latitude,
            imageUrl = imageUrl
        )

        // Save to Firebase and clear fields after saving
        val observationRef = database.child("observations").push()
        observationRef.setValue(observation)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Observation saved successfully!")
                    clearFields() // Clear input fields after saving
                } else {
                    showToast("Failed to save observation. Please try again.")
                }
            }
    }

    // Clear all input fields after observation is saved
    private fun clearFields() {
        binding.etCarName.text.clear()
        binding.etModel.text.clear()
        binding.etCarDescription.text.clear()
        binding.etLongitude.text.clear()
        binding.etLatitude.text.clear()
        binding.etImageUrl.text.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
