package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

public class MaskedDrawablePorterDuffNoBuffer extends MaskedDrawable{

    private Bitmap mPictureBitmap;
    private Bitmap mMaskBitmap;
    private final Paint mPaintDstOver = new Paint();
    private final Paint mPaintClear = new Paint();

    public static MaskedDrawableFactory getFactory(){
        return new MaskedDrawableFactory() {
            @Override
            public MaskedDrawable createMaskedDrawable() {
                return new MaskedDrawablePorterDuffNoBuffer();
            }
        };
    }

    public MaskedDrawablePorterDuffNoBuffer() {
        mPaintDstOver.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        mPaintClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
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
    public void draw(Canvas canvas) {
        if (mPictureBitmap == null || mMaskBitmap == null) {
            return;
        }

        //draw the mask with clear mode
        canvas.drawBitmap(mMaskBitmap, 0, 0, mPaintClear);

        //draw picture with dst over mode
        canvas.drawBitmap(mPictureBitmap, 0, 0, mPaintDstOver);
    }

    //just to show that alpha is not working correctly with this implementation
    @Override
    public void setAlpha(int alpha) {
        mPaintDstOver.setAlpha(alpha);
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
