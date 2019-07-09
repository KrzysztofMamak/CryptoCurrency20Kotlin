package com.example.mamak.cryptocurrency20kotlin

import java.io.IOException
import java.util.ArrayList

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryMarket {

    fun getHistoryBySymbol(symbol: String?): List<HistoryItem> {
        val builder = Retrofit.Builder()
                .baseUrl("https://min-api.cryptocompare.com/data/")
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        val retrofitAPI = retrofit.create<RetrofitAPI>(RetrofitAPI::class.java!!)

        val call = retrofitAPI.getHistoryData(symbol, "USD", 29, 1, "CCCAGG")
        var list: List<HistoryItem> = ArrayList()

        try {
            list = call.execute().body()!!.historyItemList!!
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }

        return list
    }

    companion object {
        private val TAG = "HistoryMarket"
    }

}
