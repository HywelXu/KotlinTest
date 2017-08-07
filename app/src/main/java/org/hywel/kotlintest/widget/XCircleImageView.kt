package org.hywel.kotlintest.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast

/**
 * Created by hywel on 2017/6/28.
 */

class XCircleImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : android.support.v7.widget.AppCompatImageView(context, attrs, defStyleAttr), View.OnClickListener {

    private var mPaint: Paint? = null

    init {
        setOnClickListener(this)
        //        init();
    }

    private fun init() {
        //        setScaleType(ScaleType.CENTER_CROP);

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = Color.RED
        mPaint!!.strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable ?: return
//必要步骤，避免由于初始化之前导致的异常错误
        if (width == 0 || height == 0) {
            return
        }

        if (drawable !is BitmapDrawable) {
            return
        }

        val b = drawable.bitmap ?: return

        val bitmap = b.copy(Bitmap.Config.ARGB_8888, true)

        val w = width

        val roundBitmap = getCircleBitmap(bitmap, w)
        canvas.drawBitmap(roundBitmap, 0f, 0f, null)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val defWidth = 300
        val defHeight = 300

        //获取测量值和模式
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val withSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode == View.MeasureSpec.AT_MOST && heightMode == View.MeasureSpec.AT_MOST) {
            //宽和高都为 wap_content 模式，进行设定默认值
            setMeasuredDimension(defWidth, defHeight)
            Log.d("XCircleImageView", "default")
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            //如果只有高为 wrap_content 模式
            setMeasuredDimension(withSize, defHeight)
            Log.d("XCircleImageView", "height wrap_content")
        } else if (widthMode == View.MeasureSpec.AT_MOST && heightMode == View.MeasureSpec.UNSPECIFIED) {
            //如果只有宽为 wrap_content 模式
            setMeasuredDimension(defWidth, defHeight)
            Log.d("XCircleImageView", "width wrap_content")
        } else {
            val min = Math.min(withSize, heightSize)
            //使用获取到的宽高值
            setMeasuredDimension(min, min)
            Log.d("XCircleImageView", "additional")
        }

    }

    private fun getCircleBitmap(bitmap: Bitmap, radius: Int): Bitmap {
        val originBitmap: Bitmap

        if (bitmap.width != radius || bitmap.height != radius) {
            originBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false)
        } else {
            originBitmap = bitmap
        }

        val output = Bitmap.createBitmap(originBitmap.width, originBitmap.height,
                Bitmap.Config.ARGB_8888)

        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, originBitmap.width, originBitmap.height)

        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = Color.parseColor("#BAB399")
        canvas.drawCircle(originBitmap.width / 2 + 0.7f,
                originBitmap.height / 2 + 0.7f, originBitmap.width / 2 + 0.1f, paint)

        //核心部分，设置两张图片的相交模式，在这里就是上面绘制的Circle和下面绘制的Bitmap
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(originBitmap, rect, rect, paint)

        return output
    }

    override fun onClick(view: View) {
        val description = this.contentDescription
        if (!TextUtils.isEmpty(description)) {
            Toast.makeText(view.context, "ContentDescription:" + description, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(view.context, "ContentDescription为空", Toast.LENGTH_SHORT).show()
        }
    }
}
