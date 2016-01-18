package com.example.mmase.baloondogtinnie;

import android.content.res.Resources;

/**
 * Created by mmase on 2016/01/13.
 */
public class Bat extends Item {

    Bat(int x, int y, Resources resources) {
        super(x, y, 150, 250, -3, 0, false, false, 10, resources);
        mRid = R.drawable.bat;
    }

    @Override
    public boolean isOnlyOne() {
        return true;
    }

    @Override
    public void calcUniqueMove() {
        if (Math.random() <= 0.2) {
            int possibleChange = (int)(Math.cos(1F) * 5);
            mVy = mVy + possibleChange;
        }
    }

}
