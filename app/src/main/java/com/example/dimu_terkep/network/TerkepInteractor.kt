package com.example.dimu_terkep.network

import android.os.Handler
import com.example.dimu_terkep.events.GetDetailsResponseEvent
import com.example.dimu_terkep.events.GetPinsResponseEvent
import com.example.dimu_terkep.model.Intezmeny
import com.example.dimu_terkep.model.IntezmenyPinDto
import com.example.dimu_terkep.model.IntezmenySearchParams
import com.example.dimu_terkep.model.IntezmenyTipus
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.NullPointerException

class TerkepInteractor {
    private val terkepAPI:TerkepAPI

    init {
        val retrofit=Retrofit.Builder()
            .baseUrl(TerkepAPI.ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.terkepAPI=retrofit.create(TerkepAPI::class.java)
    }

    private fun <T> runCallOnBackgroundThread(
        call: Call<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val handler = Handler()
        Thread {
            try {
                val response = call.execute().body()!!
                handler.post {
                    onSuccess(response)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { onError(e) }
            }
        }.start()
    }

    fun <T> callGetPinsEvent(response: T){
        EventBus.getDefault().post(GetPinsResponseEvent(response as List<IntezmenyPinDto>))
    }

    fun <T> callGetDetailsEvent(response: T){
        EventBus.getDefault().post(GetDetailsResponseEvent(response as Intezmeny))
    }


    fun getIntezmeny(nev:String, cim: String, vezeto:String,esemeny: String, tol: Int, ig: Int, tipus: List<IntezmenyTipus>,
                  onSuccess: (List<IntezmenyPinDto>) -> Unit, onError: (Throwable) -> Unit){
        var listOfTypes=ArrayList<Int>()
        for(t in tipus){
            listOfTypes.add(t.i)
        }
        val param= IntezmenySearchParams(nev, cim, vezeto,esemeny, tol, ig, listOfTypes)

        val getIntezmenyRequest =terkepAPI.getIntezmeny(param)
        runCallOnBackgroundThread(getIntezmenyRequest, onSuccess = this::callGetPinsEvent, onError= onError)
    }

    fun getIntezmenyById(id:String, onSuccess: (Intezmeny) -> Unit, onError: (Throwable) -> Unit){

        val getRequest = terkepAPI.getIntezmenyById(id)
        runCallOnBackgroundThread(getRequest, onSuccess=this::callGetDetailsEvent, onError=onError)

    }
}