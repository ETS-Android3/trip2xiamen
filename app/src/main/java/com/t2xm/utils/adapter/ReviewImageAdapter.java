package com.t2xm.utils.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.application.activity.ReviewActivity;

import java.util.List;

public class ReviewImageAdapter extends RecyclerView.Adapter<ReviewImageAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Bitmap> bitmapList;

    private AlertDialog.Builder builder;

    public ReviewImageAdapter(Context context, List<Bitmap> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
        this.inflater = LayoutInflater.from(this.context);

        builder = new AlertDialog.Builder(context)
                .setMessage("Delete this image?")
                .setNegativeButton("No", null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_review_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.iv_image.setImageBitmap(bitmapList.get(position));
        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ReviewActivity a = ((ReviewActivity) context);
                        a.bitmapList.remove(position);
                        a.reduceNumberOfImages();
                        a.imageAdapter.notifyDataSetChanged();
                    }
                }).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bitmapList != null ? bitmapList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_image;
        public ImageView iv_delete;

        public ViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
            iv_delete = view.findViewById(R.id.iv_delete);
        }
    }
}
