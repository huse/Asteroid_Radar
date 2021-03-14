package com.hus.asteroidradar.recyclerasteroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hus.asteroidradar.databaseasteroid.Asteroid
import com.hus.asteroidradar.R
import com.hus.asteroidradar.databinding.AsteroidRowBinding


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1
class AsteroidRecyclerAdapter(val onlClickListener: AsteroidClickListener) :
        ListAdapter<Asteroid, AsteroidRecyclerAdapter.AsteroidsViewHolder>(DiffCallback) {

    class AsteroidsViewHolder(private val binding: AsteroidRowBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroidrow = asteroid
            binding.executePendingBindings()
        }
/*    fun bind(clickListener: AsteroidRecyclerAdapter.AsteroidClickListener, item: Asteroid) {
        binding.asteroidrow = item
        binding.asteroidrowclickListener = clickListener
        binding.executePendingBindings()

    }

    companion object {
        fun from(parent: ViewGroup): AsteroidsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AsteroidRowBinding.inflate(layoutInflater, parent, false)

            return AsteroidsViewHolder(binding)
        }
    }*/
/*
    val name: TextView = itemView.findViewById(R.id.asteroids_row_name)
    val date: TextView = itemView.findViewById(R.id.asteroids_row_date)
    val icon: ImageView = itemView.findViewById(R.id.asteroids_row_hazard_icon)

    init {
        itemView.setOnClickListener { itemClick.invoke(adapterPosition) }
    }*/
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
/*        return when (viewType) {

            ITEM_VIEW_TYPE_ITEM -> AsteroidsViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }*/
    }

//    override fun getItemCount() = asteroids.size


    class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

/*    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            onlClickListener.onClick(asteroid)
        }
        holder.bind(asteroid)
    }*/



}

/*class AsteroidDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}
sealed class DataItem {
    data class SleepNightItem(val asteroid: Asteroid) : DataItem() {
        override val id = asteroid.id
    }

    object Header : DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}
class TextViewHolder(view: View) : ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): TextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.header, parent, false)
            return TextViewHolder(view)
        }
    }
}*/



