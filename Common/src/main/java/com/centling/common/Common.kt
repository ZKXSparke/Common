package com.centling.common

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import pers.victor.ext.Ext

/**
 * @describe describe
 * @anthor qiaoshouliang😜
 * @time 2018/4/19 下午12:35
 * @chang time
 */
object Common {
    fun init(context: Application) {
        Ext.with(context)
        initLogger()
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .tag("HiSmart")
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}