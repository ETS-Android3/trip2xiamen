package com.t2xm.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.entity.Item;
import com.t2xm.utils.JsonUtil;

import java.util.List;


public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Item> itemList;


    public ListItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Item item = getItemByPosition(position);
        try {
            String firstImageName = (String) JsonUtil.mapJsonToObject(item.image, List.class).get(0);
            viewHolder.iv_itemImage.setImageResource(this.context.getResources().getIdentifier(firstImageName, "drawable", context.getPackageName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.tv_itemName.setText(item.itemName);
        viewHolder.tv_itemContent.setText(item.description);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private Item getItemByPosition(int position) {
        return itemList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_itemImage;
        public TextView tv_itemName;
        public TextView tv_itemRating;
        public TextView tv_itemContent;

        public ViewHolder(View view) {
            super(view);
            iv_itemImage = view.findViewById(R.id.iv_item_image);
            tv_itemName = view.findViewById(R.id.tv_item_name);
            tv_itemRating = view.findViewById(R.id.tv_item_rating);
            tv_itemContent = view.findViewById(R.id.tv_item_content);
        }
    }
}
