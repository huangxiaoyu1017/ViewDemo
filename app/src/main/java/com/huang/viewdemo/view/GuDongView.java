package com.huang.viewdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.huang.viewdemo.R;

public class GuDongView extends View {

    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;
    private Paint bigPaint;
    private Paint smallPaint;
    private RectF mRectF;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private int progress=0;
    private ObjectAnimator mObjectAnimator;
    private int imageId = R.mipmap.ic_launcher_round;
    private Paint leftPaint;
    private Paint rightPaint;


    public GuDongView(Context context) {
        super(context);
        init();
    }

    public GuDongView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setImageResource(int id){
        imageId = id;
        invalidate();
    }


    private void init() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imageId);
        mBitmap =bitmapDrawable.getBitmap();
        bigPaint = new Paint();
        bigPaint.setColor(Color.WHITE);
        bigPaint.setStyle(Paint.Style.STROKE);
        bigPaint.setStrokeWidth(10);
        bigPaint.setAntiAlias(true);

        smallPaint = new Paint();
        smallPaint.setColor(Color.parseColor("#2CADD7"));
        smallPaint.setStyle(Paint.Style.STROKE);
        smallPaint.setStrokeWidth(10);
        smallPaint.setAntiAlias(true);
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();

        leftPaint = new Paint();
        leftPaint.setColor(Color.WHITE);
        leftPaint.setStyle(Paint.Style.STROKE);
        leftPaint.setStrokeWidth(8);
        leftPaint.setAntiAlias(true);

        rightPaint = new Paint();
        rightPaint.setColor(Color.parseColor("#2CADD7"));
        rightPaint.setStyle(Paint.Style.STROKE);
        rightPaint.setStrokeWidth(8);
        rightPaint.setAntiAlias(true);
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();

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
        //画中间的logo:
        canvas.drawBitmap(mBitmap,mWidth/2-mBitmapWidth/2,mHeight/2-mBitmapHeight/2,bigPaint);

        //画跑步的轨迹：
        mRectF = new RectF(10,10,mWidth-10,mWidth-10);
        canvas.drawArc(mRectF,-180,180,false,bigPaint);

        //画实际跑步的路线：
        canvas.drawArc(mRectF,-180,180/100*progress,false,smallPaint);



        //画首尾两个圆：
        canvas.translate(mWidth/2,mHeight/2);
        if(progress==0){

            canvas.drawCircle(-(mWidth/2-10),0,1,leftPaint);
            canvas.drawCircle(mWidth/2-10,0,1,leftPaint);
        }else if(progress<100){
            canvas.drawCircle(-(mWidth/2-10),0,1,rightPaint);
            canvas.drawCircle(mWidth/2-10,0,1,leftPaint);
        }else{

            canvas.drawCircle(-(mWidth/2-10),0,1,rightPaint);
            canvas.drawCircle(mWidth/2-10,0,1,rightPaint);
        }

    }

    public void startAnimation(){
        mObjectAnimator = ObjectAnimator.ofInt(this, "progress", 0, progress);
        mObjectAnimator.setDuration(4000);
        mObjectAnimator.start();
    }
}
