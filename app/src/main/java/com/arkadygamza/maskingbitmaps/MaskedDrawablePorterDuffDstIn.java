package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class MaskedDrawablePorterDuffDstIn extends MaskedDrawable{

    private Bitmap mPictureBitmap;
    private Bitmap mMaskBitmap;
    private Bitmap mBufferBitmap;
    private Canvas mBufferCanvas;
    private final Paint mPaintDstIn = new Paint();

    public static MaskedDrawableFactory getFactory(){
        return new MaskedDrawableFactory() {
            @Override
            public MaskedDrawable createMaskedDrawable() {
                return new MaskedDrawablePorterDuffDstIn();
            }
        };
    }

    public MaskedDrawablePorterDuffDstIn() {
        mPaintDstIn.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    public void setPictureBitmap(Bitmap pictureBitmap) {
        mPictureBitmap = pictureBitmap;
    }

    @Override
    public void setMaskBitmap(Bitmap maskBitmap) {
        mMaskBitmap = maskBitmap;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        final int width = bounds.width();
        final int height = bounds.height();

        if (width <= 0 || height <= 0) {
            return;
        }

        mBufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mBufferCanvas = new Canvas(mBufferBitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mPictureBitmap == null || mMaskBitmap == null) {
            return;
        }

        mBufferCanvas.drawBitmap(mPictureBitmap, 0, 0, null);
        mBufferCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaintDstIn);
        canvas.drawBitmap(mBufferBitmap, 0, 0, null);
    }

    @Override
    public void setAlpha(int alpha) {
        //Not implemented
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        //Not implemented
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
