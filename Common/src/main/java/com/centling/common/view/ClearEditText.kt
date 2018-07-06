package com.centling.common.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import com.centling.base.R
import pers.victor.ext.addTextChangedListener
import pers.victor.ext.dp2px
import pers.victor.ext.findDrawable

class ClearEditText(context: Context, attrs: AttributeSet) : EditText(context, attrs) {
    private val drawable = findDrawable(R.drawable.ic_circle_delete)
    private var drawableClear: Drawable? = null

    init {
        addTextChangedListener {
            after { updateIconStatus() }
        }
        setOnFocusChangeListener { _, b ->
            drawableClear = if (b) drawable else null
            updateIconStatus()
        }
    }

    private fun updateIconStatus() {
        val drawables = compoundDrawables
        if (drawableClear == null) {
            setCompoundDrawables(drawables[0], drawables[1], null, drawables[3])
            return
        }
        if (length() > 0) {
            drawableClear?.setBounds(0, 0, dp2px(12f), dp2px(12f))
            setCompoundDrawables(drawables[0], drawables[1], drawableClear, drawables[3])
        } else {
            setCompoundDrawables(drawables[0], drawables[1], null, drawables[3])
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (drawableClear != null && event.action == MotionEvent.ACTION_DOWN) {
            val xDown = event.x.toInt()
            if (xDown >= width - compoundPaddingRight && xDown < width) {
                text = null
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}
