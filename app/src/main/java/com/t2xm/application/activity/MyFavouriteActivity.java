package com.t2xm.application.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.t2xm.R;
import com.t2xm.dao.FavouriteItemDao;
import com.t2xm.entity.Item;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.adapter.ListItemAdapter;

import java.util.List;

public class MyFavouriteActivity extends AppCompatActivity {

    private Activity activity;

    private TabLayout tl_favourite_tab;
    private RecyclerView rv_favouriteItem;
    private RelativeLayout rl_noItems;

    private ListItemAdapter adapter;

    private List<Item> itemList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        activity = this;

        tl_favourite_tab = findViewById(R.id.tl_favorite_tab);
        rv_favouriteItem = findViewById(R.id.rv_favourite_item);
        rl_noItems = findViewById(R.id.rl_no_items);

        itemList = FavouriteItemDao.getFavouriteItemListByUsername(SharedPreferenceUtil.getUsername(this));
        username = SharedPreferenceUtil.getUsername(activity);

        if (itemList != null) {
            rv_favouriteItem.setVisibility(View.VISIBLE);
            rl_noItems.setVisibility(View.GONE);
            adapter = new ListItemAdapter(activity, itemList);
            rv_favouriteItem.setAdapter(adapter);
            rv_favouriteItem.setLayoutManager(new LinearLayoutManager(activity));
        } else {
            rv_favouriteItem.setVisibility(View.GONE);
            rl_noItems.setVisibility(View.VISIBLE);
        }

        tl_favourite_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("All")) {
                    itemList = FavouriteItemDao.getFavouriteItemListByUsername(username);
                } else if (tab.getText().equals("Do")) {
                    itemList = FavouriteItemDao.getItemListByUsernameAndCategory(username, 1);
                } else if (tab.getText().equals("Stay")) {
                    itemList = FavouriteItemDao.getItemListByUsernameAndCategory(username, 2);
                } else if (tab.getText().equals("Eat")) {
                    itemList = FavouriteItemDao.getItemListByUsernameAndCategory(username, 3);
                }
                if (itemList != null && itemList.size() > 0) {
                    rv_favouriteItem.setVisibility(View.VISIBLE);
                    rl_noItems.setVisibility(View.GONE);
                    rv_favouriteItem.setAdapter(new ListItemAdapter(activity, itemList));
                } else {
                    rv_favouriteItem.setVisibility(View.GONE);
                    rl_noItems.setVisibility(View.VISIBLE);
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