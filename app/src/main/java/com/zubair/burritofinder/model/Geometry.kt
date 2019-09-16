package com.zubair.burritofinder.model

import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("location") val commonLocation: CommonLocation)