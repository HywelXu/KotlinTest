package org.hywel.kotlintest.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.SystemClock
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

@SuppressLint("StaticFieldLeak")
object AndroidTools {

    private var appContext: Context? = null

    /**
     * 获取版本号

     * @return 当前应用的版本号 android:versionCode="1"
     */
    fun getVersionCode(context: Context): Int {
        var version = 0
        try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            version = info.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return version
    }

    /**
     * 获取版本名称

     * @return 当前应用的版本名称 android:versionName="1.0.3"
     */
    fun getVersionName(context: Context): String {
        var version = ""
        try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            version = info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return version
    }

    /**
     * 判断应用程序是否处于前台

     * @param context
     * *
     * @return
     */
    fun isForegroudApp(context: Context): Boolean {
        val packageName = context.packageName
        val activityManager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val tasksInfo = activityManager.getRunningTasks(1)
        if (tasksInfo.size > 0) {
            // 应用程序位于堆栈的顶层
            val topPkgName = tasksInfo[0].topActivity.packageName
            if (packageName == topPkgName) {
                return true
            }
        }
        return false
    }

    /**
     * 获取系统语言
     */
    fun getLanguage(context: Context): String {
        val locale = context.resources.configuration.locale ?: return "zh"
        val language = locale.language
        if (TextUtils.isEmpty(language)) {
            return "zh"
        }
        return language
    }

    /**
     * 获得屏幕参数：主要是分辨率 width * height
     */
    fun getDisplay(context: Context): String {
        val display = getScreenWidth(context).toString() + "*" + getScreenHeight(context)
        return display
    }

    /**
     * Get the screen width

     * @author mapeng_thun
     * *
     * @param context
     * *
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    /**
     * Get the screen height

     * @author mapeng_thun
     * *
     * @param context
     * *
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    var applicationContext: Context
        get() {
            if (appContext == null) {
                throw RuntimeException("ApplicationContext not initialized!")
            }
            return appContext!!
        }
        set(context) {
            appContext = context.applicationContext
        }

    @SuppressLint("MissingPermission")
    fun getIMEI(context: Context): String {
        val mTelephonyMgr = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val imei = mTelephonyMgr.deviceId
        return imei
    }

    @SuppressLint("MissingPermission")
    fun getIMSI(context: Context): String {
        val mTelephonyMgr = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val imsi = mTelephonyMgr.subscriberId
        return imsi
    }

    /**
     * 用户的os的sdk的版本是否大于等于指定的版本

     * @param apiLevel
     * *
     * @return
     */
    fun isCompatibleApiLevel(apiLevel: Int): Boolean {
        return android.os.Build.VERSION.SDK_INT >= apiLevel
    }

    /**
     * 显示键盘
     */
    fun showKeyboard(context: Context, view: View) {
        val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        imm.showSoftInput(view, 0)
        setEtSelection(view)
    }

    /**
     * 通过模拟点击事件,使View获取焦点,进而弹出软键盘

     * @param view
     */
    fun showKeyboardOtherway(view: View) {
        Handler().postDelayed({
            // location
            val location = IntArray(2)
            view.getLocationOnScreen(location)
            var xOffset = (location[0] + view.paddingLeft).toFloat()
            // 设置EditText光标位置
            if (view is EditText) {
                val str = view.text.toString()
                // 计算画布上的字符串宽度(所占像素宽度)
                val paint = Paint()
                xOffset += paint.measureText(str)
            }

            view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, xOffset, 0f, 0))
            view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, xOffset, 0f, 0))
        }, 200)

    }

    /**
     * 隐藏键盘
     */
    fun hideKeyboard(context: Context, view: View?) {
        if (view == null) {
            return
        }
        val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    fun isKeyboardShowing(context: Context): Boolean {
        val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.isAcceptingText
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(paramActivity: Activity): Int {
        val display = paramActivity.windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        return metrics.heightPixels
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(paramActivity: Activity): Int {
        val localRect = Rect()
        paramActivity.window.decorView.getWindowVisibleDisplayFrame(localRect)
        return localRect.top
    }

    /**
     * 获取ActionBar的高度
     */
    fun getActionBarHeight(paramActivity: Activity): Int {
        // return
        // paramActivity.getResources().getDimensionPixelSize(R.dimen.topbar_height);
        return 0
    }

    /**
     * below actionbar, above softkeyboard
     */
    fun getAppContentHeight(paramActivity: Activity): Int {
        return (getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
                - getActionBarHeight(paramActivity) - getKeyboardHeight(paramActivity))
    }

    /**
     * 获取窗口高度 below status bar,include actionbar, above softkeyboard
     */
    fun getAppHeight(paramActivity: Activity): Int {
        val localRect = Rect()
        paramActivity.window.decorView.getWindowVisibleDisplayFrame(localRect)
        return localRect.height()
    }

    /**
     * 获取键盘高度
     */
    fun getKeyboardHeight(paramActivity: Activity): Int {
        val height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
        -getAppHeight(paramActivity)

        return height
    }

    /**
     * 键盘是否显示
     */
    fun isKeyBoardShow(paramActivity: Activity): Boolean {
        val height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
        -getAppHeight(paramActivity)
        return height != 0
    }

    /**
     * 设置光标位置
     */
    fun setEtSelection(view: View) {
        if (view is EditText) {
            val et = view
            val str = et.text.toString()
            if (!TextUtils.isEmpty(str)) {
                et.setSelection(str.length)
            }
        }
    }

    /**
     * EditText获取焦点,并弹出软键盘

     * @param 文本编辑框
     */
    fun requestFocus(context: Context, v: EditText) {
        v.requestFocus()
        v.setSelection(v.text.toString().length)

        AndroidTools.showKeyboard(context, v)
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun keyboard(context: Activity) {
        context.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED)
    }

    /**
     * 判断 List 是否可用
     */
    fun <E> isListValidate(list: List<E>?): Boolean {
        if ((list != null) and (list!!.size > 0)) {
            return true
        }
        return false
    }

}
