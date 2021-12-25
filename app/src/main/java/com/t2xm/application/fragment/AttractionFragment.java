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

    private AlertDialog.Builder builder;
    private String[] filter = {"A-Z", "Z-A", "High to Low Rating", "Low to High Rating"};
    private Integer selected = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attractions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Item> itemList = ItemDao.getItemListByCategory(1);
        itemList.sort(ItemComparator.alphabetAsc);
        ListItemAdapter adapter = new ListItemAdapter(getContext(), itemList);
        RecyclerView recyclerView = view.findViewById(R.id.rv_attractions_in_xiamen);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Filter items by rating");

        view.findViewById(R.id.btn_filter_by_rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setSingleChoiceItems(filter, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selected = i;
                    }
                });
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (selected) {
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
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });
    }
}
