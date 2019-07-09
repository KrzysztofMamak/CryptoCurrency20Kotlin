package com.example.mamak.cryptocurrency20kotlin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HistoryData {
    @SerializedName("Data")
    @Expose
    val historyItemList: List<HistoryItem>? = null
}