package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.PathShape;

public class FixedMaskDrawableBitmapShader extends Drawable {

    private Bitmap mPictureBitmap;
    private final Paint mPaintShader = new Paint();
    private BitmapShader mBitmapShader;
    private Path mPath;


    public void setPictureBitmap(Bitmap src) {
        mPictureBitmap = src;
        mBitmapShader = new BitmapShader(mPictureBitmap,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT);
        mPaintShader.setShader(mBitmapShader);

        mPath = new Path();
        mPath.addOval(0, 0, getIntrinsicWidth(), getIntrinsicHeight(), Path.Direction.CW);
        Path subPath = new Path();
        subPath.addOval(getIntrinsicWidth() * 0.7f, getIntrinsicHeight() * 0.7f, getIntrinsicWidth(), getIntrinsicHeight(), Path.Direction.CW);
        mPath.op(subPath, Path.Op.DIFFERENCE);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mPictureBitmap == null) {
            return;
        }
        canvas.drawPath(mPath, mPaintShader);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaintShader.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaintShader.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public int getIntrinsicWidth() {
        return mPictureBitmap != null ? mPictureBitmap.getWidth() : super.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mPictureBitmap != null ? mPictureBitmap.getHeight() : super.getIntrinsicHeight();
    }
}
