package com.example.carspotteropsc7312poe.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.example.carspotteropsc7312poe.R
import com.example.carspotteropsc7312poe.DashboardActivity
import com.example.carspotteropsc7312poe.adapter.CarAdapter
import com.example.carspotteropsc7312poe.dataclass.CarObservation
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FirstActivity : AppCompatActivity() {
    private lateinit var carRecyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecar)

        val btnOBSER = findViewById<Button>(R.id.bottomButton)

        carRecyclerView = findViewById(R.id.carRecyclerView)
        carRecyclerView.layoutManager = LinearLayoutManager(this)

        // Logout button click listener
        btnOBSER.setOnClickListener()
        {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent) // Redirects to the main activity (login page)
        }

        fetchCarObservations()
    }

    private fun fetchCarObservations() {
        Thread {
            try {
                val apiKey = "" // Replace with your Car API key
                val url = URL("")
                val connection = url.openConnection() as HttpURLConnection

                connection.setRequestProperty("x-ebirdapitoken", apiKey)

                if (connection.responseCode == 200) {
                    val inputSystem = connection.inputStream
                    val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                    val response = Gson().fromJson(inputStreamReader, Array<CarObservation>::class.java)

                    runOnUiThread {
                        carRecyclerView.adapter = CarAdapter(response.toList())
                    }

                    inputStreamReader.close()
                    inputSystem.close()
                } else {
                    runOnUiThread {
                        // Handle failed connection
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    // Handle other exceptions
                }
            }
        }.start()
    }
}
