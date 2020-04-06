package com.example.dimu_terkep.data

import android.graphics.Color
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.Serializable

class Institution : Serializable{
    private val name:String
    private val address:String
    private val lat:Double
    private val long:Double
    private val found: Int
    private val exp: Int
    private val opened: Int
    private val closed: Int
    private val type:String
    private val head:String
    private val desc:String


    @Transient
    private val marker:Marker


    constructor(n: String, addr:String , latitude:Double, longitude:Double, map:MapView, f:Int, e:Int, o:Int, c:Int, t:String, h:String, d:String){
        name=n
        address=addr
        lat=latitude
        long=longitude
        marker = Marker(map)
        marker.position= GeoPoint( lat, long)
        found=f
        exp=e
        opened=o
        closed=c
        type=t
        head=h
        desc=d

    }

    fun getMarker(): Marker {
        return marker
    }

    fun getAddress(): String {
        return address
    }
    fun getDesc(): String {
        return desc
    }
    fun getHead(): String {
        return head
    }

    fun getName(): String {
        return name
    }

    fun isValid(year:Int): Boolean{
        return year in opened..closed
    }



}