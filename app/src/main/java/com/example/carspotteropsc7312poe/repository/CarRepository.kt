package com.example.carspotteropsc7312poe.repository

import com.example.carspotteropsc7312poe.dataclass.Car
import com.example.carspotteropsc7312poe.dataclass.CarDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CarRepository(private val carDao: CarDao) { // Only DAO is needed

    // Insert a car into Room Database
    suspend fun insertCar(car: Car) {
        withContext(Dispatchers.IO) {
            carDao.insert(car)
        }
    }

    // Get all cars from Room Database
    suspend fun getAllCars(): List<Car> {
        return withContext(Dispatchers.IO) {
            carDao.getAllCars()
        }
    }

    fun syncCarsWithRemote(cars: List<Car>) {
        // Implementation for syncing cars with a remote database/service
    }
}
