package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public class TextMaskDrawableBitmapShader extends MaskedDrawable {

    private Bitmap mPictureBitmap;
    private final Paint mPaintShader = new Paint();
    private BitmapShader mBitmapShader;

    public static MaskedDrawableFactory getFactory(){
        return new MaskedDrawableFactory() {
            @Override
            public MaskedDrawable createMaskedDrawable() {
                return new TextMaskDrawableBitmapShader();
            }
        };
    }

    @Override
    public void setPictureBitmap(Bitmap src) {
        mPictureBitmap = src;
        mBitmapShader = new BitmapShader(mPictureBitmap,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT);
        mPaintShader.setShader(mBitmapShader);

        mPaintShader.setTextSize(getIntrinsicHeight());
        mPaintShader.setStyle(Paint.Style.FILL);
        mPaintShader.setTextAlign(Paint.Align.CENTER);
        mPaintShader.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    @Override
    public void setMaskBitmap(Bitmap maskBitmap) {
        // NO-OP
    }

    @Override
    public void draw(Canvas canvas) {
        if (mPictureBitmap == null) {
            return;
        }
        canvas.drawText("A", getIntrinsicWidth() / 2, getIntrinsicHeight() * 0.9f, mPaintShader);
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
