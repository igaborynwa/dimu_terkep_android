package com.example.dimu_terkep.model

import com.google.gson.annotations.SerializedName

data class IntezmenyVezeto (
    @SerializedName("id")
    val id: String,
    @SerializedName("nev")
    val nev: String,
    @SerializedName("tol")
    val tol: Int,
    @SerializedName("ig")
    val ig: Int,
    @SerializedName("IntezmenyId")
    val intezmenyId: String
)