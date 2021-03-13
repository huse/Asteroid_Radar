package com.hus.asteroidradar.recyclerasteroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hus.asteroidradar.databaseasteroid.Asteroid
import com.hus.asteroidradar.R
import com.hus.asteroidradar.databinding.AsteroidRowBinding


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1
class AsteroidRecyclerAdapter(val clickListener: AsteroidClickListener) : ListAdapter<DataItem,
        ViewHolder>(AsteroidDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is AsteroidsViewHolder -> {
                val asteroidItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(clickListener, asteroidItem.asteroid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {
        return when (viewType) {

            ITEM_VIEW_TYPE_ITEM -> AsteroidsViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

//    override fun getItemCount() = asteroids.size


    class AsteroidClickListener(val clickListener: (astrId: Long) -> Unit) {
        fun onClick(astr: Asteroid) = clickListener(astr.id)
    }


}

class AsteroidsViewHolder private constructor(val binding: AsteroidRowBinding)

    : ViewHolder(binding.root) {

    fun bind(clickListener: AsteroidRecyclerAdapter.AsteroidClickListener, item: Asteroid) {
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
    }
/*
    val name: TextView = itemView.findViewById(R.id.asteroids_row_name)
    val date: TextView = itemView.findViewById(R.id.asteroids_row_date)
    val icon: ImageView = itemView.findViewById(R.id.asteroids_row_hazard_icon)

    init {
        itemView.setOnClickListener { itemClick.invoke(adapterPosition) }
    }*/
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<DataItem>() {
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
}



