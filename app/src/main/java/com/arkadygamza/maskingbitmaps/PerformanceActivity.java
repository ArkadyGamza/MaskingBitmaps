package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arkadygamza.maskingbitmaps.utils.NavigationDecorator;

public class PerformanceActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Bitmap mPictureBitmap;
    private Bitmap mMaskBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationDecorator.setContentView(this, R.layout.performance_activity);
        loadImages();
        mRecyclerView = (RecyclerView) findViewById(R.id.performance_recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(new PerformanceAdapter());
    }

    private void loadImages() {
        mPictureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
        mMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mask_circle).extractAlpha();
    }

    private class PerformanceViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        public PerformanceViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_imageView);
        }
    }

    private class PerformanceAdapter extends RecyclerView.Adapter<PerformanceViewHolder> {

        @Override
        public PerformanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item, parent, false);
            return new PerformanceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PerformanceViewHolder holder, int position) {
            holder.mImageView.setImageDrawable(loadDrawable());
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        private Drawable loadDrawable() {
            MaskedDrawableBitmapShader drawableBitmapShader = new MaskedDrawableBitmapShader();
            drawableBitmapShader.setMaskBitmap(mMaskBitmap);
            drawableBitmapShader.setPictureBitmap(mPictureBitmap);
            return drawableBitmapShader;
        }
    }
}
