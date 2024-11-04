package com.example.carspotteropsc7312poe.dataclass

data class CarObservation(
    val CarCode: String,
    val CarName: String,
    val locId: String,
    val locName: String,
    val obsDt: String,
    val howMany: Int,
    val lat: Double,
    val lng: Double
) {
    init {
        if (howMany < 0) {
            throw IllegalArgumentException("howMany should not be negative.")
        }
        if (lat < -90.0 || lat > 90.0) {
            throw IllegalArgumentException("Invalid latitude value. Latitude should be between -90 and 90.")
        }
        if (lng < -180.0 || lng > 180.0) {
            throw IllegalArgumentException("Invalid longitude value. Longitude should be between -180 and 180.")
        }

        if (CarCode.isEmpty()) {
            throw IllegalArgumentException("CarCode should not be empty.")
        }
        if (CarName.isEmpty()) {
            throw IllegalArgumentException("CarName should not be empty.")
        }


        if (locId.isEmpty()) {
            throw IllegalArgumentException("locId should not be empty.")
        }
        if (locName.isEmpty()) {
            throw IllegalArgumentException("locName should not be empty.")
        }
        if (obsDt.isEmpty()) {
            throw IllegalArgumentException("obsDt should not be empty.")
        }
    }
}