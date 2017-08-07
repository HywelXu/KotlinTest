package org.hywel.kotlintest.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface

/**
 * Created by hywel on 2017/8/7.
 */

class FontManager private constructor(private val mContext: Context) {

    private val mAssetManager: AssetManager? = mContext.assets

    /**
     * 获取 Luminari 字体
     */
    val luminariTypeface: Typeface
        get() {
            var fromAsset: Typeface? = null
            if (mAssetManager != null) {
                fromAsset = Typeface.createFromAsset(mAssetManager, "fonts/Luminari.ttf")
            }
            return fromAsset!!
        }

    /**
     * 获取 Bradley 字体
     */
    val bradleyTypeface: Typeface
        get() {
            var fromAsset: Typeface? = null
            if (mAssetManager != null) {
                fromAsset = Typeface.createFromAsset(mAssetManager, "fonts/Bradley Hand Bold.ttf")
            }
            return fromAsset!!
        }

    /**
     * 获取 Brush 字体
     */
    val brushTypeface: Typeface
        get() {
            var fromAsset: Typeface? = null
            if (mAssetManager != null) {
                fromAsset = Typeface.createFromAsset(mAssetManager, "fonts/Brush Script.ttf")
            }
            return fromAsset!!
        }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var sFontManager: FontManager? = null

        fun getFontManager(context: Context): FontManager {
            if (sFontManager == null) {
                sFontManager = FontManager(context)
            }
            return sFontManager as FontManager
        }
    }

}
