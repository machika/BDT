package com.example.mmase.baloondogtinnie;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

/**
 * Created by mmase on 2016/01/15.
 */
public class Tinnie extends Item {
    private final double gravity = 0.3; //g
    private int mTimeDelta;
    enum TinnieStatus {NORMAL, HAPPY, DAMAGED, };
    private TinnieStatus mStatus;
    private int mStatusChangeCount;

    Tinnie(int x, int y, int w, int h, int vx, int vy, Resources resources, int timeDelta) {
        super(x, y, w, h, vx, vy, false, true, 100, resources);
        mTimeDelta = timeDelta;
        mRid = R.drawable.tinnie;
        mYBoarder = 0;
        mStatus = TinnieStatus.NORMAL;
        mStatusChangeCount = 0;
    }

    @Override
    public boolean isOnlyOne() {
        return true;
    }

    @Override
    public void calcUniqueMove() {
        if (mStatusChangeCount > 0) {
            mStatusChangeCount = mStatusChangeCount - 1;
            if (mStatusChangeCount <= 0) {
                changeBmp(R.drawable.tinnie);
            }
        }
        mVy = mVy + (int) (0.5 * gravity * mTimeDelta);
        if(mY + mH + mVy < mYBoarder && mY + mVy > 0) {
            //mY = mY + mVy;s
        }
        else  {
            mVy = (int) (- 0.8 * mVy);
            //mY = mY + mVy;
        }
    }

    public void setYBoarder(int h) {
        mYBoarder = h;
    }

    public void changeStatusDamaged() {
        mStatus = TinnieStatus.DAMAGED;
        mStatusChangeCount = 50;
        changeBmp(R.drawable.tinnie_damage);
    }

    public void changeStatusHappy() {
        mStatus = TinnieStatus.HAPPY;
        mStatusChangeCount = 50;
        changeBmp(R.drawable.tinnie_happy);
    }

}
