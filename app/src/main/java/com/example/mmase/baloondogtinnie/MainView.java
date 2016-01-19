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
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;

/**
 * Created by mmase on 2016/01/12.
 */
public class MainView extends SurfaceView
implements SurfaceHolder.Callback, Runnable {

    private int mScore;
    private Item mTinnie;
    private int mW;
    private int mH;
    private Display mDisplay;
    private SurfaceHolder mHolder;
    private Thread mThread;
    private Paint mPaint;
    private Resources mResources;
    private enum CURRENT_STATUS { TITLE, ACTION, };
    private enum POSSIBLE_ITEMS { ITEM_CLOUD, };
    private CURRENT_STATUS mStatus;
    private final static String TAG = "MainView";
    private final long mTimeDelta = 5;

    private ArrayList<Item> mExistingBackgroundItemList;
    private final int mMaxBackgroundItemsNum = 10;

    private ArrayList<Item> mExistingForegroundItemList;
    private final int mMaxForegroundItemsNum = 3; //soft limit
    private final Object mExistingForegroundItemLock;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Preparation
        mResources = getResources();
        //mTinnieBmp = BitmapFactory.decodeResource(mResources, R.drawable.tinnie);
        //setBackgroundColor(Color.WHITE);

        mScore = 0;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(200);
        //mPaint.setTypeface(Typeface.BOLD);
        mTinnie = new Tinnie(200, 300, 150, 215, 0, 1, getResources(), (int)mTimeDelta);

        mW = 0;
        mH = 0;

        mHolder = getHolder();
        mHolder.addCallback(this);
        mThread = new Thread(this);

        mStatus = CURRENT_STATUS.TITLE;

        mExistingForegroundItemList = new ArrayList<Item>();
        mExistingForegroundItemLock = new Object();
        mExistingBackgroundItemList = new ArrayList<Item>();

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
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged: w=" + String.valueOf(w) + ", h=" + String.valueOf(h) + ",oldw=" + String.valueOf(oldw) + ", oldh=" + String.valueOf(oldh));
        mHolder.setFixedSize(w, h);
        mW = w;
        mH = h;
        mTinnie.setYBoarder(h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mTinnie.setVy(mTinnie.getVy() - 20);
            Log.i(TAG, "Up! Vy=" + String.valueOf(mTinnie.getVy()));
        }
        return true;
    }

    @Override
    public void run() {
        while (mThread != null) {
            Canvas canvas = mHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.rgb(230, 255, 255)); //Clear

                //Randomly generate items
                if (mExistingBackgroundItemList.size() < mMaxBackgroundItemsNum) {
                    if ((int) (Math.random() * 100) <= 3) {  //Cloud 3%
                        mExistingBackgroundItemList.add(new Cloud(mW, (int) (Math.random() * mH), mResources));
                    }
                }

                if (mExistingForegroundItemList.size() < mMaxForegroundItemsNum) {
                    if ((int)(Math.random() * 100) <= 1 ) {  //rabbit 2%
                        Rabbit newRabbit = new Rabbit(mW, (int) (Math.random() * mH), mResources);
                        if (mExistingForegroundItemList.contains(newRabbit) == false) {
                            mExistingForegroundItemList.add(newRabbit);
                        }
                    }
                    if ((int)(Math.random() * 100) <= 1 ) {  //Penguin 2%
                        Penguin newPenguin = new Penguin(mW, (int) (Math.random() * mH), mResources);
                        if (mExistingForegroundItemList.contains(newPenguin) == false) {
                            mExistingForegroundItemList.add(newPenguin);
                        }
                    }
                    if ((int)(Math.random() * 100) <= 1 ) {  //lion 2%
                        Lion newLion = new Lion(mW, (int) (Math.random() * mH), mResources);
                        if (mExistingForegroundItemList.contains(newLion) == false) {
                            mExistingForegroundItemList.add(newLion);
                        }
                    }
                    if ((int)(Math.random() * 100) <= 1 ) {  //lion 2%
                        Bat newBat = new Bat(mW, (int) (Math.random() * mH), mResources);
                        if (mExistingForegroundItemList.contains(newBat) == false) {
                            mExistingForegroundItemList.add(newBat);
                        }
                    }
                    if ((int)(Math.random() * 100) <= 1 ) {  //burudog 2%
                        Burudog newBurudog = new Burudog(mW, (int) (Math.random() * mH), mResources);
                        if (mExistingForegroundItemList.contains(newBurudog) == false) {
                            mExistingForegroundItemList.add(newBurudog);
                        }
                    }
                    // else if .... other items
                }
                //draw or remove items
                synchronized(mExistingForegroundItemLock) {
                    if (mExistingBackgroundItemList.size() > 0) {
                        Iterator<Item> it = mExistingBackgroundItemList.iterator();
                        while (it.hasNext()) {
                            Item curItem = it.next();
                            curItem.drawMyselfAndCalcurateNext(canvas);
                            if (curItem.getX() == 0) { // Reached very left
                                it.remove();
                            }
                        }
                    }
                }

                if (mExistingForegroundItemList.size() > 0) {
                    Iterator<Item> it = mExistingForegroundItemList.iterator();
                    while (it.hasNext()) {
                        Item curItem = it.next();
                        curItem.drawMyselfAndCalcurateNext(canvas);
                        if ((curItem.getY() <= 0  && curItem.getVy() < 0 ) || (curItem.getY() >= mH   && curItem.getVy() > 0 )) {
                            curItem.setVy(-1 * curItem.getVy()); //Reached boarder
                        }

                        if (curItem.getX() == 0) { // Reached very left
                            it.remove();
                        }
                    }
                }

                //draw Tinnie
                mTinnie.drawMyselfAndCalcurateNext(canvas);

                //Judge collision and show effect if necessary
                if (mExistingForegroundItemList.size() > 0) {
                    for (Item item : mExistingForegroundItemList) {
                        if (item.isBackground() != true && item.isContacted() == false) {
                            int centerXOfTinnie = mTinnie.getX() + mTinnie.getW() / 2;
                            int centerYOfTinnie = mTinnie.getY() + mTinnie.getH() / 2;
                            if ((centerXOfTinnie >= item.getX() && centerXOfTinnie <= item.getX() + item.getW()) &&
                                    (centerYOfTinnie >= item.getY() && centerYOfTinnie <= item.getY() + item.getY())) {
                                Log.i(TAG, "collision!");
                                item.setContacted(true);
                                if (item.isGood()) {
                                    mTinnie.changeStatusHappy();
                                    mScore = mScore + 1;
                                } else {
                                    mTinnie.changeStatusDamaged();
                                    mScore = mScore - 1;
                                }
                            }
                        }
                    }
                }

                //draw Score
                drawScore(canvas);

                mHolder.unlockCanvasAndPost(canvas);
            }

            try {
                    //Log.v(TAG, "Thread running");
                    Thread.sleep(mTimeDelta);
            } catch (InterruptedException ex) {
                    Log.w(TAG, "interrupted");
            }
        }
    }

    private void drawScore(Canvas canvas) {
         canvas.drawText(String.valueOf(mScore), 100, 250, mPaint);
    }
}
