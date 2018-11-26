package com.huang.viewdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.huang.viewdemo.R;

public class SleepView extends View {

    private Paint blackPaint;
    private int mWidth;
    private int mHeight;
    private int raduis;
    private int progress = 0;
    private Paint yellowPaint;
    private Paint smallPaint;
    private Paint linePaint1;
    private Paint linePaint2;
    private Paint clockPaint;
    private Paint headTailPaint;
    private Paint headTailPaint1;
    private Paint textPaint;
    private RectF mRectF;
    private int imageId = R.mipmap.ic_launcher_round;
    private Bitmap mBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private Paint bigPaint;
    private ObjectAnimator mObjectAnimator;



    public SleepView(Context context) {
        super(context);
        init();
    }


    public SleepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化画笔：
     */
    private void init() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imageId);
        mBitmap = bitmapDrawable.getBitmap();
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();

        blackPaint = new Paint();
        blackPaint.setColor(Color.parseColor("#171717"));
        blackPaint.setStyle(Paint.Style.STROKE);//填充
        blackPaint.setStrokeWidth(60);
        blackPaint.setAntiAlias(true);

        bigPaint = new Paint();
        bigPaint.setColor(Color.BLACK);
        bigPaint.setStyle(Paint.Style.STROKE);
        bigPaint.setStrokeWidth(1);
        bigPaint.setAntiAlias(true);


        yellowPaint = new Paint();
        yellowPaint.setStyle(Paint.Style.STROKE);//填充
        yellowPaint.setStrokeWidth(60);
        yellowPaint.setAntiAlias(true);

        //头部画笔
        headTailPaint = new Paint();
        headTailPaint.setStyle(Paint.Style.STROKE);//填充
        headTailPaint.setColor(Color.parseColor("#ff9801"));
        headTailPaint.setStrokeWidth(58);
        headTailPaint.setAntiAlias(true);

        //尾部画笔
        headTailPaint1 = new Paint();
        headTailPaint1.setStyle(Paint.Style.STROKE);//填充
        headTailPaint1.setColor(Color.parseColor("#ffcb05"));
        headTailPaint1.setStrokeWidth(58);
        headTailPaint1.setAntiAlias(true);

        smallPaint = new Paint();
        smallPaint.setColor(Color.BLACK);
        smallPaint.setStyle(Paint.Style.STROKE);
        smallPaint.setStrokeWidth(52);
        smallPaint.setAntiAlias(true);

        linePaint1 = new Paint();
        linePaint1.setColor(Color.WHITE);
        linePaint1.setStyle(Paint.Style.FILL);
        linePaint1.setStrokeWidth(5);
        linePaint1.setAntiAlias(true);

        linePaint2 = new Paint();
        linePaint2.setColor(Color.GRAY);
        linePaint2.setStyle(Paint.Style.FILL);
        linePaint2.setStrokeWidth(2);
        linePaint2.setAntiAlias(true);

        //画数字：
        clockPaint = new Paint();
        clockPaint.setColor(Color.parseColor("#8F8F91"));
        clockPaint.setTextSize(28);
        clockPaint.setTextAlign(Paint.Align.CENTER);
        clockPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(36);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
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

        setMeasuredDimension(mWidth, mHeight);
        raduis = mWidth / 2 - 40;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //画中间的文字：将进度转化为文字：2*progress min
        int number = 2*progress;
        String text = "";
        int hour = 0;
        int min = 0;
        if(number>60){
            hour = number/60;
            min = number%60;
            text= hour+ "小时"+min+"分钟";
        }else{
            text =  number+"分钟";
        }

        canvas.drawText(text, mWidth / 2, mHeight / 2 + 20, textPaint);
        //坐标转移到中心：
        canvas.translate(mWidth / 2, mHeight / 2);

        //画外面的大圆：
        canvas.drawCircle(0, 0, raduis, blackPaint);

        //画文字：不要旋转
        for (int i = 0; i < 12; i++) {
            //先从12位置开始算起：
            if (i == 0) {
                canvas.drawText("12", (float) ((raduis - 80) * Math.cos(Math.toRadians(90))), (float) (-(raduis - 80) * Math.sin(Math.toRadians(90))), clockPaint);
            } else if (i <= 3) {
                if(i==3){
                    canvas.drawText(i + "", (float) ((raduis - 80) * Math.cos(Math.toRadians(90-i * 30))+10), (float) (-(raduis - 80) * Math.sin(Math.toRadians(90-i * 30))+10), clockPaint);
                }else{
                    canvas.drawText(i + "", (float) ((raduis - 80) * Math.cos(Math.toRadians(90-i * 30))), (float) (-(raduis - 80) * Math.sin(Math.toRadians(90-i * 30))), clockPaint);
                }

            } else if (i <= 6) {
                int newIndex = i-3;
                canvas.drawText(i + "", (float) ((raduis - 80) * Math.cos(Math.toRadians(newIndex * 30))), (float) (((raduis - 80) * Math.sin(Math.toRadians(newIndex * 30))+20)), clockPaint);
            } else if (i <= 9) {
                int newIndex1 = i-6;
                if(i==9){
                    canvas.drawText(i + "", (float) (-(raduis - 80) * Math.cos(Math.toRadians(90 - newIndex1 * 30))-10), (float) ((raduis - 80) * Math.sin(Math.toRadians(90 - newIndex1 * 30))+10), clockPaint);
                }else{
                    canvas.drawText(i + "", (float) (-(raduis - 80) * Math.cos(Math.toRadians(90 - newIndex1 * 30))), (float) ((raduis - 80) * Math.sin(Math.toRadians(90 - newIndex1 * 30))+20), clockPaint);
                }
            } else if (i <= 11) {
                int newIndex2 = i-9;
                canvas.drawText(i + "", (float) (-(raduis - 80) * Math.cos(Math.toRadians(newIndex2 * 30))), (float) (-(raduis - 80) * Math.sin(Math.toRadians(newIndex2* 30))), clockPaint);
            }
        }


        //画刻度：
        for (int i = 0; i < 48; i++) {
            if (i % 4 == 0) {
                canvas.drawLine(0, raduis - 20 - 30, 0, raduis - 5 - 30, linePaint1);
            } else {
                canvas.drawLine(0, raduis - 20 - 30, 0, raduis - 5 - 30, linePaint2);
            }
            canvas.rotate(7.5f);
        }


        mRectF = new RectF(-mWidth / 2 + 40, -mWidth / 2 + 40, mWidth / 2 - 40, mWidth / 2 - 40);

        LinearGradient linearGradient = new LinearGradient((float) (raduis * Math.cos(Math.toRadians(progress / 2))), (float) (-raduis * Math.sin(Math.toRadians(progress / 2))), (float) (raduis * Math.cos(Math.toRadians(progress / 2))), (float) (raduis * Math.sin(Math.toRadians(progress / 2))), Color.parseColor("#ff9801"), Color.parseColor("#ffcb05"), Shader.TileMode.CLAMP);
        yellowPaint.setShader(linearGradient);
        //画圆弧：
        canvas.drawArc(mRectF, -progress / 2, progress, false, yellowPaint);
        //画圆弧的两个圆：在圆弧的首尾处：

        //画首尾2端：头部：尾部，角度转化为弧度
        canvas.drawCircle((float) (raduis * Math.cos(Math.toRadians(progress / 2))), (float) (-raduis * Math.sin(Math.toRadians(progress / 2))), 1, headTailPaint);
        canvas.drawCircle((float) (raduis * Math.cos(Math.toRadians(progress / 2))), (float) (raduis * Math.sin(Math.toRadians(progress / 2))), 1, headTailPaint1);

        //画中间套住的两个圆
        canvas.drawCircle((float) (raduis * Math.cos(Math.toRadians(progress / 2 - 0.3))), (float) (-raduis * Math.sin(Math.toRadians(progress / 2 - 0.3))), 1, smallPaint);
        canvas.drawCircle((float) (raduis * Math.cos(Math.toRadians(progress / 2 - 0.3))), (float) (raduis * Math.sin(Math.toRadians(progress / 2 - 0.3))), 1, smallPaint);

    }

    public void startAnimation(){
        mObjectAnimator = ObjectAnimator.ofInt(this, "progress", 0, progress);
        mObjectAnimator.setDuration(4000);
        mObjectAnimator.start();
    }
}
