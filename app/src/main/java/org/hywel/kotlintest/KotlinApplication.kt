package org.hywel.kotlintest

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import org.hywel.kotlintest.utils.AndroidTools

/**
 * Created by hywel on 2017/8/1.
 */
class KotlinApplication : Application() {

    var kotlinApplication: KotlinApplication? = null
    var mAppContext: Context? = null

    var screenHeight: Int? = 0
    var screenWidth: Int? = 0

    var tfAuthorFromAsset: Typeface? = null
    var tfContentFromAsset: Typeface? = null
    var tfTimeFromAsset: Typeface? = null

    override fun onCreate() {
        super.onCreate()

        kotlinApplication = this
        mAppContext = kotlinApplication!!.applicationContext

        screenHeight = AndroidTools.getScreenHeight(mAppContext)
        screenWidth = AndroidTools.getScreenWidth(mAppContext)

        tfAuthorFromAsset = Typeface.createFromAsset(assets, "fonts/Luminari.ttf")
        tfContentFromAsset = Typeface.createFromAsset(assets, "fonts/Bradley Hand Bold.ttf")
//        val tfTimeFromAsset = Typeface.createFromAsset(assets, "fonts/tesla.ttf")
        tfTimeFromAsset = Typeface.createFromAsset(assets, "fonts/Brush Script.ttf")

    }

}