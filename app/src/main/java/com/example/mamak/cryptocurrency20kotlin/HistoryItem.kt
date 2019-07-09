package com.example.mamak.cryptocurrency20kotlin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HistoryItem {
    @SerializedName("time")
    @Expose
    var time: Long = 0
    @SerializedName("close")
    @Expose
    private val mClose: Double = 0.toDouble()
    @SerializedName("high")
    @Expose
    val high: Double = 0.toDouble()
    @SerializedName("low")
    @Expose
    private val mLow: Double = 0.toDouble()
    @SerializedName("open")
    @Expose
    private val mOpen: Double = 0.toDouble()
    @SerializedName("volumefrom")
    @Expose
    private val mVolumeFrom: Double = 0.toDouble()
    @SerializedName("volumeto")
    @Expose
    private val mVolumeTo: Double = 0.toDouble()
}
