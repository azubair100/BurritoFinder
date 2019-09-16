package com.zubair.burritofinder.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double)