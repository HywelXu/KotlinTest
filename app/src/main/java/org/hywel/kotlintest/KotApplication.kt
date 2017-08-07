package org.hywel.kotlintest

import android.app.Application
import android.content.Context
import android.graphics.Typeface

import org.hywel.kotlintest.manager.FontManager

/**
 * Created by hywel on 2017/8/7.
 */

class KotApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mKotApplication = this
        appContext = this.applicationContext

        //get fonts
        val fontManager = FontManager.getFontManager(appContext!!)
        mLuminariTypeface = fontManager.luminariTypeface
        mBradleyTypeface = fontManager.bradleyTypeface
        mBrushTypeface = fontManager.brushTypeface
    }

    companion object {

        lateinit var mKotApplication: KotApplication
        var appContext: Context? = null
            private set

        lateinit var mLuminariTypeface: Typeface
        lateinit var mBradleyTypeface: Typeface
        lateinit var mBrushTypeface: Typeface
    }
}
