package com.example.dimu_terkep.model

import com.google.gson.annotations.SerializedName

data class IntezmenySearchParams (
    @SerializedName("intezmenyNev")
    val nev: String,
    @SerializedName("intezmenyCim")
    val cim: String,
    @SerializedName("intezmenyVezeto")
    val vezeto: String,
    @SerializedName("mukodesTol")
    val tol: Int,
    @SerializedName("mukodesIg")
    val ig: Int,
    @SerializedName("intezmenyTipus")
    val tipus: List<IntezmenyTipus>
)