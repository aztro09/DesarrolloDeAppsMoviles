package com.example.f1adventure

data class Track(
    val name: String,
    val location: String,
    val lengthKm: Double,
    val turns: Int,
    val firstRace: Int,
    val characteristics: String,
    val bestDriver: String,
    val trackImageRes: Int,
    val driverImageRes: Int
)