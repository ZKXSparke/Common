package com.centling.common.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.centling.base.R
import com.centling.common.view.AutoRecyclerView
import com.centling.common.view.TitleBar
import org.greenrobot.eventbus.EventBus
import pers.victor.ext.findColor
import pub.devrel.easypermissions.EasyPermissions

/**
 * Author : victor
 * Time : 16-9-11 20:42
 */
abstract class BaseFragment : Fragment(), View.OnClickListener, EasyPermissions.PermissionCallbacks {
    protected lateinit var mContext: BaseActivity
    private var titleBar: TitleBar? = null
    private var lastClickTime = 0L
    private var registerEventBus = false
    private var loadDataAtFirst = true
    private var onPermissionsDenied: ((String) -> Unit)? = null
    private var onPermissionsGranted: ((String) -> Unit)? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return if (useTitleBar()) {
            val layoutMain = LinearLayout(mContext)
            layoutMain.orientation = LinearLayout.VERTICAL
            titleBar = TitleBar(mContext)
            titleBar?.useElevation()
            layoutMain.addView(titleBar, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val layoutContent = inflater.inflate(bindLayout(), layoutMain, false)
            layoutMain.addView(layoutContent, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            layoutMain
        } else {
            inflater.inflate(bindLayout(), container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
        initWidgets()
        if (registerEventBus) {
            EventBus.getDefault().register(this)
        }
        if (loadDataAtFirst) {
            loadData()
            loadData(true)
        }
    }

    protected open fun useTitleBar() = true

    protected fun setTitleBarVisibility(visibility: Int) = { titleBar?.visibility = visibility }()

    protected fun setTitleBarText(title: String) = titleBar?.setTitleBarText(title)

    protected fun setTitleBarText(id: Int) = titleBar?.setTitleBarText(resources.getString(id))

    protected fun setTitleBarColor(id: Int) = titleBar?.setTitleBarColor(findColor(id))

    protected fun setTitleBarLeft(drawable: Int) = titleBar?.setLeftDrawable(drawable)

    protected fun setTitleBarRight(drawable: Int) = titleBar?.setRightDrawable(drawable)

    protected fun setTitleBarLeft(leftText: String) = titleBar?.setLeftText(leftText)

    protected fun setTitleBarRight(rightText: String) = titleBar?.setRightText(rightText)

    protected fun setTitleBarRight(rightText: String, iconId: Int, iconAtLeft: Boolean = true) = titleBar?.setRightTextAndIcon(rightText, iconId, iconAtLeft)

    protected fun setTitleBarLeft(leftText: String, iconId: Int, iconAtLeft: Boolean = true) = titleBar?.setLeftTextAndIcon(leftText, iconId, iconAtLeft)

    protected fun setTitleBarLeftVisibility(visibility: Int) {
        titleBar?.setLeftVisibility(visibility)
        if (visibility == View.INVISIBLE) {
            titleBar?.setTitleBarLeftClick(null)
        }
    }

    protected fun setTitleBarRightVisibility(visibility: Int) {
        titleBar?.setRightVisibility(visibility)
        if (visibility == View.INVISIBLE) {
            titleBar?.setTitleBarRightClick(null)
        }
    }

    protected fun setTitleBarLeftClick(click: () -> Unit) = titleBar?.setTitleBarLeftClick { click() }

    protected fun setTitleBarRightClick(click: () -> Unit) = titleBar?.setTitleBarRightClick { click() }

    protected fun setTitleBarTitleClick(click: () -> Unit) = titleBar?.setTitleBarTitleClick { click() }

    protected fun getTitleBar() = titleBar

    protected fun registerEventBus() = { this.registerEventBus = true }()

    override fun onDestroyView() {
        if (registerEventBus) {
            EventBus.getDefault().unregister(this)
        }
        onPermissionsGranted = null
        onPermissionsDenied = null
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        if (System.currentTimeMillis() - lastClickTime > 300) {
            lastClickTime = System.currentTimeMillis()
            onWidgetsClick(v)
        }
    }

    protected abstract fun initWidgets()

    protected abstract fun onWidgetsClick(v: View)

    protected abstract fun bindLayout(): Int

    protected open fun initData() {
    }

    protected open fun loadData() {
    }

    protected open fun loadData(isRefresh: Boolean) {
    }

    protected fun click(vararg views: View) = views.forEach { it.setOnClickListener(this) }

    protected open fun disallowLoadDataAtFirst() = { loadDataAtFirst = false }()

    protected fun requestPermission(vararg permission: String, granted: ((String) -> Unit)? = null, denied: ((String) -> Unit)? = null, rationale: String? = null) {
        this.onPermissionsGranted = granted
        this.onPermissionsDenied = denied
        val list = arrayListOf<String>()
        permission.forEach {
            if (EasyPermissions.hasPermissions(mContext, it)) {
                this.onPermissionsGranted?.invoke(it)
            } else {
                list.add(it)
            }
        }
        EasyPermissions.requestPermissions(this, rationale ?: getString(R.string.app_name) + "需要申请权限", 110, *list.toTypedArray())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, permissions: MutableList<String>) {
        permissions.forEach { this.onPermissionsDenied?.invoke(it) }
        this.onPermissionsDenied = null
    }

    override fun onPermissionsGranted(requestCode: Int, permissions: MutableList<String>) {
        permissions.forEach { this.onPermissionsGranted?.invoke(it) }
        this.onPermissionsGranted = null
    }

    protected fun showLoadingDialog(msg: String = "加载中…", cancelable: Boolean = false) = mContext.showLoadingDialog(msg, cancelable)

    protected fun showLoadingDialog(cancelable: Boolean) = mContext.showLoadingDialog(cancelable)

    protected fun dismissLoadingDialog() = mContext.dismissLoadingDialog()

    protected fun handleDataList(recyclerView: AutoRecyclerView?, oldList: MutableList<out Any>, newList: List<Any>, isRefresh: Boolean, hasNextPage: Boolean, loadDataListener: (() -> Unit)) = mContext.handleDataList(recyclerView, oldList, newList, isRefresh, hasNextPage, loadDataListener)
}
