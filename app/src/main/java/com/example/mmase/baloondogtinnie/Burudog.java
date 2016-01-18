package com.example.mmase.baloondogtinnie;

import android.content.res.Resources;

/**
 * Created by mmase on 2016/01/13.
 */
public class Burudog extends Item {

    Burudog(int x, int y, Resources resources) {
        super(x, y, 150, 215, -3, 0, false, false, 10, resources);
        mRid = R.drawable.burudo;
    }

    @Override
    public boolean isOnlyOne() {
        return true;
    }

    @Override
    public void calcUniqueMove() {
        if (Math.random() <= 0.1) {
            int possibleChange = (int)((Math.random() - 0.5)*5);
            mVy = mVy + possibleChange;
        }
    }

}