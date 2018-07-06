package com.centling.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/**
 * Created by Victor on 14/12/2017. (ง •̀_•́)ง
 */
class SingleSelectedLinearLayout(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {
    private var onSelectChanged: ((Int) -> Unit)? = null
    val targetChildren = mutableListOf<View>()

    inline fun <reified T> filterView(defaultSelected: Int = -1) {
        targetChildren.clear()
        targetChildren.addAll((0 until childCount).map { getChildAt(it) }.filter { it.javaClass == T::class.java })

        targetChildren.forEachIndexed { index, view ->
            view.isSelected = index == defaultSelected
            view.setOnClickListener { selectChildAtRelative(index) }
        }
    }

    fun selectChildAtRelative(index: Int) {
        if (targetChildren[index].isSelected) {
            return
        }
        targetChildren.forEachIndexed { i, v -> v.isSelected = index == i }
        onSelectChanged?.invoke(index)
    }

    fun setOnSelectChangedListener(onSelectChanged: (Int) -> Unit) {
        this.onSelectChanged = onSelectChanged
    }
}