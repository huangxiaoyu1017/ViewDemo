package com.huang.viewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SwipeView extends  View {

    private static final int MSG_UPDATE = 1;
    private Paint mSmallPaint;
    private Paint mBigPaint;
    private Paint mSwipePaint;
    private int mWidth;
    private int mHeight;
    private int radius;
    private int progress = 0;
    private RectF mRectF;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==MSG_UPDATE){
                mHandler.removeMessages(MSG_UPDATE);
                progress+=3;
                if(progress>=360){
                    progress =360;
                }
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE,30);
                invalidate();

            }
            return false;
        }
    });

    public SwipeView(Context context) {
        super(context);
        init();
    }

    public SwipeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private void init() {
        mSmallPaint = new Paint();
        mSmallPaint.setColor(Color.WHITE);
        mSmallPaint.setStrokeWidth(15);
        mSmallPaint.setStyle(Paint.Style.FILL);//画实心的圆
        mSmallPaint.setAntiAlias(true);

        mBigPaint = new Paint();
        mBigPaint.setColor(Color.WHITE);
        mBigPaint.setStrokeWidth(15);
        mBigPaint.setStyle(Paint.Style.STROKE);//画空心的圆
        mBigPaint.setAntiAlias(true);

        mSwipePaint = new Paint();
        mSwipePaint.setColor(Color.parseColor("#66029ff1"));
        mSwipePaint.setStrokeWidth(5);
        mSwipePaint.setStyle(Paint.Style.FILL);//画空心的圆
        mSwipePaint.setAntiAlias(true);


    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        setMeasuredDimension(mWidth,mHeight);
        radius = mHeight/2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawCircle(0,0,radius-48,mSmallPaint);
        canvas.drawCircle(0,0,radius-18,mBigPaint);

        //开始画扫描角度：90读开始，顺时针画
        mRectF = new RectF(-mWidth/2+10,-mHeight/2+10,mWidth/2-10,mHeight/2-10);
        canvas.drawArc(mRectF,-90,progress,true,mSwipePaint);
        if(progress==360){
            mHandler.removeMessages(MSG_UPDATE);
        }else{
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE,500);
        }
    }

}
