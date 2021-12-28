package com.t2xm.utils.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;

import java.util.List;

public class ReviewImageViewAdapter extends RecyclerView.Adapter<ReviewImageViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Bitmap> bitmapList;

    public ReviewImageViewAdapter(Context context, List<Bitmap> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_review_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.iv_image.setImageBitmap(bitmapList.get(position));
    }

    @Override
    public int getItemCount() {
        return bitmapList != null ? bitmapList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_image;

        public ViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
            view.findViewById(R.id.iv_delete).setVisibility(View.GONE);
        }
    }
}
