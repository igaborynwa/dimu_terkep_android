package com.example.dimu_terkep

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.example.dimu_terkep.events.GetPinsResponseEvent
import com.livinglifetechway.quickpermissions.annotations.WithPermissions

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_map.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {
    private lateinit var btnMap: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        btnMap=findViewById(R.id.btnMap)
        btnMap.setOnClickListener {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            val isNetworkAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected
            if(!isNetworkAvailable) Toast.makeText(applicationContext,getString(R.string.nem_elerheto), Toast.LENGTH_LONG).show()
            else createMap()
        }
    }

    @WithPermissions(
        permissions = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE]
    )
    private fun createMap() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }


}
