package com.example.carspotteropsc7312poe.dataclass

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarDao {
    // Insert a new Car entry or replace if it already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: Car)

    // Retrieve all cars from the database
    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<Car>

    // (Optional) Delete a specific car by name (or by ID if added)
    @Query("DELETE FROM cars WHERE name = :carName")
    suspend fun deleteCarByName(carName: String)
}
