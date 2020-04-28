package com.example.dimu_terkep

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.Toast
import com.example.dimu_terkep.data.Institution
import com.example.dimu_terkep.events.GetDetailsResponseEvent
import com.example.dimu_terkep.events.GetPinsResponseEvent
import com.example.dimu_terkep.model.Intezmeny
import com.example.dimu_terkep.network.TerkepInteractor

import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DetailsActivity : AppCompatActivity() {
    private var terkepInteractor = TerkepInteractor()
    private  lateinit var intezmeny: Intezmeny
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        val id = intent.getSerializableExtra("intezmenyId") as String
        loadDetails(id)
    }

    private fun loadDetails(id:String){
        terkepInteractor.getIntezmenyById(id, onSuccess = this::requestByIdSuccess, onError = this::requestByIdError )
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetDetailsResponse(event: GetDetailsResponseEvent) {
        requestByIdSuccess(event.response)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private fun requestByIdSuccess(i: Intezmeny){
        intezmeny=i
        title = i.nev
        var cim =""
        for(s in i.intezmenyHelyszinek) {
            var kolt =""
            if(s.koltozes!=0) kolt=s.koltozes.toString()
            cim+=s.helyszin+" ("+s.nyitas.toString()+" - "+ kolt + ")\n"
        }
        tv_addr.text=cim
        var vezetok =""
        for(s in i.intezmenyVezetok) {
            var ig=""
            if(s.ig!=0) ig=s.ig.toString()
            vezetok+=s.nev+" ("+ s.tol.toString()+" -  "+ig +")\n"
        }
        tv_head.text=vezetok
        tv_desc.text=i.leiras
        tv_links.text=Html.fromHtml(i.link)
        tv_links.movementMethod=LinkMovementMethod.getInstance()
        tv_media.text=i.videok
        var esemenyek=""
        for(s in i.esemenyek) esemenyek+=s.datum+": "+ s.nev+ ", " +s.szervezo +"\n"
        tv_exh.text=esemenyek
    }

    private fun requestByIdError(e:Throwable){
        Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }
}
