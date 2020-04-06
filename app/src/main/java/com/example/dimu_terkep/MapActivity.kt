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
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.dimu_terkep.data.Institution
import kotlinx.android.synthetic.main.content_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.views.overlay.Marker
import java.util.*


class MapActivity : AppCompatActivity() {
    private lateinit var map: MapView
    private lateinit var seekBar:SeekBar
    private lateinit var tv:TextView

    private val inst =ArrayList<Institution>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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
        seekBar.max=Calendar.getInstance().get(Calendar.YEAR)
        seekBar.progress=Calendar.getInstance().get(Calendar.YEAR)
        tv.text=seekBar.progress.toString()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
               tvProgress.text = seekBar.progress.toString()
                addMarkers(seekBar.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        initList()
        addMarkers(2020)
    }

    private fun initList(){
        inst.add(Institution("Fiatal Iparművészek Stúdiója Egyesület","V. Kálmán Imre utca 16.",47.5069605, 19.0528687, map, 1982, 0, 1990, 2020,"Egyesület", "xy", "blabla"))
        inst.add(Institution("Ar2day Gallery","V. Szalay utca 2. ",47.50866, 19.04836, map,2009, 0, 2009, 2018,"Kereskedelmi galéria", "xy", "blabla"))
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

    private fun addMarkers(year:Int){
        map.overlays.clear()
        map.invalidate()
        for(i in inst){
            if(i.isValid(year)) {
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
}
