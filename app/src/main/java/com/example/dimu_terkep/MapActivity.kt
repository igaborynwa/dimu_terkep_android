package com.example.dimu_terkep

import android.content.Intent
import android.graphics.Color
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
import com.example.dimu_terkep.events.GetPinsResponseEvent
import com.example.dimu_terkep.fragments.SearchDialogFragment
import com.example.dimu_terkep.model.Intezmeny
import com.example.dimu_terkep.model.IntezmenyPinDto
import com.example.dimu_terkep.model.IntezmenyTipus
import com.example.dimu_terkep.network.TerkepInteractor
import com.ianpinto.androidrangeseekbar.rangeseekbar.RangeSeekBar
import kotlinx.android.synthetic.main.content_map.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.osmdroid.config.Configuration
import org.osmdroid.views.overlay.Marker
import java.util.*
import kotlin.collections.ArrayList


class MapActivity : AppCompatActivity(), SearchDialogFragment.SearchListener {


    private var terkepInteractor = TerkepInteractor()
    private lateinit var map: MapView
    private lateinit var seekBar: RangeSeekBar<Int>
    private lateinit var tv:TextView
    private  var searchName=""
    private  var searchAddr=""
    private var searchHead=""
    private var typeList= ArrayList<IntezmenyTipus>()
    private var markerList =ArrayList<Marker>()


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
                loadPins()
            }
        }
        )

        initList()
        loadPins()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetPinResponse(event: GetPinsResponseEvent) {
        showMarkers(event.response)
    }



    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }


    private fun loadPins() {
        terkepInteractor.getIntezmeny(searchName,searchAddr,searchHead, seekBar.selectedMinValue, seekBar.selectedMaxValue, typeList,
            onSuccess = this::showMarkers, onError = this::showError)
    }



    private fun showMarkers(intezmenyek:List<IntezmenyPinDto>){
        //Toast.makeText(applicationContext, "success", Toast.LENGTH_LONG).show()
        map.overlays.clear()
        map.invalidate()
        //for(m in markerList) markerList.remove(m)
        for(i in intezmenyek){
            val marker = Marker(map)
            marker.position= GeoPoint(i.latitude, i.longitude)
            marker.textLabelBackgroundColor= Color.RED
            marker.textLabelForegroundColor=Color.RED

            marker.setOnMarkerClickListener { m, mapView ->
                showDetails(i)
                true
            }
            map.overlays.add(marker)
            markerList.add(marker)

        }
    }
    private fun showError(e: Throwable) {
        Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }




    private fun initList(){
        typeList.add(IntezmenyTipus.desc(getString(R.string.type1),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type2),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type3),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type4),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type5),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type6),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type7),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type8),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type9),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type10),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type11),this))
        typeList.add(IntezmenyTipus.desc(getString(R.string.type12),this))
    }

    private fun showDetails(intezmeny: IntezmenyPinDto){
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("intezmenyId", intezmeny.id)
            startActivity(intent)


    }

    private fun addMarkers(){
        map.overlays.clear()
        map.invalidate()
        for(i in inst){
            if(i.isValid(seekBar.selectedMinValue, seekBar.selectedMaxValue)) {
                i.getMarker().setOnMarkerClickListener { marker, mapView ->
                    //showDetails(marker)
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


    override fun searchParamChanged(name:String, addr:String,  head: String, list:ArrayList<IntezmenyTipus>) {
        searchName=name
        searchAddr=addr
        searchHead=head
        typeList=list
        loadPins()

    }

    override fun getSearchName(): String {
        return searchName
    }

    override fun getSearchAddr(): String {
        return searchAddr
    }

    override fun getSearchHead(): String {
        return searchHead
    }


    override fun getList():ArrayList<IntezmenyTipus>{
        return typeList
    }

}

