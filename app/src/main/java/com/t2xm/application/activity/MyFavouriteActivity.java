package com.t2xm.application.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.t2xm.R;

public class MyFavouriteActivity extends AppCompatActivity {
    private TabLayout tl_favourite_tab;
    private RecyclerView rv_favouriteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        tl_favourite_tab = findViewById(R.id.tl_favorite_tab);
        rv_favouriteItem = findViewById(R.id.rv_favourite_item);

        tl_favourite_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //TODO display item
                if (tab.getText().equals("All")) {

                } else if (tab.getText().equals("Do")) {

                } else if (tab.getText().equals("Stay")) {

                } else if (tab.getText().equals("Eat")) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}