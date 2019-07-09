package com.example.mamak.cryptocurrency20kotlin

import java.util.ArrayList

class CurrencyLab private constructor() {
    private val mCurrencies: MutableList<Currency>

    val currencies: List<Currency>
        get() = mCurrencies

    init {
        mCurrencies = ArrayList()
    }

    fun getCurrency(symbol: String): Currency? {
        for (currency in mCurrencies) {
            if (currency.symbol == symbol) {
                return currency
            }
        }
        return null
    }

    fun addAll(list: List<Currency>) {
        mCurrencies.addAll(list)
    }

    companion object {
        private var sCurrencyLab: CurrencyLab? = null
        fun get(): CurrencyLab {
            if (sCurrencyLab == null) {
                sCurrencyLab = CurrencyLab()
            }
            return sCurrencyLab as CurrencyLab
        }
    }
}
