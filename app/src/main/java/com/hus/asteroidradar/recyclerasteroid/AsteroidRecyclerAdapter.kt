package com.hus.asteroidradar.recyclerasteroid

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hus.asteroidradar.databaseasteroid.Asteroid
import com.hus.asteroidradar.R
import com.hus.asteroidradar.databinding.AsteroidRowBinding


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1
class AsteroidRecyclerAdapter(private val context: Context?,
                              private val asteroids: List<Asteroid>,
                              private val itemClick: (pos: Int) -> Unit) : RecyclerView.Adapter<AsteroidRecyclerAdapter.AsteroidsViewHolder>() {

    //private val binding: AsteroidRowBinding,
    class AsteroidsViewHolder(private val binding: AsteroidRowBinding, itemView: View,
                              private val itemClick: (pos: Int) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        val icon: ImageView = itemView.findViewById(R.id.asteroids_row_icon)
        val name: TextView = itemView.findViewById(R.id.asteroids_row_name)
        val date: TextView = itemView.findViewById(R.id.asteroids_row_date)

        fun bind(asteroidClickListener: AsteroidClickListener, asteroid: Asteroid) {
            binding.asteroidrow = asteroid
            binding.asteroidrowclickListener = asteroidClickListener
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener { itemClick.invoke(adapterPosition) }
        }

    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {


            Log.i("hhhhh", "onCreateViewHolder called")
            return AsteroidsViewHolder(AsteroidRowBinding.inflate(LayoutInflater.from(parent.context)),LayoutInflater.from(context)
                    .inflate(R.layout.asteroid_row,
                            parent,
                            false),
                    itemClick)
        }

        override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {

            Log.i("hhhhh", "onBindViewHolder called")
            val asteroid = asteroids[position]
            holder.name.text = asteroid.codename
            holder.date.text = asteroid.closeApproachDate

            //holder.icon.isSelected = asteroid.isPotentiallyHazardous
            Log.i("kkkkk   ", "isPotentiallyHazardous  :   " + asteroid.isPotentiallyHazardous)


            if (asteroid.isPotentiallyHazardous) {
                Log.i("kkkkk   ", "Set icon to hazardous")

                R.drawable.ic_status_potentially_hazardous

            } else {
                Log.i("kkkkk   ", "Set icon to Normal")
                R.drawable.ic_status_normal
            }


/*            holder.icon.contentDescription = if (asteroid.isPotentiallyHazardous) {
                context?.getString(R.string.potentially_hazardous_asteroid_image)

            } else {
                context?.getString(R.string.not_hazardous_asteroid_image)
            }*/

           /* when (holder) {
                is AsteroidsViewHolder -> {
                    val nightItem = getItem(position) as DataItem.SleepNightItem
                    holder.bind(clickListener, nightItem.sleepNight)
                }
            }*/
        }

        override fun getItemCount(): Int {
            Log.i("hhhhh", "getItemCount called")

            return asteroids.size
        }


}

class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}
    /*class AsteroidsViewHolder(private val binding: AsteroidRowBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroidrow = asteroid
            binding.executePendingBindings()
        }
*//*    fun bind(clickListener: AsteroidRecyclerAdapter.AsteroidClickListener, item: Asteroid) {
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
    }*//*
*//*
    val name: TextView = itemView.findViewById(R.id.asteroids_row_name)
    val date: TextView = itemView.findViewById(R.id.asteroids_row_date)
    val icon: ImageView = itemView.findViewById(R.id.asteroids_row_hazard_icon)

    init {
        itemView.setOnClickListener { itemClick.invoke(adapterPosition) }
    }*//*
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
        return AsteroidsViewHolder(AsteroidRowBinding.inflate(LayoutInflater.from(parent.context)))*/
/*        return when (viewType) {

            ITEM_VIEW_TYPE_ITEM -> AsteroidsViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }*/
   // }

//    override fun getItemCount() = asteroids.size


/*    class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }*/

/*    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            onlClickListener.onClick(asteroid)
        }
        holder.bind(asteroid)
    }*/



//}

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



