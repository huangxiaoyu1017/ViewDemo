package com.huang.viewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.huang.viewdemo.R;

public class FolderView extends View {

    private Bitmap mBitmap;
    private Matrix mMatrix;

    public FolderView(Context context) {
        super(context);
        init();
    }



    public FolderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.music);

        mMatrix = new Matrix();



//        float[] dst = { 0, 0,
//                mBitmap.getWidth(), 100,
//                mBitmap.getWidth(), mBitmap.getHeight() - 100,
//                0, mBitmap.getHeight() };
//        //设置的点如下：
//        for(int i = 0;i<8;i++){
//            float[] src = { i, i,
//                    mBitmap.getWidth()/(8-i), mBitmap.getHeight()/(i+1),
//                    mBitmap.getWidth(), mBitmap.getHeight(),
//                    0, mBitmap.getHeight() };
//        }
//
//        mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
//        mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
//        mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
//        mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
