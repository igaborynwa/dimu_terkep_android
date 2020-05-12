package com.example.dimu_terkep

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
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
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import com.example.dimu_terkep.model.IntezmenyTipus


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
        title=""
        tvTitle.text= i.nev
        var cim =""
        val sortedHelyszin=i.intezmenyHelyszinek.sortedWith(compareBy { it.nyitas })
        for(s in sortedHelyszin) {
            var kolt =""
            if(s.koltozes!=0) kolt=s.koltozes.toString()
            cim+=s.helyszin+" ("+s.nyitas.toString()+" - "+ kolt + ")\n"
        }
        tv_addr.text=cim
        var vezetok =""
        val sortedVezeto=i.intezmenyVezetok.sortedWith(compareBy { it.tol })
        for(s in sortedVezeto) {
            var ig=""
            if(s.ig!=0) ig=s.ig.toString()
            vezetok+=s.nev+" ("+ s.tol.toString()+" -  "+ig +")\n"
        }
        tv_head.text=vezetok
        tv_desc.text=i.leiras

        imageView.setImageDrawable(ContextCompat.getDrawable(this, getIcon(i)))
        tv_links.text=Html.fromHtml(i.link)
        tv_links.movementMethod=LinkMovementMethod.getInstance()

        tv_media.text=i.videok

        var esemenyek=""
        val sortedEsemeny=i.esemenyek.sortedWith(compareBy { it.datum })
        for(s in sortedEsemeny) esemenyek+=s.datum+": "+ s.nev+ ", " +s.szervezo +"\n"
        tv_exh.text=esemenyek
    }

    private fun getIcon(i: Intezmeny): Int{

        return when(i.tipus){
            0 -> R.drawable.ikonallmuz
            1 -> R.drawable.ikonallkulkozp
            2 -> R.drawable.ikononkmuz
            3 -> R.drawable.ikononkkulkozp
            4 -> R.drawable.ikononkgal
            5 -> R.drawable.ikonkergal
            6 -> R.drawable.ikonfuggkultint
            7 -> R.drawable.ikonnonp
            8 -> R.drawable.ikonkultint
            9 -> R.drawable.ikonegy
            10 -> R.drawable.ikonoktint
            else -> R.drawable.ikonett

        }
    }

    private fun requestByIdError(e:Throwable){
        Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }
}
