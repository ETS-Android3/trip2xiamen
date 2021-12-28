package com.t2xm.application.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.entity.Item;
import com.t2xm.utils.adapter.ListItemAdapter;
import com.t2xm.utils.comparators.ItemComparator;

import java.util.List;

public class AttractionFragment extends Fragment {

    private AlertDialog.Builder filterByRatingBuilder;
    private String[] filterByRating = {"A-Z", "Z-A", "High to Low Rating", "Low to High Rating"};

    private AlertDialog.Builder filterByCategoryBuilder;
    private String[] filterByCategory = {"All", "To visit", "To eat", "To stay"};

    private ListItemAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tv_noItemsAvailable;

    private List<Item> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attractions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupFragmentComponents(view);
        setupFragmentListeners(view);
    }

    private void updateRecyclerView() {
        if (itemList != null) {
            recyclerView.setVisibility(View.VISIBLE);
            tv_noItemsAvailable.setVisibility(View.GONE);
            adapter = new ListItemAdapter(getActivity(), itemList);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            tv_noItemsAvailable.setVisibility(View.VISIBLE);
        }
    }

    private void setupFragmentComponents(View view) {
        itemList = ItemDao.getAllItemList();
        itemList.sort(ItemComparator.ratingAsc.reversed());

        adapter = new ListItemAdapter(getContext(), itemList);
        tv_noItemsAvailable = view.findViewById(R.id.tv_no_items_available);
        recyclerView = view.findViewById(R.id.rv_attractions_in_xiamen);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        filterByCategoryBuilder = new AlertDialog.Builder(view.getContext());
        filterByCategoryBuilder.setTitle("Filter items by category");
        filterByRatingBuilder = new AlertDialog.Builder(view.getContext());
        filterByRatingBuilder.setTitle("Filter items by category");
    }

    private void setupFragmentListeners(View view) {
        view.findViewById(R.id.btn_filter_by_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByCategoryBuilder.setItems(filterByCategory, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 1:
                                itemList = ItemDao.getItemListByCategory(1);
                                break;
                            case 2:
                                itemList = ItemDao.getItemListByCategory(2);
                                break;
                            case 3:
                                itemList = ItemDao.getItemListByCategory(3);
                                break;
                            default:
                                itemList = ItemDao.getAllItemList();
                        }
                        updateRecyclerView();
                    }
                });
                filterByCategoryBuilder.setNegativeButton("Cancel", null);
                filterByCategoryBuilder.create().show();
            }
        });

        view.findViewById(R.id.btn_filter_by_rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByRatingBuilder.setItems(filterByRating, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                itemList.sort(ItemComparator.alphabetAsc);
                                break;
                            case 1:
                                itemList.sort(ItemComparator.alphabetAsc.reversed());
                                break;
                            case 2:
                                itemList.sort(ItemComparator.ratingAsc.reversed());
                                break;
                            case 3:
                                itemList.sort(ItemComparator.ratingAsc);
                                break;
                        }
                        updateRecyclerView();
                    }
                });
                filterByRatingBuilder.setNegativeButton("Cancel", null);
                filterByRatingBuilder.create().show();
            }
        });
    }
}
