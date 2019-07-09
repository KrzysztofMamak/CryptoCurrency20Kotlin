package com.example.mamak.cryptocurrency20kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.mamak.cryptocurrency20kotlin.R

class CurrencyPagerActivity : AppCompatActivity() {

    private var mViewPager: ViewPager? = null
    private var mCurrencies: List<Currency>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_pager)

        val currencySymbol = intent.getStringExtra(EXTRA_SYMBOL)

        mViewPager = findViewById<View>(R.id.currency_view_pager) as ViewPager?

        mCurrencies = CurrencyLab.get().currencies
        val fragmentManager = supportFragmentManager
        mViewPager!!.adapter = object : FragmentStatePagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                val currency = mCurrencies!![position]
                return CurrencyFragment.newInstance(currency.symbol)
            }

            override fun getCount(): Int {
                return mCurrencies!!.size
            }
        }

        for (i in mCurrencies!!.indices) {
            if (mCurrencies!![i].symbol == currencySymbol) {
                mViewPager!!.currentItem = i
                break
            }

        }
    }

    companion object {
        private val EXTRA_SYMBOL = "example.mamak.cryptocurrency20.symbol"

        fun newIntent(packageContext: FragmentActivity?, symbol: String?): Intent {
            val intent = Intent(packageContext, CurrencyPagerActivity::class.java)
            intent.putExtra(EXTRA_SYMBOL, symbol)
            return intent
        }
    }
}