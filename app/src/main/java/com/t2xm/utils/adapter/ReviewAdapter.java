package com.t2xm.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.UserDao;
import com.t2xm.entity.Item;
import com.t2xm.entity.Review;
import com.t2xm.entity.User;
import com.t2xm.utils.ImageUtil;

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

        viewHolder.iv_user_profileImage.setImageBitmap(ImageUtil.byteArrayToBitmap(user.profileImg));
        viewHolder.tv_username.setText(user.username);
        //TODO requires rating
//        updateRatingStars(viewHolder, Double.valueOf(review.rating));
        viewHolder.tv_reviewText.setText(review.reviewText);
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
        }
    }
}
