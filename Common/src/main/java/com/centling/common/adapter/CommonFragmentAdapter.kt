package com.centling.common.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.centling.common.base.BaseFragment

class CommonFragmentAdapter(fm: FragmentManager, val title: List<CharSequence>, val fragmentList: List<BaseFragment>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int) = title[position]

}
