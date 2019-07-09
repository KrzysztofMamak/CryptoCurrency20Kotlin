package com.example.mamak.cryptocurrency20kotlin

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class CurrencyFragment : Fragment() {

    private var mCurrency: Currency? = null
    private var mNameTextView: TextView? = null
    private var mPriceUsdTextView: TextView? = null
    private var mPriceBtcTextView: TextView? = null
    private var mMarketCapTextView: TextView? = null
    private var mChange1hTextView: TextView? = null
    private var mChange24hTextView: TextView? = null
    private var mChange7dTextView: TextView? = null
    private var mLogoImageView: ImageView? = null
    private var mGraphView: GraphView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCurrency = CurrencyLab
                .get()
                .getCurrency(arguments!!.get(ARG_CURRENCY_SYMBOL).toString())
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_currency, container, false)

        mLogoImageView = v.findViewById<View>(R.id.logo_image_view) as ImageView
        mNameTextView = v.findViewById<View>(R.id.name_text_view) as TextView
        mPriceUsdTextView = v.findViewById<View>(R.id.price_usd_text_view) as TextView
        mPriceBtcTextView = v.findViewById<View>(R.id.price_btc_text_view) as TextView
        mMarketCapTextView = v.findViewById<View>(R.id.market_cap_text_view) as TextView
        mChange1hTextView = v.findViewById<View>(R.id.percent_1h_text_view) as TextView
        mChange24hTextView = v.findViewById<View>(R.id.percent_24h_text_view) as TextView
        mChange7dTextView = v.findViewById<View>(R.id.percent_7d_text_view) as TextView
        mGraphView = v.findViewById<View>(R.id.graph) as GraphView
        mGraphView!!.title = resources.getString(R.string.graph_name)
        mGraphView!!.titleColor = Color.GRAY
        HistoryMarketTask(mCurrency!!.symbol).execute()

        GlideHelper.downloadImage(activity, mLogoImageView!!, mCurrency!!.symbol)
        mNameTextView!!.text = mCurrency!!.name + "(" + mCurrency!!.symbol + ")"
        mPriceUsdTextView!!.text = mCurrency!!.priceUsd.toString() + "$"
        mPriceBtcTextView!!.text = mCurrency!!.priceBtc.toString()
        mMarketCapTextView!!.setText(String.format("%.3f", mCurrency!!.marketCapUsd / 1000000000.0) + "B$")
        mChange1hTextView!!.text = mCurrency!!.percentChange1h.toString() + "%"
        mChange24hTextView!!.text = mCurrency!!.priceChange24h.toString() + "%"
        mChange7dTextView!!.text = mCurrency!!.percentChange7d.toString() + "%"

        return v
    }

    private fun setupGraph(historyItems: List<HistoryItem>) {
        val itemCount = historyItems.size
        val points = arrayOfNulls<DataPoint>(itemCount)
        Log.i(TAG, "itemCount$itemCount")
        for (i in 0 until itemCount) {
            points[i] = DataPoint(i.toDouble(), historyItems[i].high)
        }
        val series = LineGraphSeries<DataPoint>(points)
        series.color = R.color.colorPrimaryDark
        series.backgroundColor = R.color.colorPrimaryDark
        series.isDrawAsPath = true
        mGraphView!!.addSeries(series)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.fragment_currency, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.insert_record -> {
                val recordUploader = RecordUploader()
                recordUploader.execute(mCurrency)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private inner class HistoryMarketTask(private val mSymbol: String?) : AsyncTask<Void, Void, List<HistoryItem>>() {

        override fun doInBackground(vararg voids: Void): List<HistoryItem> {
            return HistoryMarket().getHistoryBySymbol(mSymbol)
        }

        override fun onPostExecute(historyItems: List<HistoryItem>) {
            setupGraph(historyItems)
        }
    }

    companion object {
        private val TAG = "CurrencyFragment"
        private val ARG_CURRENCY_SYMBOL = "currency_symbol"

        fun newInstance(symbol: String?): CurrencyFragment {
            val args = Bundle()
            args.putSerializable(ARG_CURRENCY_SYMBOL, symbol)
            val fragment = CurrencyFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
