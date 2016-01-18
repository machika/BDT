package com.example.mmase.baloondogtinnie;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by mmase on 2016/01/13.
 */
public abstract class Item {
    protected int mX;
    protected int mY;
    protected int mVx;
    protected int mVy;
    protected int mW;
    protected int mH;
    private boolean mBackground;
    private boolean mGood; // Good effect (plus 1 point) or not (minus 1 point), no meaning for background
    private Bitmap mBmp;
    protected Resources mResources;
    protected int mRid;
    protected boolean mContacted;
    protected int mYBoarder;

    Item(int x, int y, int w, int h, int vx, int vy, boolean background, boolean good, int probability, Resources resources) {
        mX = x;
        mY = y;
        mW = w;
        mH = h;
        mVx = vx;
        mVy = vy;
        mBackground = background;
        mGood = good;
        mBmp = null;
        mResources = resources;
        mRid = 0;
        mContacted = false;
        mYBoarder = 0;
    };

    public boolean isBackground() { return mBackground; }
    public boolean isGood() { return mGood; }
    public abstract boolean isOnlyOne();

    public void drawMyselfAndCalcurateNext(Canvas canvas) {
        if (mBmp == null) {
            mBmp = BitmapFactory.decodeResource(mResources, mRid);
        }
        if (mBmp != null) {
            canvas.drawBitmap(mBmp, null, new Rect(mX, mY, mX + mW, mY + mH), null);
            mX = mX + mVx;
            mY = mY + mVy;
            calcUniqueMove();
        }

    }

    public abstract void calcUniqueMove(); //Add unique move by modifying vx and vy, or do nothing

    public int getX() { return mX; }
    public int getY() { return mY; }
    public int getVx() { return mVx; }
    public int getVy() { return mVy; }
    public void setVy(int vy) { mVy = vy; }
    public int getW() { return mW; }
    public int getH() { return mH; }

    @Override
    public boolean equals(Object obj) {
        return (this.getClass().getName().equals(obj.getClass().getName()));
    }

    public void setContacted(boolean b) { mContacted = b; }
    public boolean isContacted() { return mContacted; }

    public void setYBoarder(int YBoarder) {
        mYBoarder = YBoarder;
    }

    public void changeBmp(int rid) {
        mBmp = BitmapFactory.decodeResource(mResources, rid);
    }
    public void changeStatusDamaged() { }
    public void changeStatusHappy() { }

}



