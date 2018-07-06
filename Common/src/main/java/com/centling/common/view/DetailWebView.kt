package com.centling.common.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class DetailWebView(context: Context, attrs: AttributeSet) : WebView(context, attrs) {

    init {
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        setInitialScale(1)
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true

        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
    }

    fun loadData(data: String) {
        loadData("<head><meta content=\"width=device-width,initial-scale=1.0,minimum-scale=.5," +
                "maximum-scale=3\" name=\"viewport\"></head><body style='margin:0;" +
                "padding:0;'>" + data + "</body>",
                "text/html;charset=utf-8", null)
    }
}
