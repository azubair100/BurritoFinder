package com.zubair.burritofinder.model

import com.google.gson.annotations.SerializedName

data class CommonLocation(
    @SerializedName("lat") var latitude: Double,
    @SerializedName("lng") var longitude: Double)