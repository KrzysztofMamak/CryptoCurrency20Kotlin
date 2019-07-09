package com.example.mamak.cryptocurrency20kotlin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitAPI {
    @Headers("Content-type: application/json")
    @GET("v1/ticker/")
    fun getCurrencies(@Query("start") start: Int,
                      @Query("limit") limit: Int): Call<List<Currency>>

    @Headers("Content-type: application/json")
    @GET("histoday?")
    fun getHistoryData(@Query("fsym") fsym: String?,
                       @Query("tsym") tsym: String,
                       @Query("limit") limit: Int,
                       @Query("aggregate") aggregate: Int,
                       @Query("e") e: String): Call<HistoryData>
}
