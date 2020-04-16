package com.example.dimu_terkep

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_map.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat.startActivity
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.dimu_terkep.data.Institution
import com.example.dimu_terkep.fragments.SearchDialogFragment
import com.ianpinto.androidrangeseekbar.rangeseekbar.RangeSeekBar
import kotlinx.android.synthetic.main.content_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.views.overlay.Marker
import java.util.*
import kotlin.collections.ArrayList


class MapActivity : AppCompatActivity(), SearchDialogFragment.SearchListener {


    private lateinit var map: MapView
    private lateinit var seekBar: RangeSeekBar<Int>
    private lateinit var tv:TextView
    private  var searchParam=""
    private  var searchValue=""
    private var typeList= ArrayList<String>()


    private val inst =ArrayList<Institution>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fab.setOnClickListener { view ->
            val searchFragment = SearchDialogFragment()
            searchFragment.show(supportFragmentManager, "TAG")
        }
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        val mapController = map.controller
        mapController.setZoom(15)
        val startPoint = GeoPoint(47.4983563703, 19.0409786879)
        mapController.setCenter(startPoint)
        seekBar=findViewById(R.id.simpleSeekBar)
        tv=findViewById(R.id.tvProgress)
        seekBar.setRangeValues(1700,Calendar.getInstance().get(Calendar.YEAR))
        seekBar.selectedMaxValue=Calendar.getInstance().get(Calendar.YEAR)
        seekBar.selectedMinValue=1700
        tv.text=seekBar.selectedMinValue.toString()+" - " + seekBar.selectedMaxValue.toString()


        seekBar.setOnRangeSeekBarChangeListener(object : RangeSeekBar.OnRangeSeekBarChangeListener<Int>{
            override fun onRangeSeekBarValuesChanged(bar: RangeSeekBar<*>?, minValue: Int?, maxValue: Int?) {
                tv.text=seekBar.selectedMinValue.toString()+" - " + seekBar.selectedMaxValue.toString()
                refreshMarkers()
            }
        }
        )

        initList()
        addMarkers()
    }



    private fun initList(){
        typeList.add(getString(R.string.type1).toLowerCase())
        typeList.add(getString(R.string.type2).toLowerCase())
        typeList.add(getString(R.string.type3).toLowerCase())
        typeList.add(getString(R.string.type4).toLowerCase())
        typeList.add(getString(R.string.type5).toLowerCase())
        typeList.add(getString(R.string.type6).toLowerCase())
        typeList.add(getString(R.string.type7).toLowerCase())
        typeList.add(getString(R.string.type8).toLowerCase())
        typeList.add(getString(R.string.type9).toLowerCase())
        typeList.add(getString(R.string.type10).toLowerCase())
        typeList.add(getString(R.string.type11).toLowerCase())
        typeList.add(getString(R.string.type12).toLowerCase())



        inst.add(Institution("Fiatal Iparművészek Stúdiója Egyesület","V. Kálmán Imre utca 16.",47.5069605, 19.0528687, map, 1982, 0, 1990, 2020,"Egyesület", "xy", "blabla"))
        inst.add(Institution("Ar2day Gallery","V. Szalay utca 2. ",47.50866, 19.04836, map,2000, 0, 2000, 2009,"Kereskedelmi galéria", "xy", "blabla"))

    }

    private fun showDetails(marker:Marker?){
        var tmp: Institution? = null
        for(i in inst){
            if(i.getMarker() == marker){
                tmp=i
                break
            }
        }
        if(tmp!=null) {
            System.out.println(tmp.getName())
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("isntitution", tmp)
            startActivity(intent)
        }

    }

    private fun refreshMarkers(){
        map.overlays.clear()
        map.invalidate()
        for(i in inst){
            if(i.isValid(seekBar.selectedMinValue, seekBar.selectedMaxValue)
                && i.search(searchParam, searchValue)
                && typeList.contains(i.getType().toLowerCase())) {
                i.getMarker().setOnMarkerClickListener { marker, mapView ->
                    showDetails(marker)
                    true
                }
                map.overlays.add(i.getMarker())
            }
        }
    }



    private fun addMarkers(){
        map.overlays.clear()
        map.invalidate()
        for(i in inst){
            if(i.isValid(seekBar.selectedMinValue, seekBar.selectedMaxValue)) {
                i.getMarker().setOnMarkerClickListener { marker, mapView ->
                    showDetails(marker)
                    true
                }
                map.overlays.add(i.getMarker())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }


    override fun searchParamChanged(param:String, value: String, list:ArrayList<String>) {
        searchParam=param
        searchValue=value
        typeList=list
        refreshMarkers()
    }

    override fun getSearchValue(): String {
        return searchValue
    }

    override fun getSearchParam(): String {
        return searchParam
    }

    override fun getList():ArrayList<String>{
        return typeList
    }

}

