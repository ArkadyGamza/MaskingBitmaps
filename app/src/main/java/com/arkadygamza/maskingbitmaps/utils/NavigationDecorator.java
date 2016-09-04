package com.arkadygamza.maskingbitmaps.utils;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.arkadygamza.maskingbitmaps.MainActivity;
import com.arkadygamza.maskingbitmaps.PerformanceActivity;
import com.arkadygamza.maskingbitmaps.R;

public class NavigationDecorator {

    public static void setContentView(@NonNull AppCompatActivity activity, @LayoutRes int layoutRes) {
        View rootView = activity.getLayoutInflater().inflate(R.layout.navigation, null);
        activity.setContentView(rootView);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        initNavigationDrawer(activity, rootView, toolbar);
        inflateContentStub(layoutRes, rootView);
    }

    private static void initNavigationDrawer(@NonNull final AppCompatActivity activity, @NonNull View rootView, Toolbar toolbar) {
        final DrawerLayout drawerLayout = (DrawerLayout) rootView.findViewById(R.id.navigation_drawerLayout);

        NavigationView navigationView = (NavigationView) rootView.findViewById(R.id.navigation_navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.navigationMenu_demo:
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        break;
                    case R.id.navigationMenu_performance:
                        activity.startActivity(new Intent(activity, PerformanceActivity.class));
                        break;
                }

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle =
            new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.openDrawer, R.string.closeDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private static void inflateContentStub(@LayoutRes int layoutRes, View rootView) {
        ViewStub viewStub = (ViewStub) rootView.findViewById(R.id.navigation_stub);
        viewStub.setLayoutResource(layoutRes);
        viewStub.inflate();
    }

}
