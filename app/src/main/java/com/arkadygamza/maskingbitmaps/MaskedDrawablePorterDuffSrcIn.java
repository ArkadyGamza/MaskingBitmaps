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

public class MaskedDrawablePorterDuffSrcIn extends MaskedDrawable {

    private Bitmap mPictureBitmap;
    private Bitmap mMaskBitmap;
    private Bitmap mBufferBitmap;
    private Canvas mBufferCanvas;
    private final Paint mPaintSrcIn = new Paint();

    public static MaskedDrawableFactory getFactory() {
        return new MaskedDrawableFactory() {
            @Override
            public MaskedDrawable createMaskedDrawable() {
                return new MaskedDrawablePorterDuffSrcIn();
            }
        };
    }

    public MaskedDrawablePorterDuffSrcIn() {
        mPaintSrcIn.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    public void setPictureBitmap(Bitmap pictureBitmap) {
        mPictureBitmap = pictureBitmap;
        redrawBufferCanvas();
    }

    @Override
    public void setMaskBitmap(Bitmap maskBitmap) {
        mMaskBitmap = maskBitmap;
        redrawBufferCanvas();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        final int width = bounds.width();
        final int height = bounds.height();

        if (width <= 0 || height <= 0) {
            return;
        }

        if (mBufferBitmap != null
            && mBufferBitmap.getWidth() == width
            && mBufferBitmap.getHeight() == height) {
            return;
        }

        mBufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); //that's too bad
        mBufferCanvas = new Canvas(mBufferBitmap);
        redrawBufferCanvas();
    }

    private void redrawBufferCanvas() {
        if (mPictureBitmap == null || mMaskBitmap == null || mBufferCanvas == null) {
            return;
        }

        mBufferCanvas.drawBitmap(mMaskBitmap, 0, 0, null);
        mBufferCanvas.drawBitmap(mPictureBitmap, 0, 0, mPaintSrcIn);
    }

    @Override
    public void draw(Canvas canvas) {
        //dump the buffer
        canvas.drawBitmap(mBufferBitmap, 0, 0, null);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaintSrcIn.setAlpha(alpha);
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
