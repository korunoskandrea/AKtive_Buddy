package com.example.aktivebuddy.database.model

import kotlinx.serialization.Serializable
import org.osmdroid.util.GeoPoint
import java.util.UUID

data class Location(
    val country: String,
    val city: String,
    val street: String,
    val x: Double,
    val y: Double
) {
}
