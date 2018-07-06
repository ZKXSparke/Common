package com.centling.common.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.centling.base.R
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import pers.victor.ext.app

/**
 * Created by Victor on 2017/6/29. (ง •̀_•́)ง
 */
@SuppressLint("CheckResult")
fun ImageView.url(url: String, isCircleCrop: Boolean = false, holder: Int = R.color.main, error: Int = R.color.main) {
    val options = RequestOptions.placeholderOf(holder)
            .error(error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
    if (isCircleCrop) {
        options.circleCrop()
    }
    Glide.with(this.context)
            .load(url)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}



fun ImageView.loadBitmap(bitmap: Bitmap) {

    Glide.with(this.context)
            .load(bitmap)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    bitmap.recycle()
}

fun ImageView.loadBitmap(url: String, options: RequestOptions) {
    Glide.with(app)
            .asBitmap()
            .load(url)
            .apply(options)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    setImageBitmap(resource)
                }
            })
}

@SuppressLint("CheckResult")
fun ImageView.local(obj: Any, isCircleCrop: Boolean = false) {
    val options = RequestOptions().centerCrop()
    if (isCircleCrop) {
        options.circleCrop()
    }
    Glide.with(this.context)
            .load(obj)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}




fun postEvent(event: Any) = EventBus.getDefault().post(event)
fun postStickyEvent(event: Any) = EventBus.getDefault().postSticky(event)

fun log(log: Any?) = Logger.i("Tag: " + log.toString())
fun json(json: Any?) = Logger.json(json.toString())
fun err(error: Any?) = Logger.e(error.toString())
