package com.hus.asteroidradar.recyclerasteroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hus.asteroidradar.databaseasteroid.Asteroid

import com.hus.asteroidradar.databinding.AsteroidRowBinding


/*
class AsteroidRecyclerAdapter2(val onlClickListener: AsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidRecyclerAdapter2.AsteroidsViewHolder>(DiffCallback) {

    class AsteroidsViewHolder(private val binding: AsteroidRowBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroidrow = asteroid
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            onlClickListener.onClick(asteroid)
        }
        holder.bind(asteroid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {
        return AsteroidsViewHolder(AsteroidRowBinding.inflate(LayoutInflater.from(parent.context)))

    }

    class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}*/
