package com.example.mamak.cryptocurrency20kotlin

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.mamak.cryptocurrency20kotlin.R

import java.text.SimpleDateFormat
import java.util.Date

class CurrencyListFragment : Fragment() {

    private var mCurrencyRecyclerView: RecyclerView? = null
    private var mUpdateTextView: TextView? = null
    private var mIsLoading = false
    private var mProgressBar: ProgressBar? = null
    private var mRecyclerViewState: Parcelable? = null
    private var mOnCreateViewLoaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_currency_list, container, false)
        mOnCreateViewLoaded = false
        mUpdateTextView = view.findViewById<View>(R.id.update_text_view) as TextView
        mProgressBar = view.findViewById<View>(R.id.progress_bar) as ProgressBar
        mCurrencyRecyclerView = view.findViewById<View>(R.id.currency_recycler_view) as RecyclerView
        updateCurrencies(0)
        mCurrencyRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        mCurrencyRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val layoutManager = mCurrencyRecyclerView!!.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                val itemCount = mCurrencyRecyclerView!!.adapter.itemCount

                if (mIsLoading == false && lastPosition == itemCount - 1) {
                    mRecyclerViewState = mCurrencyRecyclerView!!.layoutManager.onSaveInstanceState()
                    updateCurrencies(itemCount)
                    mIsLoading = true
                }
            }
        })
        setupAdapter()
        changeLastUpdate()

        return view
    }

    override fun onResume() {
        super.onResume()
    }

    private fun updateCurrencies(start: Int) {
        CurrencyMarketTask(start).execute()
    }

    private fun setupAdapter() {
        mCurrencyRecyclerView!!.adapter = CurrencyAdapter(
                CurrencyLab.get().currencies)
        mCurrencyRecyclerView!!.layoutManager.onRestoreInstanceState(mRecyclerViewState)
    }

    private fun changeLastUpdate() {
        val today: Date
        val result: String
        val formatter: SimpleDateFormat

        formatter = SimpleDateFormat("d MMM yyyy, H:mm")
        today = Date()
        result = formatter.format(today)
        mUpdateTextView!!.text = "Last update: $result"
    }

    private inner class CurrencyHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_currency, parent, false)), View.OnClickListener {
        private var mCurrency: Currency? = null
        private val mNameTextView: TextView
        private val mPriceUsdTextView: TextView
        private val mChangeImageView: ImageView
        private val mLogoImageView: ImageView

        init {
            itemView.setOnClickListener(this)

            mNameTextView = itemView.findViewById<View>(R.id.currency_name) as TextView
            mPriceUsdTextView = itemView.findViewById<View>(R.id.currency_price_usd) as TextView
            mLogoImageView = itemView.findViewById<View>(R.id.currency_logo) as ImageView
            mChangeImageView = itemView.findViewById<View>(R.id.change_image_view) as ImageView
        }

        fun bind(currency: Currency) {
            mCurrency = currency
            mNameTextView.text = mCurrency!!.name
            mPriceUsdTextView.text = mCurrency!!.priceUsd.toString() + " $"
            GlideHelper.downloadImage(activity, mLogoImageView, mCurrency!!.symbol)
            if (mCurrency!!.percentChange1h > 0) {
                mChangeImageView.setImageResource(R.drawable.ic_arrow_up)
            } else if (mCurrency!!.percentChange1h < 0) {
                mChangeImageView.setImageResource(R.drawable.ic_arrow_down)
            } else {
                mChangeImageView.setImageResource(R.drawable.ic_line)
            }
        }

        override fun onClick(v: View) {
            val intent = CurrencyPagerActivity.newIntent(activity, mCurrency!!.symbol)
            startActivity(intent)
        }
    }

    private inner class CurrencyAdapter(private val mCurrencies: List<Currency>) : RecyclerView.Adapter<CurrencyHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
            val layoutInflater = LayoutInflater.from(activity)
            return CurrencyHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
            val currency = mCurrencies[position]
            holder.bind(currency)
        }

        override fun getItemCount(): Int {
            return mCurrencies.size
        }
    }

    private inner class CurrencyMarketTask(private val mStart: Int) : AsyncTask<Void, Void, List<Currency>>() {

        override fun onPreExecute() {
            if (mOnCreateViewLoaded == false) {
                mCurrencyRecyclerView!!.visibility = View.GONE
                mUpdateTextView!!.visibility = View.GONE
                mProgressBar!!.visibility = View.VISIBLE
            }
        }

        override fun doInBackground(vararg voids: Void): List<Currency>? {
            return CurrencyMarket().getCurrenciesByPosition(mStart)
        }

        override fun onPostExecute(currencies: List<Currency>) {
            CurrencyLab.get().addAll(currencies)
            if (mOnCreateViewLoaded == false) {
                mProgressBar!!.visibility = View.GONE
                mUpdateTextView!!.visibility = View.VISIBLE
                mCurrencyRecyclerView!!.visibility = View.VISIBLE
            }
            mCurrencyRecyclerView!!.setBackgroundColor(Color.LTGRAY)
            setupAdapter()
            mOnCreateViewLoaded = true
            mIsLoading = false
        }
    }

    companion object {
        private val TAG = "CurrencyListFragment"
    }
}
