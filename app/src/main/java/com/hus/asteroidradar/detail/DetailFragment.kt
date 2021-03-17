package com.hus.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.hus.asteroidradar.R
import com.hus.asteroidradar.databaseasteroid.Asteroid
import com.hus.asteroidradar.databaseasteroid.AsteroidMainViewModel
import com.hus.asteroidradar.databaseasteroid.AsteroidMainViewModelFactory
import com.hus.asteroidradar.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var  binding: FragmentDetailBinding
    private val asteroidMainViewModel: AsteroidMainViewModel by activityViewModels {
        AsteroidMainViewModelFactory(activity?.applicationContext)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        asteroidMainViewModel.selectedAsteroid.observe(viewLifecycleOwner, Observer { resource ->
            bindingAsteroidViewModelData(resource)

        })
        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        binding.asteroid = asteroid

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }

    private fun bindingAsteroidViewModelData(dataAsteroid: Asteroid) {
        dataAsteroid.also { asteroid ->
            binding.activityDetailImageOfAsteroid.contentDescription = if (asteroid.isPotentiallyHazardous) {
                getString(R.string.potentially_hazardous_asteroid_image)
            } else {
                getString(R.string.not_hazardous_asteroid_image)
            }

            if (asteroid.isPotentiallyHazardous) {
                Picasso.get()
                    .load(R.drawable.asteroid_hazardous)
                    .into(binding.activityDetailImageOfAsteroid)
            } else {
                Picasso.get()
                    .load(R.drawable.asteroid_safe)
                    .into(binding.activityDetailImageOfAsteroid)
            }

            binding.closeApproachDate.text = asteroid.closeApproachDate
            binding.absoluteMagnitude.text = getString(R.string.astronomical_unit_format, asteroid.absoluteMagnitude)
            binding.estimatedDiameter.text = getString(R.string.km_unit_format, asteroid.estimatedDiameter)
            binding.relativeVelocity.text = getString(R.string.km_s_unit_format, asteroid.relativeVelocity)
            binding.distanceFromEarth.text = getString(R.string.astronomical_unit_format, asteroid.distanceFromEarth)
        }
    }

}
