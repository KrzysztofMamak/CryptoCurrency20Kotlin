package com.example.mamak.cryptocurrency20kotlin

import java.io.IOException
import java.util.ArrayList

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyMarket {

    fun getCurrenciesByPosition(start: Int): List<Currency>? {
        val builder = Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com/")
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        val retrofitAPI = retrofit.create<RetrofitAPI>(RetrofitAPI::class.java!!)

        val call = retrofitAPI.getCurrencies(start, 50)
        var list: List<Currency>? = ArrayList()

        try {
            list = call.execute().body()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }

        return list
    }

    companion object {
        private val TAG = "CurrencyMarket"
    }
}
