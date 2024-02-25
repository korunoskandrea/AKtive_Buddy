package com.example.aktivebuddy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.aktivebuddy.R
import com.example.aktivebuddy.database.DatabaseService
import com.example.aktivebuddy.database.model.Fitness
import com.example.aktivebuddy.databinding.MapFragmentBinding
import kotlinx.coroutines.launch
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlin.math.log

class MapFragment(private val fitnessPoint: GeoPoint? = null) : Fragment(R.layout.map_fragment) {
    private lateinit var binding: MapFragmentBinding
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map: MapView
    private lateinit var mapController: IMapController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MapFragmentBinding.inflate(inflater, container, false)
        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.setUseDataConnection(true)
        mapController = map.controller
        // Center the map
        if (fitnessPoint != null) {
            mapController.setCenter(fitnessPoint)
            mapController.setZoom(19.0)

        } else {
            val mariborCoordinates = GeoPoint(46.5547, 15.6459)
            mapController.setCenter(mariborCoordinates)
            mapController.setZoom(15.0)
        }


        lifecycleScope.launch {
            // Fetch fitness location data from the database within a coroutine
            val fitnessList = try {
                DatabaseService.getAllFitnessLocations()
            } catch (e: Exception) {
                Log.d("MapFragment", "Error fetching fitness locations", e)
                emptyList() // Handle the error gracefully
            }

            // Add markers for each fitness location
            for (fitness in fitnessList) {
                val geoPoint = GeoPoint(fitness.location.x, fitness.location.y)
                val marker = Marker(map)
                marker.position = geoPoint
                marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_position)
                marker.title = fitness.name
                marker.snippet = fitness.location.street
                map.overlays.add(marker)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(requireContext(), requireActivity().getPreferences(0))
        map.onResume()
    }
}
