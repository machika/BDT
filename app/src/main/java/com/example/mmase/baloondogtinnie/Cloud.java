package com.example.mmase.baloondogtinnie;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

/**
 * Created by mmase on 2016/01/13.
 */

public class Cloud extends Item {

    Cloud(int x, int y, Resources resources) {
        super(x, y, 150, 100, -5, 0, true, true, 10, resources);
        mRid = R.drawable.cloud;
    }

    @Override
    public boolean isOnlyOne() {
        return false;
    }

    @Override
    public void calcUniqueMove() {
        //nothing
    }

}