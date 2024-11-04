package com.example.carspotteropsc7312poe.dataclass

data class UserObservation(
    var CarName: String = "",       // Car model spotted (default: empty string)
    var Model: String = "",
    var CarDescription: String = "",
    var dateTime: String = "",       // Date and time of the observation (default: empty string)
    var latitude: Double = 0.0,      // Latitude of the observation location (default: 0.0)
    var longitude: Double = 0.0,     // Longitude of the observation location (default: 0.0)
    var notes: String = "",          // Additional notes about the spotting (default: empty string)
    var imageUrl: String = ""        // URL of the uploaded image (default: empty string)
) {

    // Optional secondary constructor, if you want an empty object with all default values
    constructor() : this("", "", "","",0.0, 0.0, "", "")
}