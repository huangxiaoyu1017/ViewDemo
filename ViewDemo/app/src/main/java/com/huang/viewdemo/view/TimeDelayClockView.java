package com.huang.viewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.huang.viewdemo.R;
import com.huang.viewdemo.util.DensityUtil;

public class TimeDelayClockView extends View {

    private static final int MSG_UPDATE = 1;
    private static final int MSG_TEXT = 2;
    private Context mContext;

    private Paint bigCiclePaint;
    private float mRadius; //园的半径
    private int mBigCircleColor;
    private float mBigCircleWidth;
    private Paint mBigCirclePaint;
    private int mCx;
    private int mCy;
    private float mPointRatio; //市重点到圆心的距离
    private Paint calibrationPaint; //画刻度

    private Paint hourPaint; //画时钟
    private Paint minutePaint; //画分钟
    private Paint bigTextPaint; //画文字
    private Paint smallTextPaint;
    private int count = 0;
    private int deadline = 3;
    private int textCount = 8;
    private OnHideListener mOnHideListener;
    private int baseColor = Color.parseColor("#ffd824");


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE:
                    textCount--;
                    Log.d("hxy", "textCount=" + textCount);
                    if (textCount > 0) {
                        if (textCount % 2 == 0) {
                            bigTextPaint.setColor(baseColor);
                        } else {
                            bigTextPaint.setColor(Color.TRANSPARENT);
                        }
                        mHandler.sendEmptyMessageDelayed(MSG_UPDATE, 800);
                        invalidate();
                    } else {
                        bigTextPaint.setColor(baseColor);
                        mHandler.removeMessages(MSG_UPDATE);
                        textCount = 8;
                    }
                    break;
                default:
                    break;

            }
            return false;
        }
    });



    public TimeDelayClockView(Context context) {
        this(context, null);
    }

    public TimeDelayClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public TimeDelayClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.TClock, defStyleAttr, 0);
        initAttr(typedArray);
        initPaint();
        typedArray.recycle();
    }


    /**
     * @param typedArray
     */
    private void initAttr(TypedArray typedArray) {
        mRadius = typedArray.getDimension(R.styleable.TClock_radius, 0);
        initBigCircleAttr(typedArray);

    }


    /**
     *
     */
    private void initPaint() {
        mBigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        calibrationPaint = new Paint();
        calibrationPaint.setColor(baseColor);
        calibrationPaint.setStyle(Paint.Style.FILL);
        calibrationPaint.setStrokeWidth(12);

        hourPaint = new Paint();
        hourPaint.setColor(baseColor);
        hourPaint.setStrokeWidth(10);

        minutePaint = new Paint();
        minutePaint.setColor(baseColor);
        minutePaint.setStrokeWidth(10);

        bigTextPaint = new Paint();
        bigTextPaint.setColor(baseColor);
        bigTextPaint.setAntiAlias(true);
        bigTextPaint.setTextAlign(Paint.Align.CENTER);
        bigTextPaint.setTextSize(62);


        smallTextPaint = new Paint();
        smallTextPaint.setColor(baseColor);
        smallTextPaint.setAntiAlias(true);
        smallTextPaint.setTextAlign(Paint.Align.CENTER);
        smallTextPaint.setTextSize(20);

    }


    /**
     * @param typedArray
     */
    private void initBigCircleAttr(TypedArray typedArray) {
        mBigCircleColor = typedArray.getColor(R.styleable.TClock_big_circle_color, baseColor);
        mBigCircleWidth = typedArray.getDimension(R.styleable.TClock_big_circle_width, DensityUtil.dp2px(mContext, 8));
        mPointRatio = typedArray.getFloat(R.styleable.TClock_point_radius_ratio, (float) 0.88);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCx = getWidth() / 2;
        mCy = getHeight() / 2;
        mRadius = mRadius == 0 ? (float) (Math.min(getWidth(), getHeight()) / 2.4) : mRadius;
        //画大圆
        drawBigCircle(canvas, mRadius);

        //画数字
        drawNumber(canvas);

        //画时钟
        canvas.save();
        canvas.rotate(90, mCx, mCy); //旋转画布准备画时针
        //canvas.drawLine(mCx, mCy, mCx, mRadius - 87, hourPaint); //画时针
        //canvas.drawLine(mCx, mCy, mCx, -2 * mCy + 80, hourPaint); //画时针、
        canvas.drawLine(mCx, mCy, mCx, (float) (mRadius * 0.7), hourPaint); //画时针
        //   Log.d("hxy", "mRadius=" + mRadius);
        canvas.restore(); //重置画布

        //画分钟：
        float minuteDegree = count * 30;
        canvas.save();
        canvas.rotate(minuteDegree, mCx, mCy);
        canvas.drawLine(mCx, mCy + 5, mCx, (float) (mRadius * 0.6), hourPaint); //画分钟
        canvas.restore();

        //画文字
        drawMsg(canvas);


    }

    /**
     * @param canvas
     * @param radius
     */
    private void drawBigCircle(Canvas canvas, float radius) {
        mBigCirclePaint.setStrokeWidth(mBigCircleWidth);
        mBigCirclePaint.setColor(mBigCircleColor);
        mBigCirclePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCx, mCy, mRadius, mBigCirclePaint);
        canvas.save();
    }

    /**
     * @param canvas
     */
    private void drawNumber(Canvas canvas) {
        for (int i = 0; i <= 3; i++) {
            canvas.save();
            canvas.rotate(i * 30, mCx, mCy); //先画1点，从1到12
            canvas.drawLine(mCx, mCy - mRadius + 34 / 2, mCy, mCy - mRadius + 34, calibrationPaint); //画刻度
            canvas.restore();
        }
    }


    /**
     * 画文字：
     *
     * @param canvas
     */
    private void drawMsg(Canvas canvas) {
        String msg = deadline + "";

        String smallMsg = "min";
        float measureNumber1 = smallTextPaint.measureText(msg);
        Paint.FontMetricsInt fontMetricsInt1 = smallTextPaint.getFontMetricsInt();
        int textHeight1 = fontMetricsInt1.bottom - fontMetricsInt1.top;
        canvas.drawText(smallMsg, (float) (mCx + measureNumber1 * 1.5), (float) (mCy + textHeight1), smallTextPaint);

        float measureNumber = bigTextPaint.measureText(msg);
        Paint.FontMetricsInt fontMetricsInt = bigTextPaint.getFontMetricsInt();
        int textHeight = fontMetricsInt.bottom - fontMetricsInt.top;
        canvas.drawText(msg, (float) (mCx - measureNumber / 1.5), (float) (mCy + textHeight / 3.5), bigTextPaint);

        canvas.save();

    }

    /**
     * 需要重新绘制ClockView
     */
    public void reDrawClock() {
        count = 0;
        deadline = 3;
        textCount = 8;
        bigTextPaint.setColor(baseColor);
        invalidate();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    /**
     * @param mOnHideListener
     */
    public void setOnHideListener(OnHideListener mOnHideListener) {
        this.mOnHideListener = mOnHideListener;
    }

    /**
     *
     */
    public interface OnHideListener {
        /**
         *
         */
        void onHide();

    }


    /**
     * 更新数字的方法
     *
     * @param data 更新分钟转动方向
     * @param text 更新数字
     */
    public void setNewData(int data, int text) {
        bigTextPaint.setColor(baseColor);
        count = data;
        deadline = text;
        invalidate();
    }




}
