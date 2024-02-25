package com.example.aktivebuddy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aktivebuddy.R
import com.example.aktivebuddy.adapter.FitnessAdapter
import com.example.aktivebuddy.database.DatabaseService
import com.example.aktivebuddy.databinding.CardViewDesignBinding
import com.example.aktivebuddy.databinding.FitnessCentrsFragmentBinding
import com.example.aktivebuddy.database.model.Fitness
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint

class FitnessFragment(private var onItemClick: (GeoPoint) -> Unit): Fragment(R.layout.fitness_centrs_fragment) {
    //private lateinit var myApplication: MyApplication
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FitnessCentrsFragmentBinding.inflate(inflater, container, false)
        val cardBinding = CardViewDesignBinding.inflate(inflater, container, false)

        val fitnessAdapter = FitnessAdapter(mutableListOf(), cardBinding, onItemClick)


        binding.rvMain.setHasFixedSize(false)
        binding.rvMain.adapter = fitnessAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(context)

        // Fetch fitness data asynchronously
        fetchFitnessData { fitnessList ->
            fitnessAdapter.updateData(fitnessList)
        }

        return binding.root
    }

    private fun fetchFitnessData(callback: (List<Fitness>) -> Unit) {
        // Use a coroutine to perform the Firestore operation
        lifecycleScope.launch {
            try {
                val fitnessList = DatabaseService.getAllFitnessLocations()
                callback(fitnessList)
            } catch (e: Exception) {
                Log.d("DB", "Error getting documents", e)
            }
        }
    }
}