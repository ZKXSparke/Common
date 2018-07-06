package com.centling.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.centling.base.R

class FlexibleImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {
    private var proportionWidth: Int
    private var proportionHeight: Int

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlexibleImageView)
        proportionWidth = typedArray.getInt(R.styleable.FlexibleImageView_proportion_width, 0)
        proportionHeight = typedArray.getInt(R.styleable.FlexibleImageView_proportion_height, 0)
        typedArray.recycle()
        scaleType = ScaleType.CENTER_CROP
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        if (specMode != MeasureSpec.EXACTLY && proportionHeight * proportionWidth != 0) {
            val height = View.resolveSize(measuredWidth * proportionHeight / proportionWidth, heightMeasureSpec)
            setMeasuredDimension(measuredWidth, height)
        }
    }

    fun setProportion(width: Int, height: Int) {
        proportionWidth = width
        proportionHeight = height
        requestLayout()
    }
}