package com.huang.viewdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LoadingView extends View {

    private Paint mPaint;
    private int progress = 0;
    private int mWidth;//控件的宽
    private int mHeight;//控件的高
    private ObjectAnimator mObjectAnimator;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {

        this.progress = progress;

        invalidate();
    }



    public LoadingView(Context context) {
        super(context);
        init();

    }

    public LoadingView(Context context, @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint= new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        setMeasuredDimension(mWidth, mHeight);
        Log.d("hxy 控件宽高：","mWidth= "+mWidth+" mHeight= "+mHeight);
    }

    /**
     * public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter,
     @NonNull Paint paint)
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
       //开始画圆弧：在矩形区域中画圆：
        mPaint.setColor(Color.parseColor("#2CADD7"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);//画笔宽度有点大，为了显示圆，可以将矩形区域扩大
        mPaint.setAntiAlias(true);
        RectF rectF = new RectF(10,10,mWidth-10,mHeight-10);
        canvas.drawArc(rectF,0, (float) ((360/100+0.7)*progress),false,mPaint);

        //画文字：
        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(40);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
        canvas.drawText("进度"+progress+"%",mWidth/2,mHeight/2,mPaint);
    }

    public void startAnimation(){
        mObjectAnimator = ObjectAnimator.ofInt(this, "progress", 0, progress);
        mObjectAnimator.setDuration(4000);
        mObjectAnimator.start();
    }


}
