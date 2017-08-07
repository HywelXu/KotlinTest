package org.hywel.kotlintest.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by hywel on 2017/6/28.
 */

public class XCircleImageView extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener {

    private Paint mPaint;

    public XCircleImageView(Context context) {
        this(context, null);
    }

    public XCircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
//        init();
    }

    private void init() {
//        setScaleType(ScaleType.CENTER_CROP);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null == drawable) return;
        //必要步骤，避免由于初始化之前导致的异常错误
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        if (!(drawable instanceof BitmapDrawable)) {
            return;
        }

        Bitmap b = ((BitmapDrawable) drawable).getBitmap();

        if (null == b) {
            return;
        }

        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth();

        Bitmap roundBitmap = getCircleBitmap(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int defWidth = 300;
        int defHeight = 300;

        //获取测量值和模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //宽和高都为 wap_content 模式，进行设定默认值
            setMeasuredDimension(defWidth, defHeight);
            Log.d("XCircleImageView", "default");
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //如果只有高为 wrap_content 模式
            setMeasuredDimension(withSize, defHeight);
            Log.d("XCircleImageView", "height wrap_content");
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.UNSPECIFIED) {
            //如果只有宽为 wrap_content 模式
            setMeasuredDimension(defWidth, defHeight);
            Log.d("XCircleImageView", "width wrap_content");
        } else {
            int min = Math.min(withSize, heightSize);
            //使用获取到的宽高值
            setMeasuredDimension(min, min);
            Log.d("XCircleImageView", "additional");
        }

    }

    private Bitmap getCircleBitmap(Bitmap bitmap, int radius) {
        Bitmap originBitmap;

        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius) {
            originBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
        } else {
            originBitmap = bitmap;
        }

        Bitmap output = Bitmap.createBitmap(originBitmap.getWidth(), originBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, originBitmap.getWidth(), originBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(originBitmap.getWidth() / 2 + 0.7f,
                originBitmap.getHeight() / 2 + 0.7f, originBitmap.getWidth() / 2 + 0.1f, paint);

        //核心部分，设置两张图片的相交模式，在这里就是上面绘制的Circle和下面绘制的Bitmap
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(originBitmap, rect, rect, paint);

        return output;
    }

    @Override
    public void onClick(View view) {
        CharSequence description = this.getContentDescription();
        if (!TextUtils.isEmpty(description)) {
            Toast.makeText(view.getContext(), "ContentDescription:" + description, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(view.getContext(), "ContentDescription为空", Toast.LENGTH_SHORT).show();
        }
    }
}
