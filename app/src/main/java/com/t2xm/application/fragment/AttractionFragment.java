package com.t2xm.application.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private Integer selectedRating = 0;

    private AlertDialog.Builder filterByCategoryBuilder;
    private String[] filterByCategory = {"All", "To visit", "To eat", "To stay"};
    private Integer selectedCategory = 0;

    private List<Item> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attractions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemList = ItemDao.getItemListByCategory(1);
        itemList.sort(ItemComparator.alphabetAsc);
        ListItemAdapter adapter = new ListItemAdapter(getContext(), itemList);
        RecyclerView recyclerView = view.findViewById(R.id.rv_attractions_in_xiamen);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        filterByCategoryBuilder = new AlertDialog.Builder(view.getContext());
        filterByCategoryBuilder.setTitle("Filter items by category");

        view.findViewById(R.id.btn_filter_by_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByCategoryBuilder.setItems(filterByCategory, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            switch (selectedCategory) {
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
                            if(itemList != null) {
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                //TODO display no items message
                            }
                    }
                });
                filterByCategoryBuilder.setNegativeButton("Cancel", null);
                filterByCategoryBuilder.create().show();
            }
        });

        view.findViewById(R.id.btn_filter_by_rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByRatingBuilder.setSingleChoiceItems(filterByRating, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedRating = i;
                    }
                });
                filterByRatingBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (selectedRating) {
                            case 0:
                                itemList.sort(ItemComparator.alphabetAsc);
                                break;
                            case 1:
                                itemList.sort(ItemComparator.alphabetAsc.reversed());
                                break;
                            case 2:
                                itemList.sort(ItemComparator.ratingAsc);
                                break;
                            case 3:
                                itemList.sort(ItemComparator.ratingAsc.reversed());
                                break;
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                filterByRatingBuilder.setNegativeButton("Cancel", null);
                filterByRatingBuilder.create().show();
            }
        });
    }
}
