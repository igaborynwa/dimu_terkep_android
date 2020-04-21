package com.example.dimu_terkep.network

import android.os.Handler
import com.example.dimu_terkep.model.Intezmeny
import com.example.dimu_terkep.model.IntezmenyPinDto
import com.example.dimu_terkep.model.IntezmenySearchParams
import com.example.dimu_terkep.model.IntezmenyTipus
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
                handler.post { onSuccess(response) }

            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { onError(e) }
            }
        }.start()
    }

    fun getIntezmeny(nev:String, cim: String, vezeto:String, tol: Int, ig: Int, tipus: List<IntezmenyTipus>,
                  onSuccess: (List<IntezmenyPinDto>) -> Unit, onError: (Throwable) -> Unit){
        val param= IntezmenySearchParams(nev, cim, vezeto, tol, ig, tipus)

        val getIntezmenyRequest =terkepAPI.getIntezmeny(param)
        runCallOnBackgroundThread(getIntezmenyRequest, onSuccess, onError)
    }

    fun getIntezmenyById(id:String, onSuccess: (Intezmeny) -> Unit, onError: (Throwable) -> Unit){

        val getRequest = terkepAPI.getIntezmenyById(id)
        runCallOnBackgroundThread(getRequest, onSuccess, onError)

    }
}