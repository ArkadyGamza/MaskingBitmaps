package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class MaskedDrawableBitmapShader extends Drawable {

    private Bitmap mPictureBitmap;
    private Bitmap mMaskBitmap;
    private final Paint mPaintShader = new Paint();
    private BitmapShader mBitmapShader;

    public void setMaskBitmap(Bitmap maskBitmap) {
        mMaskBitmap = maskBitmap;
        updateScaleMatrix();
    }

    public void setPictureBitmap(Bitmap src) {
        mPictureBitmap = src;
        mBitmapShader = new BitmapShader(mPictureBitmap,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT);
        mPaintShader.setShader(mBitmapShader);
        updateScaleMatrix();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mPictureBitmap == null || mMaskBitmap == null) {
            return;
        }
        canvas.drawBitmap(mMaskBitmap, 0, 0, mPaintShader);
    }

    private void updateScaleMatrix() {
        if (mPictureBitmap == null || mMaskBitmap == null) {
            return;
        }

        int maskW = mMaskBitmap.getWidth();
        int maskH = mMaskBitmap.getHeight();
        int kittenW = mPictureBitmap.getWidth();
        int kittenH = mPictureBitmap.getHeight();

        float wScale = maskW / (float) kittenW;
        float hScale = maskH / (float) kittenH;

        float scale = Math.max(wScale - 1, hScale - 1) + 1;

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate((maskW - kittenW * scale) / 2f, (maskH - kittenH * scale) / 2f);
        mBitmapShader.setLocalMatrix(matrix);
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
        return mMaskBitmap != null ? mMaskBitmap.getWidth() : super.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mMaskBitmap != null ? mMaskBitmap.getHeight() : super.getIntrinsicHeight();
    }
}
