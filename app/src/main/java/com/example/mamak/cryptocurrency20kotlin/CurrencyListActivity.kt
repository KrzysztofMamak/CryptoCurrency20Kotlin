package com.example.mamak.cryptocurrency20kotlin

import android.support.v4.app.Fragment

class CurrencyListActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CurrencyListFragment()
    }
}
