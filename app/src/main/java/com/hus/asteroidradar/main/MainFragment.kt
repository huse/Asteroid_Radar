package com.hus.asteroidradar.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hus.asteroidradar.R
import com.hus.asteroidradar.asteroidrepository.StatusEnumClass
import com.hus.asteroidradar.databaseasteroid.Asteroid
import com.hus.asteroidradar.databaseasteroid.AsteroidMainViewModel
import com.hus.asteroidradar.databaseasteroid.AsteroidMainViewModelFactory
import com.hus.asteroidradar.databinding.FragmentMainBinding
import com.hus.asteroidradar.detail.DetailFragment
import com.hus.asteroidradar.recyclerasteroid.AsteroidRecyclerAdapter

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

        observeViewModel()
       // observePictureOfDay()
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
    fun observeViewModel() {
        asteroidViewModel.asteroidFeed.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                StatusEnumClass.SUCCESSFULL -> {
                    if (resource.data == null) {
                        return@Observer
                    }

                    bindViewModelData(resource.data)
                }
            }
        })
    }
    fun bindViewModelData(data: List<Asteroid>) {
        asteroidsFeeder.clear()
        asteroidsFeeder.addAll(data)
        binding.asteroidRecycler.adapter?.notifyDataSetChanged()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
