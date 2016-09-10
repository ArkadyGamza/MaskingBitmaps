package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;

public class MaskedDrawableBitmapShader extends MaskedDrawable{

    private Bitmap mPictureBitmap;
    private Bitmap mMaskBitmap;
    private final Paint mPaintShader = new Paint();
    private BitmapShader mBitmapShader;

    public static MaskedDrawableFactory getFactory(){
        return new MaskedDrawableFactory() {
            @Override
            public MaskedDrawable createMaskedDrawable() {
                return new MaskedDrawableBitmapShader();
            }
        };
    }

    @Override
    public void setMaskBitmap(Bitmap maskBitmap) {
        mMaskBitmap = maskBitmap;
        updateScaleMatrix();
    }

    @Override
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
        int pictureW = mPictureBitmap.getWidth();
        int pictureH = mPictureBitmap.getHeight();

        float wScale = maskW / (float) pictureW;
        float hScale = maskH / (float) pictureH;

        float scale = Math.max(wScale, hScale);

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate((maskW - pictureW * scale) / 2f, (maskH - pictureH * scale) / 2f);
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
