package com.t2xm.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t2xm.R;
import com.t2xm.fragment.DoFragment;
import com.t2xm.fragment.EatFragment;
import com.t2xm.fragment.HomeFragment;
import com.t2xm.fragment.ProfileFragment;
import com.t2xm.fragment.StayFragment;

public class MainActivity extends AppCompatActivity {

    private final HomeFragment HOME_FRAGMENT = new HomeFragment();
    private final DoFragment DO_FRAGMENT = new DoFragment();
    private final StayFragment STAY_FRAGMENT = new StayFragment();
    private final EatFragment EAT_FRAGMENT = new EatFragment();
    private final ProfileFragment PROFILE_FRAGMENT = new ProfileFragment();

    private Fragment activeFragment = HOME_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, activeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bn_home:
                        activeFragment = HOME_FRAGMENT;
                        break;
                    case R.id.bn_do:
                        activeFragment = DO_FRAGMENT;
                        break;
                    case R.id.bn_stay:
                        activeFragment = STAY_FRAGMENT;
                        break;
                    case R.id.bn_eat:
                        activeFragment = EAT_FRAGMENT;
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
}