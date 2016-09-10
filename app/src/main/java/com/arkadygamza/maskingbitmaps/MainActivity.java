package com.arkadygamza.maskingbitmaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.arkadygamza.maskingbitmaps.utils.NavigationDecorator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationDecorator.setContentView(this, R.layout.main_activity);

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.mainActivity_root, new DemoFragment()).commit();
    }

}
