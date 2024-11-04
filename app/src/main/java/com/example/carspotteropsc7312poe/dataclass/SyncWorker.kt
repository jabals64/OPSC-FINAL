package com.example.carspotteropsc7312poe.dataclass

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.carspotteropsc7312poe.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val carDao = AppDatabase.getDatabase(applicationContext).carDao()
                val cars = carDao.getAllCars()

                // Call your repository's sync method
                val carRepository = CarRepository(carDao) // No need to pass context
                carRepository.syncCarsWithRemote(cars)

                Result.success()
            } catch (e: Exception) {
                // Log the exception if necessary
                // Log.e("SyncWorker", "Error syncing cars", e) // Uncomment if you want to log
                Result.retry()
            }
        }
    }
}
