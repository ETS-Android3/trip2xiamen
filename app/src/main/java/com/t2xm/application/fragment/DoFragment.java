package com.t2xm.application.fragment;

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

import java.util.List;

public class DoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_do, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_filter_by_rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO alert dialog with checkbox
            }
        });

        List<Item> itemList = ItemDao.getItemListByCategory(1);
        ListItemAdapter adapter = new ListItemAdapter(getContext(), itemList);
        RecyclerView recyclerView = view.findViewById(R.id.rv_todo_in_xiamen);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
}
