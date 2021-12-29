package com.t2xm.application.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t2xm.R;
import com.t2xm.application.fragment.AttractionFragment;
import com.t2xm.application.fragment.HomeFragment;
import com.t2xm.application.fragment.ProfileFragment;
import com.t2xm.application.fragment.RecommendFragment;
import com.t2xm.utils.values.RequestCode;

public class MainActivity extends AppCompatActivity {

    private final HomeFragment HOME_FRAGMENT = new HomeFragment();
    private final RecommendFragment RECOMMEND_FRAGMENT = new RecommendFragment();
    private final AttractionFragment ATTRACTION_FRAGMENT = new AttractionFragment();
    private final ProfileFragment PROFILE_FRAGMENT = new ProfileFragment();

    private Fragment activeFragment = HOME_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup bottom navigation
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, activeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bn_home:
                        activeFragment = HOME_FRAGMENT;
                        break;
                    case R.id.bn_recommend:
                        activeFragment = RECOMMEND_FRAGMENT;
                        break;
                    case R.id.bn_attraction:
                        activeFragment = ATTRACTION_FRAGMENT;
                        break;
                    case R.id.bn_profile:
                        activeFragment = PROFILE_FRAGMENT;
                        break;
                    default:
                        return false;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, activeFragment).commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppCompatActivity activity = this;
        new AlertDialog.Builder(activity)
                .setTitle("Exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close application
                        activity.finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println(requestCode  + ", " + resultCode);

        if(requestCode == RequestCode.START_SETTINGS && activeFragment == PROFILE_FRAGMENT) {
            if(resultCode == RESULT_OK) {
                PROFILE_FRAGMENT.updateUserInformation();
            }
        }
        if(requestCode == RequestCode.VIEW_ITEM_DETAILS && activeFragment == RECOMMEND_FRAGMENT) {
            if(resultCode == RESULT_OK) {
                RECOMMEND_FRAGMENT.updateTopPlaces();
                RECOMMEND_FRAGMENT.updateLatestReviews();
            }
        }
    }
}