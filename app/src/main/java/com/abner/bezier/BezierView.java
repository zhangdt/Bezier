package com.abner.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.NumberFormat;

/**
 * Created by abner on 2016/7/15.
 */
public class BezierView extends View {

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mPaint, mPaint2;
    private Path mPath = new Path();
    protected int mViewWidth, mViewHeight;
    protected int mWidth, mHeight;
    private float r, rArc, x;
    private float percent = 0.5f;
    int i = 1;
    private RectF rectF;
    private PointF mPointF = new PointF(0, 0);


    public BezierView(Context context) {
        this(context, null);

    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(100);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.CYAN);
        mPaint2.setStrokeWidth(8);
        mPaint2.setStyle(Paint.Style.FILL);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = w;
        mViewHeight = h;

        mWidth = mViewWidth - getPaddingLeft() - getPaddingRight();
        mHeight = mViewHeight - getPaddingTop() - getPaddingBottom();

        r = Math.min(mWidth, mHeight) * 0.4f;
        rectF = new RectF(-r, -r, r, r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        canvas.drawCircle(0, 0, r, mPaint);
        rArc = r * (1 - 2 * percent);
        double angle = Math.acos((double) rArc / r);
        x = r * (float) Math.sin(angle);
        mPath.addArc(rectF, 90 - (float) Math.toDegrees(angle), (float) Math.toDegrees(angle) * 2);
        mPath.moveTo(-x, rArc);
        Log.i("TAG", x + "");
        Log.i("TAG", r + "");
        Log.i("TAG", angle + "");

        mPath.rQuadTo(x / 2 + i++, -r / 8, x + i++, 0);
        mPath.rQuadTo(x / 2 + i++, r / 8, x + i++, 0);
        canvas.drawPath(mPath, mPaint2);
        mPath.rewind();
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(1);
//        textCenter(new String[]{numberFormat.format(percent)},mPaint,canvas,mPointF, Paint.Align.CENTER);
    }

    /**
     * 多行文本居中、居右、居左
     *
     * @param strings 文本字符串列表
     * @param paint   画笔
     * @param canvas  画布
     * @param point   点的坐标
     * @param align   居中、居右、居左
     */
    protected void textCenter(String[] strings, Paint paint, Canvas canvas, PointF point, Paint.Align align) {
        paint.setTextAlign(align);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int length = strings.length;
        float total = (length - 1) * (-top + bottom) + (-fontMetrics.ascent + fontMetrics.descent);
        float offset = total / 2 - bottom;
        for (int i = 0; i < length; i++) {
            float yAxis = -(length - i - 1) * (-top + bottom) + offset;
            canvas.drawText(strings[i], point.x, point.y + yAxis, paint);
        }
    }
}
