package com.example.aktivebuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aktivebuddy.R
import com.example.aktivebuddy.databinding.WelcomeFragmentBinding

class WelcomeFragment(private val onFitnessClick: () -> Unit, private val onLoginClick: () -> Unit): Fragment(
    R.layout.fitness_centrs_fragment
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        val fitnessList = mutableListOf<Fitness>()
//        for (i in 1..10){
//            val fitness= Fitness.generateFakeFitnessData()
//            fitnessList.add(fitness)
//        }

        val binding = WelcomeFragmentBinding.inflate(inflater,container,false)


        binding.buttonFitnessCentersFragment.setOnClickListener{
            onFitnessClick();
        }

        binding.buttonLoginFragment.setOnClickListener{
            onLoginClick();
        }

        return binding.root
    }

}