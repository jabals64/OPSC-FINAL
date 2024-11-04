package com.example.carspotteropsc7312poe.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

// Annotate the class with @Entity to define it as a Room database table
@Entity(tableName = "cars")
data class Car(
    @PrimaryKey val name: String,   // Assuming "name" is unique; otherwise, use a different unique ID
    val description: String,
    val imageResId: Int
) {
    init {
        // Ensure that the image resource ID is a valid resource
        if (imageResId <= 0) {
            throw IllegalArgumentException("Invalid image resource ID: $imageResId. It should be a valid resource ID.")
        }
    }
}
