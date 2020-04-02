package com.example.dimu_terkep.data

import android.graphics.Color
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class Institution {
    private val name:String
    private val lat:Double
    private val long:Double

    private val marker:Marker


    constructor(n: String , latitude:Double, longitude:Double, map:MapView){
        name=n
        lat=latitude
        long=longitude
        marker = Marker(map)
        marker.position= GeoPoint( lat, long)
    }

    fun getMarker(): Marker {
        return marker
    }

    fun getName(): String {
        return name
    }
}