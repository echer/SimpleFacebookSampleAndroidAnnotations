package com.devmix.snapshot.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView.ScaleType;

public class FollowItem {
	private int id;
	final Bitmap mBitmap;
    final String mLine1;
    final String mLine2;
    final ScaleType mScaleType;

    FollowItem(Context c, int resid, String line1, String line2, ScaleType scaleType) {
        mBitmap = BitmapFactory.decodeResource(c.getResources(), resid);
        mLine1 = line1;
        mLine2 = line2;
        mScaleType = scaleType;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
