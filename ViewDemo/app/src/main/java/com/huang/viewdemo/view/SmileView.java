package com.huang.viewdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SmileView extends View {

    private Paint mEyePaint;
    private Paint mMouthPaint;
    private Paint mFacePaint;
    private Paint mYellowPaint;
    private int mWidth;
    private int mHeight;
    private RectF mLeftRect;
    private RectF mRightRct;
    private RectF mMouthRect;
    private int xLength = 160;//矩形的长
    private int yLength = 160;//矩形的宽
    private ObjectAnimator mObjectAnimator;

    public SmileView(Context context) {
        super(context);
        init();
    }

    public SmileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /***
     * 初始化画笔
     */
    private void init() {
        mEyePaint = new Paint();
        mEyePaint.setColor(Color.BLACK);
        mEyePaint.setStyle(Paint.Style.FILL);//填充
        mEyePaint.setStrokeWidth(10);
        mEyePaint.setAntiAlias(true);

        mMouthPaint = new Paint();
        mMouthPaint.setColor(Color.RED);
        mMouthPaint.setStyle(Paint.Style.STROKE);//填充
        mMouthPaint.setStrokeWidth(10);
        mMouthPaint.setAntiAlias(true);

        mFacePaint = new Paint();
        mFacePaint.setColor(Color.BLACK);
        mFacePaint.setStyle(Paint.Style.STROKE);//空心
        mFacePaint.setStrokeWidth(10);
        mFacePaint.setAntiAlias(true);

        mYellowPaint = new Paint();
        mYellowPaint.setColor(Color.YELLOW);
        mYellowPaint.setStyle(Paint.Style.FILL);//填充
        mYellowPaint.setStrokeWidth(10);
        mYellowPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = mWidth/2-20;
        Log.d("hxy",radius+" ");
        canvas.translate(mWidth/2,mHeight/2);

        //左右眼睛定位：
        mLeftRect = new RectF(-radius/2,-radius/2,-(radius/2-xLength),-(radius/2-yLength));
        mRightRct = new RectF(radius/2-xLength,-radius/2,radius/2,-(radius/2-yLength));

        mMouthRect = new RectF(-xLength/2,radius/2-yLength,xLength/2,radius/2);
        canvas.drawCircle(0,0,radius,mYellowPaint);
        canvas.drawCircle(0,0,radius,mFacePaint);//画圆脸

        canvas.drawOval(mLeftRect,mEyePaint);//画椭圆 ，眨眼的时候，变为一根直线
        canvas.drawOval(mRightRct,mEyePaint);
        canvas.drawArc(mMouthRect,30,120,false,mMouthPaint);//画嘴巴
    }

    public int getXLength() {
        return xLength;
    }

    public void setXLength(int xLength) {
        this.xLength = xLength;
        invalidate();
    }

    public int getYLength() {
        return yLength;
    }

    public void setYLength(int yLength) {
        this.yLength = yLength;
        invalidate();
    }

    public void startAnimation(){
        mObjectAnimator = ObjectAnimator.ofInt(this, "xLength", xLength, 10,xLength);
        mObjectAnimator.setDuration(400);
        mObjectAnimator.start();

        mObjectAnimator = ObjectAnimator.ofInt(this, "yLength", yLength, 10,yLength);
        mObjectAnimator.setDuration(400);
        mObjectAnimator.start();
    }
}
