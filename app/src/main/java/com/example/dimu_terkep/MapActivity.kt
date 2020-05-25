package com.example.dimu_terkep

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_map.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.widget.Toast
import com.example.dimu_terkep.events.GetPinsResponseEvent
import com.example.dimu_terkep.fragments.SearchDialogFragment
import com.example.dimu_terkep.model.IntezmenyPinDto
import com.example.dimu_terkep.model.IntezmenyTipus
import com.example.dimu_terkep.network.TerkepInteractor
import com.ianpinto.androidrangeseekbar.rangeseekbar.RangeSeekBar
import com.livinglifetechway.quickpermissions.annotations.WithPermissions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.osmdroid.config.Configuration
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import java.util.*
import kotlin.collections.ArrayList
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus




class MapActivity : AppCompatActivity(), SearchDialogFragment.SearchListener {


    private var terkepInteractor = TerkepInteractor()
    private lateinit var map: MapView
    private lateinit var seekBar: RangeSeekBar<Int>
    private lateinit var tv:TextView
    private  var searchName=""
    private  var searchAddr=""
    private var searchHead=""
    private var searchEvent=""
    private var typeList= ArrayList<IntezmenyTipus>()



    @WithPermissions(
        permissions = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE]
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fab.setOnClickListener { view ->
            val searchFragment = SearchDialogFragment()
            searchFragment.show(supportFragmentManager, "TAG")
        }
        fab2.setOnClickListener { v ->
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(false)
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
        terkepInteractor.getIntezmeny(searchName,searchAddr,searchHead,searchEvent, seekBar.selectedMinValue, seekBar.selectedMaxValue, typeList,
            onSuccess = this::showMarkers, onError = this::showError)
    }

    private fun getIcon(i: IntezmenyPinDto): Int {
        return when(i.type){
            0 -> R.drawable.markerallmuz
            1 -> R.drawable.markerallkulkozp
            2 -> R.drawable.markeronkmuz
            3 -> R.drawable.markeronkkultkozp
            4 -> R.drawable.markeronkgal
            5 -> R.drawable.markerkergal
            6 -> R.drawable.markerfuggkult
            7 -> R.drawable.markernonprof
            8 -> R.drawable.markerkultint
            9 -> R.drawable.markeregy
            10 -> R.drawable.markerokt
            else -> R.drawable.markerett

        }
    }



    private fun showMarkers(intezmenyek:List<IntezmenyPinDto>){
        map.overlays.clear()
        map.invalidate()
        for(i in intezmenyek){


            val items = ArrayList<OverlayItem>()
            val item =OverlayItem("", "", GeoPoint(i.latitude, i.longitude))
            item.setMarker(ContextCompat.getDrawable(this, getIcon(i)))
            item.markerHotspot=OverlayItem.HotspotPlace.BOTTOM_CENTER

            items.add(item)

//the overlay
            val mOverlay = ItemizedOverlayWithFocus<OverlayItem>(items,
                object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                    override fun onItemSingleTapUp(index: Int, item: OverlayItem): Boolean {
                        showDetails(i)
                        return true
                    }

                    override fun onItemLongPress(index: Int, item: OverlayItem): Boolean {
                        return false
                    }
                },applicationContext)
            map.getOverlays().add(mOverlay)

        }
    }
    private fun showError(e: Throwable) {
        Toast.makeText(applicationContext, R.string.nem_elerheto, Toast.LENGTH_LONG).show()
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



    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }


    override fun searchParamChanged(name:String, addr:String,  head: String, event: String, list:ArrayList<IntezmenyTipus>) {
        searchName=name
        searchAddr=addr
        searchHead=head
        searchEvent=event
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

    override fun getSearchEventParam(): String {
        return searchEvent
    }

    override fun getList():ArrayList<IntezmenyTipus>{
        return typeList
    }

}

