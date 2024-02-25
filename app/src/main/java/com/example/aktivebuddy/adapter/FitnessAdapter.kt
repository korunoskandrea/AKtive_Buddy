package com.example.aktivebuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aktivebuddy.database.model.Fitness
import com.example.aktivebuddy.R
import com.example.aktivebuddy.databinding.CardViewDesignBinding
import com.squareup.picasso.Picasso
import org.osmdroid.util.GeoPoint

interface MyOnClick{
    fun onClick(position: Int)
}

class FitnessAdapter(private var data: List<Fitness>, private val binding: CardViewDesignBinding, private var onItemClick: (GeoPoint) -> Unit):
    RecyclerView.Adapter<FitnessAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_view_design, viewGroup, false)
        return FitnessAdapter.ViewHolder(view, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fitness = data[position]

        Picasso.get().load(fitness.imageUrl).into(holder.imageView)

        holder.name.text = fitness.name
        holder.location.text = fitness.location.street

        holder.itemView.setOnClickListener {
            onItemClick(GeoPoint(fitness.location.x, fitness.location.y));
        }
    }

    fun updateData(newData: List<Fitness>) {
        data = newData
        notifyDataSetChanged() //  refresh the RecyclerView with the new data.
    }


    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View, binding: CardViewDesignBinding) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView;
        val name: TextView;
        val location: TextView;
        init {
            name = itemView.findViewById(binding.fitnessName.id)
            location = itemView.findViewById(binding.fitnessLocation.id)
            imageView = itemView.findViewById(binding.imageView.id)
        }
    }
}