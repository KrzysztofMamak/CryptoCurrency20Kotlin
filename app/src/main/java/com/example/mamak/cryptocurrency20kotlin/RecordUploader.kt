package com.example.mamak.cryptocurrency20kotlin

import android.os.AsyncTask
import android.util.Log

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date

class RecordUploader : AsyncTask<Currency, Void, String>() {

    override fun doInBackground(vararg params: Currency): String? {
        val currency = params[0]
        val insert_url = "http://[ip]/insertcurrency.php"
        try {
            val name = currency.name
            val symbol = currency.symbol
            val rank = currency.rank.toString()
            val price_usd = currency.priceUsd.toString()
            val price_btc = currency.priceBtc.toString()
            val market_cap_usd = currency.marketCapUsd.toString()
            val percent_change_1h = currency.percentChange1h.toString()
            val percent_change_24h = currency.priceChange24h.toString()
            val percent_change_7d = currency.percentChange7d.toString()
            val date = Date()
            val formatter = SimpleDateFormat("H:mm EEE, MMM d, yyyy")
            val dateString = formatter.format(date)
            Log.d("-------------------", dateString)
            val url = URL(insert_url)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.doOutput = true
            httpURLConnection.doInput = true
            val outputStream = httpURLConnection.outputStream
            val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream,
                    "UTF-8"))
            val post_data = (URLEncoder.encode("name", "UTF-8")
                    + "=" + URLEncoder.encode(name, "UTF-8")
                    + "&" + URLEncoder.encode("symbol", "UTF-8")
                    + "=" + URLEncoder.encode(symbol, "UTF-8")
                    + "&" + URLEncoder.encode("rank", "UTF-8")
                    + "=" + URLEncoder.encode(rank, "UTF-8")
                    + "&" + URLEncoder.encode("price_usd", "UTF-8")
                    + "=" + URLEncoder.encode(price_usd, "UTF-8")
                    + "&" + URLEncoder.encode("price_btc", "UTF-8")
                    + "=" + URLEncoder.encode(price_btc, "UTF-8")
                    + "&" + URLEncoder.encode("market_cap_usd", "UTF-8")
                    + "=" + URLEncoder.encode(market_cap_usd, "UTF-8")
                    + "&" + URLEncoder.encode("percent_change_1h", "UTF-8")
                    + "=" + URLEncoder.encode(percent_change_1h, "UTF-8")
                    + "&" + URLEncoder.encode("percent_change_24h", "UTF-8")
                    + "=" + URLEncoder.encode(percent_change_24h, "UTF-8")
                    + "&" + URLEncoder.encode("percent_change_7d", "UTF-8")
                    + "=" + URLEncoder.encode(percent_change_7d, "UTF-8")
                    + "&" + URLEncoder.encode("date", "UTF-8")
                    + "=" + URLEncoder.encode(dateString, "UTF-8"))
            bufferedWriter.write(post_data)
            bufferedWriter.flush()
            bufferedWriter.close()
            outputStream.close()
            val inputStream = httpURLConnection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream,
                    "iso-8859-1"))
            var result = ""
            var line: String
            //while ((line = bufferedReader.readLine()) != null) {
            //    result += line
            //}
            bufferedReader.close()
            inputStream.close()
            httpURLConnection.disconnect()
            return result
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Log.d("XXX", "MATI")
        return null
    }

    override fun onPreExecute() {

    }

    override fun onPostExecute(result: String?) {
        if (result != null)
            Log.d(TAG, result)
    }

    override fun onProgressUpdate(vararg values: Void) {
        super.onProgressUpdate(*values)
    }

    companion object {

        private val TAG = "RecordUploader"
    }
}
