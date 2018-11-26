package com.huang.viewdemo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PlayView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeigh;
    private int mCenterX;
    private int mCenterY;
    private static int mStrioke = 6;
    private int mChangeHeight = 20;
    private int mFirstHeight;
    private int mSecondHeight;
    private int mThirdHeight;
    private int mFourthHeight;
    private boolean mHasAnimation = false;
    public static final float SCALE = 1.0f;
    private List<Animator> animators = new ArrayList<>();

    float[] scaleYFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE};



    public PlayView(Context context) {
        this(context,null);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#17d6ff"));
        mPaint.setStrokeWidth(mStrioke);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeigh = MeasureSpec.getSize(heightMeasureSpec);

        //以中心为起点，画左右各2条，都是以像素为单位
        mCenterX = mWidth/2;
        mCenterY = mHeigh/2;//作为柱子的标准高度

        mFirstHeight = mCenterY+mChangeHeight*2-mChangeHeight;
        mSecondHeight = mCenterY+mChangeHeight;
        mThirdHeight = mCenterY;
        mFourthHeight = mCenterY+mCenterY/2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation) {
            mHasAnimation = true;
            applyAnimation();
        }
    }

    private void applyAnimation() {
        long[] delays = new long[]{100, 200, 300, 400};
        for (int i = 0; i < 4; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setDuration(800);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleYFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();//刷新界面
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mCenterX-mStrioke*3,mFirstHeight+scaleYFloats[0]*mChangeHeight,mCenterX-mStrioke*3,mCenterY*2,mPaint);//画左边第一条
        canvas.drawLine(mCenterX-mStrioke,mSecondHeight-scaleYFloats[1]*mChangeHeight,mCenterX-mStrioke,mCenterY*2,mPaint);//画左边第二条
        canvas.drawLine(mCenterX+mStrioke,mThirdHeight+scaleYFloats[2]*mChangeHeight,mCenterX+mStrioke,mCenterY*2,mPaint);//画右边第二条，标准线
        canvas.drawLine(mCenterX+mStrioke*3,mFourthHeight-scaleYFloats[3]*(mChangeHeight-5),mCenterX+mStrioke*3,mCenterY*2,mPaint);//画右边第一条

    }

    public void end(){
        if(animators.isEmpty()){
            return;
        }
        for(int i = 0;i<4;i++){
            animators.get(i).end();
        }
    }

    public void finalEnd(){
        if(animators.isEmpty()){
            return;
        }
        for(int i = 0;i<4;i++){
            animators.get(i).cancel();
        }
    }


    public void start(){
        if(animators.isEmpty()){
            return;
        }
        for(int i = 0;i<4;i++){
            animators.get(i).start();
        }
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if(visibility == GONE){
            finalEnd();
        }else if(visibility == INVISIBLE){
            end();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        finalEnd();
    }
}