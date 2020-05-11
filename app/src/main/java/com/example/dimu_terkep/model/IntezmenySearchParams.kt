package com.example.dimu_terkep.model

import com.google.gson.annotations.SerializedName

data class IntezmenySearchParams (
    @SerializedName("intezmenyNev")
    val nev: String,
    @SerializedName("intezmenyCim")
    val cim: String,
    @SerializedName("intezmenyVezeto")
    val vezeto: String,
    @SerializedName("esemenyNev")
    val esemeny: String,
    @SerializedName("mukodesTol")
    val tol: Int,
    @SerializedName("mukodesIg")
    val ig: Int,
    @SerializedName("intezmenyTipus")
    val tiipus:List<Int>
)