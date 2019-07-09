package com.example.mamak.cryptocurrency20kotlin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Currency : Serializable {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("symbol")
    @Expose
    var symbol: String? = null

    @SerializedName("rank")
    @Expose
    val rank: Int = 0

    @SerializedName("price_usd")
    @Expose
    val priceUsd: Double = 0.toDouble()

    @SerializedName("price_btc")
    @Expose
    val priceBtc: Double = 0.toDouble()

    @SerializedName("market_cap_usd")
    @Expose
    val marketCapUsd: Long = 0

    @SerializedName("percent_change_1h")
    @Expose
    val percentChange1h: Float = 0.toFloat()

    @SerializedName("percent_change_24h")
    @Expose
    val priceChange24h: Float = 0.toFloat()

    @SerializedName("percent_change_7d")
    @Expose
    val percentChange7d: Float = 0.toFloat()

    override fun toString(): String {
        return "Currency{" +
                "mName='" + name + '\''.toString() +
                ", mSymbol='" + symbol + '\''.toString() +
                ", mRank=" + rank +
                ", mPriceUsd=" + priceUsd +
                ", mPriceBtc=" + priceBtc +
                ", mMarketCapUsd='" + marketCapUsd + '\''.toString() +
                ", mPercentChange1h='" + percentChange1h + '\''.toString() +
                ", mPriceChange24h=" + priceChange24h +
                ", mPercentChange7d='" + percentChange7d + '\''.toString() +
                '}'.toString()
    }
}
