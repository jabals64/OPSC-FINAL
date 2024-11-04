package com.example.carspotteropsc7312poe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carspotteropsc7312poe.R
import com.example.carspotteropsc7312poe.dataclass.CarObservation

class CarAdapter(private val carList: List<CarObservation>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carNameTextView: TextView = itemView.findViewById(R.id.carNameTextView)

        val locNameTextView: TextView = itemView.findViewById(R.id.locNameTextView)
        val obsDtTextView: TextView = itemView.findViewById(R.id.obsDtTextView)
        val howManyTextView: TextView = itemView.findViewById(R.id.howManyTextView)
        val latTextView: TextView = itemView.findViewById(R.id.latTextView)
        val lngTextView: TextView = itemView.findViewById(R.id.lngTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for each car list item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Retrieve the CarObservation object for the current position
        val car = carList[position]

        // Set the text for each TextView in the ViewHolder
        holder.carNameTextView.text = "Common Name: ${car.CarName}"

        holder.locNameTextView.text = "Location: ${car.locName}"
        holder.obsDtTextView.text = "Observation Date: ${car.obsDt}"
        holder.howManyTextView.text = "How Many: ${car.howMany}"
        holder.latTextView.text = "Latitude: ${car.lat}"
        holder.lngTextView.text = "Longitude: ${car.lng}"
    }

    override fun getItemCount(): Int {
        // Return the size of the birdList
        return carList.size
    }
}