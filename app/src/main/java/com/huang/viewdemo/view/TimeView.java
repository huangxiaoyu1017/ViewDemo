package com.huang.viewdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TimeView extends View {

    private Paint mBluePaint;
    private Paint mGrayPaint;//#2CADD7
    private int mWidth;
    private int mHeight;
    private int numberLine = 30; //默认画30根线条：
    private Paint textPaint;
    private Paint circlePaint;
    private int progress= 0;
    private ObjectAnimator mObjectAnimator;

    public TimeView(Context context) {
        super(context);
        init();
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public int getProgress() {
        return progress;
    }

    /**
     * progress进度需要和转盘刻度对应：
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    private void init() {
        mBluePaint = new Paint();
        mBluePaint.setColor(Color.parseColor("#2CADD7"));
        mBluePaint.setStyle(Paint.Style.STROKE);
        mBluePaint.setStrokeWidth(10);
        mBluePaint.setAntiAlias(true);

        mGrayPaint = new Paint();
        mGrayPaint.setColor(Color.WHITE);
        mGrayPaint.setStyle(Paint.Style.STROKE);
        mGrayPaint.setStrokeWidth(10);
        mGrayPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#2CADD7"));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setStrokeWidth(10);
        circlePaint.setAntiAlias(true);
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

        //画中间的文字：
        canvas.drawText("180s",mWidth/2,mHeight/2,textPaint);

        //画布转移到中心：
        canvas.translate(mWidth/2,mHeight/2);

        //从左上角开始画起：
        for(int i = 0;i<numberLine;i++){
            //在某一个点处画圆：
            if(i==0){//画圆形
                canvas.drawCircle(0,mHeight/2-20,20,circlePaint);
            }else{
                if(i<=(30*progress)/180){
                    canvas.drawLine(0, mHeight/2, 0,mHeight/2-40 , mBluePaint);
                }else{
                    canvas.drawLine(0, mHeight/2, 0,mHeight/2-40 , mGrayPaint);
                }
            }
            canvas.rotate(12);
        }

    }

    public void startAnimation(){
        mObjectAnimator = ObjectAnimator.ofInt(this, "progress", 0, progress);
        mObjectAnimator.setDuration(4000);
        mObjectAnimator.start();
    }
}
