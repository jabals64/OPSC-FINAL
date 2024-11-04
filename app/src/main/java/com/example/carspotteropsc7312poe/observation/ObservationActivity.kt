package com.example.carspotteropsc7312poe.observation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.carspotteropsc7312poe.DashboardActivity
import com.example.carspotteropsc7312poe.R
import com.example.carspotteropsc7312poe.adapter.CarObservationAdapter
import com.example.carspotteropsc7312poe.databinding.ActivityRecyclerviewBinding
import com.example.carspotteropsc7312poe.dataclass.UserObservation

class ObservationActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityRecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readData()

        val buttonAddObservation = findViewById<Button>(R.id.buttonAddObservation)
        buttonAddObservation.setOnClickListener {
            val intent = Intent(this, AddObservationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun readData() {
        Log.d("ObservationActivity", "Fetching data from Firebase")

        database.child("observations").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val observationList = mutableListOf<UserObservation>()

                for (childSnapshot in snapshot.children) {
                    val observation = childSnapshot.getValue(UserObservation::class.java)
                    observation?.let { observationList.add(it) }
                }

                val adapter = CarObservationAdapter(observationList)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                showToast("Data loaded successfully!")
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to load data. Please try again.")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
