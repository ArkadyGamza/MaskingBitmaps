package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Bitmap mPictureBitmap;
    private Bitmap mMaskBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mImageView = (ImageView) findViewById(R.id.imageView1);
        loadImages();
    }

    private void loadImages() {
        mPictureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
        mMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mask_circle).extractAlpha();
    }

    public void setDrawablePDDstIn(View view) {
        MaskedDrawablePorterDuffDstIn maskedDrawable = new MaskedDrawablePorterDuffDstIn();
        maskedDrawable.setPictureBitmap(mPictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmap);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawablePDSrcIn(View view) {
        MaskedDrawablePorterDuffSrcIn maskedDrawable = new MaskedDrawablePorterDuffSrcIn();
        maskedDrawable.setPictureBitmap(mPictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmap);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawablePDNoBuffer(View view) {
        MaskedDrawablePorterDuffNoBuffer maskedDrawable = new MaskedDrawablePorterDuffNoBuffer();
        maskedDrawable.setPictureBitmap(mPictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmap);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawablePDNoBufferAlpha(View view) {
        MaskedDrawablePorterDuffNoBuffer maskedDrawable = new MaskedDrawablePorterDuffNoBuffer();
        maskedDrawable.setPictureBitmap(mPictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmap);
        maskedDrawable.setAlpha(128);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawableShader(View view) {
        MaskedDrawableBitmapShader maskedDrawable = new MaskedDrawableBitmapShader();
        maskedDrawable.setPictureBitmap(mPictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmap);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawableShaderAlpha(View view) {
        MaskedDrawableBitmapShader maskedDrawable = new MaskedDrawableBitmapShader();
        maskedDrawable.setPictureBitmap(mPictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmap);
        maskedDrawable.setAlpha(128);
        mImageView.setImageDrawable(maskedDrawable);
    }

}
