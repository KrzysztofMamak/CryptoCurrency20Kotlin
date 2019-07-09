package com.example.mamak.cryptocurrency20kotlin

import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.ImageView

import com.bumptech.glide.Glide

object GlideHelper {
    private val TAG = "GlideHelper"
    private val CONST_URL = "https://raw.githubusercontent.com/cjdowner/cryptocurrency-icons/master/128/icon/"

    fun downloadImage(context: FragmentActivity?, imageView: ImageView, symbol: String?) {
        Log.d(TAG, "Downloading image...")

        val newSymbol = symbol?.toLowerCase()
        val newUrl = "$CONST_URL$newSymbol.png"
        context?.let {
            Glide
                .with(it)
                .load(newUrl)
                .into(imageView)
        }
    }
}
