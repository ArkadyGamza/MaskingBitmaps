package com.arkadygamza.maskingbitmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class DemoFragment extends Fragment {
    private static final Map<String, MaskedDrawable.MaskedDrawableFactory> sDrawableFactories = new LinkedHashMap<>();

    static {
        sDrawableFactories.put("Porter-Duff SrcIn", MaskedDrawablePorterDuffSrcIn.getFactory());
        sDrawableFactories.put("Porter-Duff no buffer", MaskedDrawablePorterDuffNoBuffer.getFactory());
        sDrawableFactories.put("Bitmap Shader", MaskedDrawableBitmapShader.getFactory());
        sDrawableFactories.put("Fixed mask", FixedMaskDrawableBitmapShader.getFactory());
        sDrawableFactories.put("Text mask", TextMaskDrawableBitmapShader.getFactory());
        sDrawableFactories.put("Porter-Duff DstIn :(", MaskedDrawablePorterDuffDstIn.getFactory());
    }

    private ImageView mImageView;
    private Bitmap mPictureBitmap;
    private Bitmap mMaskBitmap;
    private Spinner mSpinner;
    private CheckBox mAlphaCheckBox;
    private MaskedDrawable mMaskedDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.demoFragment_imageView);
        mSpinner = (Spinner) view.findViewById(R.id.demoFragment_spinner);
        mAlphaCheckBox = (CheckBox) view.findViewById(R.id.demoFragment_checkAlpha);
        loadImages();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
            android.R.layout.simple_spinner_item,
            new LinkedList<>(sDrawableFactories.keySet())
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String method = adapter.getItem(i);
                mMaskedDrawable = sDrawableFactories.get(method).createMaskedDrawable();
                mMaskedDrawable.setPictureBitmap(mPictureBitmap);
                mMaskedDrawable.setMaskBitmap(mMaskBitmap);
                mImageView.setImageDrawable(mMaskedDrawable);
                applyAlpha();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //NO-OP
            }
        });

        mAlphaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                applyAlpha();
            }
        });
    }

    private void applyAlpha() {
        if (mAlphaCheckBox == null){
            return;
        }
        if (mAlphaCheckBox.isChecked()){
            mMaskedDrawable.setAlpha(128);
        }else {
            mMaskedDrawable.setAlpha(255);
        }
        mImageView.invalidate();
    }

    private void loadImages() {
        mPictureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
        mMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mask_circle).extractAlpha();
    }
}
