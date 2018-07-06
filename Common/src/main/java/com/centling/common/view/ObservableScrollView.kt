package com.centling.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

class ObservableScrollView(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    private var onScrollCallback: ((l: Int, t: Int) -> Unit)? = null

    fun setOnScrollChangedCallback(onScrollCallback: (l: Int, t: Int) -> Unit) {
        this.onScrollCallback = onScrollCallback
    }

    fun clearOnScrollChangedCallback() {
        this.onScrollCallback = null
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        onScrollCallback?.invoke(l, t)
    }
}