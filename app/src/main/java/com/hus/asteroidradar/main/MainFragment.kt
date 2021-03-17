package com.hus.asteroidradar.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.hus.asteroidradar.R
import com.hus.asteroidradar.asteroidrepository.StatusEnumClass
import com.hus.asteroidradar.databaseasteroid.Asteroid
import com.hus.asteroidradar.databaseasteroid.AsteroidMainViewModel
import com.hus.asteroidradar.databaseasteroid.AsteroidMainViewModelFactory
import com.hus.asteroidradar.databasepictureday.PictureOfDay
import com.hus.asteroidradar.databinding.FragmentMainBinding
import com.hus.asteroidradar.detail.DetailFragment
import com.hus.asteroidradar.recyclerasteroid.AsteroidRecyclerAdapter
import com.squareup.picasso.Picasso
import timber.log.Timber

class MainFragment : Fragment() {

/*    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }*/
    private val asteroidViewModel: AsteroidMainViewModel by activityViewModels {
        AsteroidMainViewModelFactory(activity?.applicationContext)
    }

    private val asteroidsFeeder: MutableList<Asteroid> = mutableListOf()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        Timber.plant(Timber.DebugTree())
        Timber.i("hhhh by Timber onCreateView method called." )

        Log.i("hhhhh", "onCreateView called")
        observingAsteroidInViewModel()
        observingPictureOfDay()
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.asteroidMainViewModel = asteroidViewModel

        asteroidViewModel.gettingFed()



        binding.asteroidRecycler.adapter = AsteroidRecyclerAdapter(context,
                asteroidsFeeder) { pos ->

            asteroidViewModel.selecting(asteroidsFeeder[pos])

            val isTablet = resources.configuration.smallestScreenWidthDp > 600
            if (!isTablet) {
                activity?.supportFragmentManager
                        ?.beginTransaction()
                    ?.replace(R.id.nav_host_fragment, DetailFragment())
                        ?.addToBackStack(TAG)
                        ?.commit()
            }
        }

/*        binding.asteroidRecycler.adapter = AsteroidRecyclerAdapter(AsteroidRecyclerAdapter.AsteroidClickListener {
            viewModel.displayAsteroidDetails(it)
        })

        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayAsteroidDetailsComplete()
            }
        })*/
/*


        val application = requireNotNull(this.activity).application

        val dataSource = AsteroidDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = AsteroidMainViewModelFactory(dataSource, application)

        val asteroidMainViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(AsteroidMainViewModel::class.java)

        val adapter = AsteroidRecyclerAdapter(AsteroidRecyclerAdapter.AsteroidClickListener { asteroidId ->
            asteroidMainViewModel.onAsteroidClicked(asteroidId)
        })

        binding.sleepList.adapter = adapter

        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })*/





        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    fun observingAsteroidInViewModel() {
        asteroidViewModel.asteroidFeed.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                StatusEnumClass.SUCCESSFULL -> {
                    if (resource.data == null) {
                        return@Observer
                    }

                    bindingAsteroidlData(resource.data)
                }
            }
        })
    }
    private fun observingPictureOfDay() {

        asteroidViewModel.pictureOfDay.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                StatusEnumClass.SUCCESSFULL -> {
                    if (resource.data == null) {
                        return@Observer
                    }

                    bindingPictureOfDay(resource.data)
                }
                StatusEnumClass.LOAD -> {}
                StatusEnumClass.ERROR -> {
                }
            }
        })
    }

    private fun bindingPictureOfDay(pictureOfDay: PictureOfDay) {
        binding.activityMainImageOfTheDay.contentDescription = getString(
            R.string.nasa_picture_of_day_content_description_format,
            pictureOfDay.title)

        binding.textViewForImage.text = pictureOfDay.title
        Picasso.get()
            .load(pictureOfDay.url)
            .into(binding.activityMainImageOfTheDay)
    }
    private fun bindingAsteroidlData(asteroidList: List<Asteroid>) {
        asteroidsFeeder.clear()
        asteroidsFeeder.addAll(asteroidList)
        binding.asteroidRecycler.adapter?.notifyDataSetChanged()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
