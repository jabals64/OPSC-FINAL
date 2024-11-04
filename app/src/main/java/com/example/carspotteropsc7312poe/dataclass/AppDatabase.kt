package com.example.carspotteropsc7312poe.dataclass // Ensure this path is correct

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Abstract method to get the DAO
    abstract fun carDao(): CarDao

    companion object {
        // Volatile ensures that multiple threads handle the INSTANCE variable correctly
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Function to get the database instance
        fun getDatabase(context: Context): AppDatabase {
            // Check if the database instance is null
            return INSTANCE ?: synchronized(this) {
                // Create the database instance
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Use application context to prevent memory leaks
                    AppDatabase::class.java,
                    "car_database" // The name of the database
                ).build()
                // Assign the newly created instance to INSTANCE
                INSTANCE = instance
                instance // Return the instance
            }
        }
    }
}
