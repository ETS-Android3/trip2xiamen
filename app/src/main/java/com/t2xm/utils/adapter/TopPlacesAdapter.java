package com.t2xm.utils.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.application.activity.DetailsActivity;
import com.t2xm.entity.Item;
import com.t2xm.utils.JsonUtil;

import java.util.List;

public class TopPlacesAdapter extends RecyclerView.Adapter<TopPlacesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Item> itemList;


    public TopPlacesAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_top_place, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Item item = getItemByPosition(position);

        //setup item image
        try {
            String firstImageName = (String) JsonUtil.mapJsonToObject(item.image, List.class).get(0);
            viewHolder.iv_itemImage.setImageResource(this.context.getResources().getIdentifier(firstImageName, "drawable", context.getPackageName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //setup item name
        viewHolder.tv_itemName.setText(item.itemName);

        //setup click listener
        viewHolder.rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("itemId", item.itemId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private Item getItemByPosition(int position) {
        return itemList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_container;
        public ImageView iv_itemImage;
        public TextView tv_itemName;

        public ViewHolder(View view) {
            super(view);
            rl_container = view.findViewById(R.id.rl_list_container);
            iv_itemImage = view.findViewById(R.id.iv_item_image);
            tv_itemName = view.findViewById(R.id.tv_item_name);
        }
    }
}
