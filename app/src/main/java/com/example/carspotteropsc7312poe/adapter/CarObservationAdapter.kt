package com.example.carspotteropsc7312poe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carspotteropsc7312poe.R
import com.example.carspotteropsc7312poe.dataclass.CarObservation
import com.example.carspotteropsc7312poe.databinding.ItemCarObservationBinding
import com.example.carspotteropsc7312poe.dataclass.UserObservation
import com.squareup.picasso.Picasso

class CarObservationAdapter(private val observations: List<UserObservation>) : RecyclerView.Adapter<CarObservationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarObservationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val observation = observations[position]
        holder.bind(observation)
    }

    override fun getItemCount(): Int {
        return observations.size
    }

    inner class ViewHolder(private val binding: ItemCarObservationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(observation: UserObservation) {
            binding.tvCarName.text = observation.CarName
            binding.tvModel.text = observation.Model
            binding.tvCarDescription.text = observation.CarDescription
            binding.tvLongitude.text = "Longitude: ${observation.longitude}"
            binding.tvLatitude.text = "Latitude: ${observation.latitude}"

            // Check if the imageUrl is not empty or null before loading with Picasso
            if (!observation.imageUrl.isNullOrEmpty()) {
                // Load image using Picasso
                Picasso.get().load(observation.imageUrl).into(binding.imageView)
            } else {
                binding.imageView.setImageResource(R.drawable.icon)
            }
        }
    }
}