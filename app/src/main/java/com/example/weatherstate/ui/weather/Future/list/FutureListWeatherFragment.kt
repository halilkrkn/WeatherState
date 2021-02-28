package com.example.weatherstate.ui.weather.Future.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherstate.R
import com.example.weatherstate.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import com.example.weatherstate.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FutureListWeatherFragment: ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private  val viewModelFactory : FutureListWeatherViewModelFactory by instance()
    private lateinit var viewModelFutureWeatherList: FutureListWeatherViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFutureWeatherList = ViewModelProviders.of(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        bindUI()
    }


    private fun bindUI() = launch(Dispatchers.Main){

        val futureWeatherEntries = viewModelFutureWeatherList.weatherEntries.await()
        val weatherLocation = viewModelFutureWeatherList.weatherLocation.await()

        weatherLocation.observe(this@FutureListWeatherFragment, Observer{ location ->
           if (location == null) return@Observer
            updateLocation(location.name)
        })

        futureWeatherEntries.observe(this@FutureListWeatherFragment, Observer { futureWeatherEntries ->
            if (futureWeatherEntries == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToNextWeek()
            initRecyclerView(futureWeatherEntries.toFutureWeatherItems())

        })

    }


    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToNextWeek() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next Week"
    }

    // Bu kısımda recyclerview için oluşturduğumuz FutureWeatherItem adapter i için UnitSpecificSimpleFutureWeatherEntry i listeye koyduk ve  FutureWeatherItem a map işlemine sokmuş olduk.
    private fun List<UnitSpecificSimpleFutureWeatherEntry>.toFutureWeatherItems() : List<FutureWeatherItem> {
        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>){

        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
                addAll(items)
        }

        // LinearLayoutManagerişlemini future_list_weather_fragmentte recyclerView da tanımladım.
        recyclerView.adapter = groupAdapter

        // item_future_weather da oluşturduğumuz yapıya tıklama işlemi getirdik.
        groupAdapter.setOnItemClickListener { item, view ->
            Toast.makeText(this@FutureListWeatherFragment.context,"Clicked", Toast.LENGTH_SHORT).show()
        }

/*        // Bu kısımda  ise kod yazarak recyclerView için LinearLayoutManager işlemini yapabiliriz.
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
//            adapter = groupAdapter
//
        }
*/




    }



}