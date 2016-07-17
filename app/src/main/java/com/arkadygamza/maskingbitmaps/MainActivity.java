package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Bitmap pictureBitmap;
    private Bitmap mMaskBitmapAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mImageView = (ImageView) findViewById(R.id.imageView1);
        loadImages();
    }

    private void loadImages() {
        pictureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
        mMaskBitmapAlpha = BitmapFactory.decodeResource(getResources(), R.drawable.mask_circle).extractAlpha();
    }

    public void setDrawablePD(View view) {
        MaskedDrawablePorterDuff maskedDrawable = new MaskedDrawablePorterDuff();
        maskedDrawable.setPictureBitmap(pictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmapAlpha);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawablePDNoBuffer(View view) {
        MaskedDrawablePorterDuffNoBuffer maskedDrawable = new MaskedDrawablePorterDuffNoBuffer();
        maskedDrawable.setPictureBitmap(pictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmapAlpha);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawablePDNoBufferAlpha(View view) {
        MaskedDrawablePorterDuffNoBuffer maskedDrawable = new MaskedDrawablePorterDuffNoBuffer();
        maskedDrawable.setPictureBitmap(pictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmapAlpha);
        maskedDrawable.setAlpha(128);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawableShader(View view) {
        MaskedDrawableBitmapShader maskedDrawable = new MaskedDrawableBitmapShader();
        maskedDrawable.setPictureBitmap(pictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmapAlpha);
        mImageView.setImageDrawable(maskedDrawable);
    }

    public void setDrawableShaderAlpha(View view) {
        MaskedDrawableBitmapShader maskedDrawable = new MaskedDrawableBitmapShader();
        maskedDrawable.setPictureBitmap(pictureBitmap);
        maskedDrawable.setMaskBitmap(mMaskBitmapAlpha);
        maskedDrawable.setAlpha(128);
        mImageView.setImageDrawable(maskedDrawable);
    }

}
