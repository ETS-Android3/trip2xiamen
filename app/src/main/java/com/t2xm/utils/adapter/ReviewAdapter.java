package com.t2xm.utils.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.ReviewImageDao;
import com.t2xm.dao.UserDao;
import com.t2xm.entity.Review;
import com.t2xm.entity.User;
import com.t2xm.utils.valuesConverter.ImageUtil;

import java.util.ArrayList;
import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Review> reviewList;


    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_details_review, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Review review = getReviewByPosition(position);
        User user = UserDao.getUserInfoByUserId(review.userId);

        if (user != null) {
            if (user.profileImg != null && user.profileImg.length > 0) {
                viewHolder.iv_user_profileImage.setImageBitmap(ImageUtil.byteArrayToBitmap(user.profileImg));
            } else {
                viewHolder.iv_user_profileImage.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_person_24));
                viewHolder.iv_user_profileImage.setColorFilter(context.getColor(R.color.primary_color));
            }
            viewHolder.tv_username.setText(user.username);
        } else {
            viewHolder.iv_user_profileImage.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_person_24));
            viewHolder.tv_username.setText("-deleted-");
            viewHolder.iv_user_profileImage.setColorFilter(context.getColor(R.color.gray));
        }

        updateRatingStars(viewHolder, Double.valueOf(review.rating));
        viewHolder.tv_reviewText.setText(review.reviewText);

        List<byte[]> imageBytesList = ReviewImageDao.getReviewImageByteListByReviewId(review.reviewId);
        List<Bitmap> bitmapList = null;
        if (imageBytesList != null && imageBytesList.size() > 0) {
            bitmapList = new ArrayList<>();
            for (byte[] bytes : imageBytesList) {
                bitmapList.add(ImageUtil.byteArrayToBitmap(bytes));
            }
        }
        ReviewImageViewAdapter reviewImageAdapter = new ReviewImageViewAdapter(context, bitmapList);
        viewHolder.rv_reviewImages.setAdapter(reviewImageAdapter);
        viewHolder.rv_reviewImages.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return reviewList != null ? reviewList.size() : 0;
    }

    private Review getReviewByPosition(int position) {
        return reviewList.get(position);
    }

    private void updateRatingStars(ViewHolder viewHolder, Double rating) {
        int index = 0;
        while (index < 5) {
            if (rating >= 1) {
                viewHolder.iv_starList.get(index).setImageResource(R.drawable.ic_baseline_star_24);
                rating -= 1;
            } else if (rating >= 0.5) {
                viewHolder.iv_starList.get(index).setImageResource(R.drawable.ic_baseline_star_half_24);
                rating = Math.floor(rating);
            } else {
                viewHolder.iv_starList.get(index).setImageResource(R.drawable.ic_baseline_star_border_24);
                rating = Math.floor(rating);
            }
            index++;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_user_profileImage;
        public TextView tv_username;
        public List<ImageView> iv_starList = new ArrayList<>();
        public TextView tv_reviewText;
        public RecyclerView rv_reviewImages;

        public ViewHolder(View view) {
            super(view);
            iv_user_profileImage = view.findViewById(R.id.iv_user_profile_image);
            tv_username = view.findViewById(R.id.tv_username);
            iv_starList.add(view.findViewById(R.id.iv_star_1));
            iv_starList.add(view.findViewById(R.id.iv_star_2));
            iv_starList.add(view.findViewById(R.id.iv_star_3));
            iv_starList.add(view.findViewById(R.id.iv_star_4));
            iv_starList.add(view.findViewById(R.id.iv_star_5));
            tv_reviewText = view.findViewById(R.id.tv_review_text);
            rv_reviewImages = view.findViewById(R.id.rv_review_images);
        }
    }
}
