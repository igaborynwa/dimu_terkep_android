package com.example.dimu_terkep.network

import com.example.dimu_terkep.model.Intezmeny
import com.example.dimu_terkep.model.IntezmenyPinDto
import com.example.dimu_terkep.model.IntezmenySearchParams
import com.example.dimu_terkep.model.IntezmenyTipus
import retrofit2.Call
import retrofit2.http.*

interface TerkepAPI {
    companion object{
        const val ENDPOINT_URL="https://dimu-backend.herokuapp.com/api/"
    }

    @POST("Intezmeny")
    fun getIntezmeny(@Body param: IntezmenySearchParams): Call<List<IntezmenyPinDto>>


    @GET("Intezmeny/{id}")
    fun getIntezmenyById(@Path("id") id: String ): Call<Intezmeny>
}