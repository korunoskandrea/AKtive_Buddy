package com.example.aktivebuddy

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.aktivebuddy.database.DatabaseService
import com.example.aktivebuddy.database.model.Fitness
import com.example.aktivebuddy.databinding.ActivityMainBinding
import com.example.aktivebuddy.fragments.FitnessFragment
import com.example.aktivebuddy.fragments.MapFragment
import com.example.aktivebuddy.fragments.WelcomeFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.api.IMapController
import org.osmdroid.util.GeoPoint
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    lifecycleScope.launch {
                        val fitnessList = try {
                            DatabaseService.getAllFitnessLocations()
                        } catch (e: Exception) {
                            emptyList() // Handle the error gracefully
                        }
                        for (fitness in fitnessList) {
                            val distance = sqrt(Math.pow(fitness.location.x - location.latitude, 2.0) + Math.pow(fitness.location.y - location.longitude, 2.0))
                            if (distance <= 0.01) {
                                pushNearFitnessNotification(fitness)
                                break
                            }
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d("my-loc", "${it.message}")
            }

        setContentView(binding.root)

        createNotificationChannel()

        val notificationManager = NotificationManagerCompat.from(this)


        val redirect = intent.getStringExtra("redirect");
        if (redirect == "map-fragment") {
            val x = intent.getDoubleExtra("redirect-x", 0.0);
            val y = intent.getDoubleExtra("redirect-y", 0.0);
            replaceFragment(
                MapFragment(GeoPoint(x, y))
            )
        } else {
            replaceFragment(
                WelcomeFragment(
                    onFitnessClick = {
                        replaceFragment(FitnessFragment { fitness -> onFitnessClicked(fitness) })
                    },
                    onLoginClick = {
                        replaceFragment(MapFragment())
                    }
                )
            )
        }

        binding.backBtn.setOnClickListener {
            replaceFragment(
                WelcomeFragment(
                    onFitnessClick = {
                        replaceFragment(FitnessFragment { fitness -> onFitnessClicked(fitness) })
                    },
                    onLoginClick = {
                        replaceFragment(MapFragment())
                    }
                )
            )
        }
    }


    @SuppressLint("MissingPermission")
    private fun pushNearFitnessNotification(fitness: Fitness) {
        var mapController: IMapController
        val resultIntent = Intent(this, MainActivity::class.java)
        resultIntent.putExtra("redirect", "map-fragment")
        resultIntent.putExtra("redirect-x", fitness.location.x)
        resultIntent.putExtra("redirect-y", fitness.location.y)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack.
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack.
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val notificationBuiler = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Hey, here is a fitness near you?")
            .setContentText(fitness.name)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(resultPendingIntent).setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, notificationBuiler.build())
        }
    }

    private fun onFitnessClicked(fitness: GeoPoint) {
        replaceFragment(MapFragment(fitness))
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

// adding a element into database
//        adding fitnesses in database while going to fitness fragment
//        val country: String = "Slovenia"
//        val city: Strinxg = "Maribor"
//        val street1: String = "Ulica Eve Lovše 15"
//        val point1: GeoPoint = GeoPoint(46.537650, 15.64910)
//        val latitude = point1.latitude
//        val longitude = point1.longitude
//        val location: Location = Location(country, city, street1, latitude, longitude)
//
//        val name: String = "UNIFit Betnava"