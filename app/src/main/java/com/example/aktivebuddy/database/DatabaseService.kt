package com.example.aktivebuddy.database

import android.util.Log
import com.example.aktivebuddy.database.model.Fitness
import com.example.aktivebuddy.database.model.Location
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import org.osmdroid.util.GeoPoint

object DatabaseService {
    private val db = Firebase.firestore

    // add location
    fun addLocationData(country: String, city: String, street: String, point: GeoPoint) {
        val location = hashMapOf(
            "country" to country,
            "city" to city,
            "street" to street,
            "point" to hashMapOf(
                "x" to point.latitude,
                "y" to point.longitude
            )
        )

        db.collection("location").add(location)
            .addOnSuccessListener { documentReference ->
                Log.d("DB", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("DB", "Error adding document", e)
            }
    }

    // add fitness
    fun addFitnessData(location: Location, name: String) {
        val fitness = hashMapOf(
            "name" to name,
            "location" to location,
        )

        db.collection("fitness").add(fitness)
            .addOnSuccessListener { documentReference ->
                Log.d("DB", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("DB", "Error adding document", e)
            }
    }

    // get fitness -> Updated getFitnessData to return a Deferred<List<Fitness>>
    suspend fun getAllFitnessLocations(): List<Fitness> {
        val fitnessList = mutableListOf<Fitness>()

        try {
            val fitnessDocuments = db.collection("fitness").get().await()

            for (document in fitnessDocuments) {
                val name = document.getString("name") ?: ""

                // Assuming you have stored the location data as a nested map
                val locationData = document.get("location") as? Map<String, Any> ?: mapOf()
                val locationCity = locationData["city"] as? String ?: ""
                val locationCountry = locationData["country"] as? String ?: ""
                val locationStreet = locationData["street"] as? String ?: ""
                val locationX = locationData["x"] as? Double ?: 0.0
                val locationY = locationData["y"] as? Double ?: 0.0

                val fitness = Fitness(name, Location(locationCity, locationCountry, locationStreet, locationX, locationY))
                fitnessList.add(fitness)
            }
        } catch (e: Exception) {
            Log.d("DB", "Error getting documents", e)
        }

        return fitnessList
    }

}