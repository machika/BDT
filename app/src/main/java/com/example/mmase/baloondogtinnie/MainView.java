package com.example.mmase.baloondogtinnie;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by mmase on 2016/01/12.
 */
public class MainView extends SurfaceView
implements SurfaceHolder.Callback, Runnable {

    private int mTinnieX;
    private int mTinnieY;
    private int mTinnieW; //Width
    private int mTinnieH; //Height
    private int mTinnieVx; //verocity
    private int mTinnieVy; //verocity
    private Display mDisplay;
    private final double gravity = 0.1; //g
    private SurfaceHolder mHolder;
    private Thread mThread;
    private Bitmap mTinnieBmp;
    private Paint mPaint;
    private Resources mResources;
    private enum currentStatus { STATUS_TITLE, STATUS_GAME, };
    private final static String TAG = "MainView";
    private final long mTimeDelta = 100;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Preparation
        mResources = getResources();
        mTinnieBmp = BitmapFactory.decodeResource(mResources, R.drawable.tinnie);
        //setBackgroundColor(Color.WHITE);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTinnieX = 200;
        mTinnieY = 100;
        mTinnieW = 70;
        mTinnieH = 85;
        Log.i(TAG, "mTinnieW=" + String.valueOf(mTinnieW) + ", mTinnieH=" + String.valueOf(mTinnieH) + ", View Heiht=" + getHeight());

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setFixedSize(500,500);
        mThread = new Thread(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mThread != null)
            mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread  = null;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mTinnieVy = mTinnieVy - 50;
        }
        return true;
    }

    @Override
    public void run() {
        while (mThread != null) {
            Canvas canvas = mHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE); //Clear
                Rect dstRect = new Rect(mTinnieX, mTinnieY, mTinnieX + mTinnieW, mTinnieY + mTinnieH);
                canvas.drawBitmap(mTinnieBmp, null, dstRect, mPaint);

                //Calcurate next position
                mTinnieVy = mTinnieVy + (int) (0.5 * gravity * 50);
                if(mTinnieY + mTinnieH + mTinnieVy < 500 && mTinnieY + mTinnieVy > 0) {
                    mTinnieY = mTinnieY + mTinnieVy;
                }

            }
            mHolder.unlockCanvasAndPost(canvas);

            try {
                    //Log.v(TAG, "Thread running");
                    Thread.sleep(mTimeDelta);
            } catch (InterruptedException ex) {
                    Log.w(TAG, "interrupted");
            }
        }
    }
}
