package com.t2xm.application.fragment;

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
import com.t2xm.dao.ReviewDao;
import com.t2xm.entity.Item;
import com.t2xm.entity.Review;
import com.t2xm.utils.adapter.HomeReviewAdapter;
import com.t2xm.utils.adapter.TopPlacesAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends Fragment {

    private List<Item> itemList;
    List<Review> topReviewList;

    private View view;
    private TextView tv_noItemsAvailable;

    private RecyclerView rv_topPlaces;
    private RecyclerView rv_latestReviews;

    private TopPlacesAdapter topPlacesAdapter;
    private HomeReviewAdapter homeReviewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        tv_noItemsAvailable = view.findViewById(R.id.tv_no_items_available) ;
        rv_topPlaces = view.findViewById(R.id.rv_top_places) ;
        rv_latestReviews = view.findViewById(R.id.rv_latest_reviews) ;

        updateTopPlaces();
        updateLatestReviews();
    }

    public void updateTopPlaces() {
        itemList = ItemDao.get5TopRatingItems();

        if (itemList != null) {
            rv_topPlaces = view.findViewById(R.id.rv_top_places);

            topPlacesAdapter = new TopPlacesAdapter(getContext(), itemList);
            rv_topPlaces.setAdapter(topPlacesAdapter);
            rv_topPlaces.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }

    public void updateLatestReviews() {
        topReviewList = ReviewDao.get5LatestReview();
        if (topReviewList != null) {
            rv_latestReviews.setVisibility(View.VISIBLE);
            tv_noItemsAvailable.setVisibility(View.GONE);

            List<Item> reviewItemList = new ArrayList<>();
            for (Review review : topReviewList) {
                Item item = ItemDao.getItemByItemId(review.itemId);
                if (item != null) {
                    reviewItemList.add(item);
                }
            }
            if (reviewItemList.size() > 0) {
                rv_latestReviews = view.findViewById(R.id.rv_latest_reviews);
                homeReviewAdapter = new HomeReviewAdapter(getContext(), topReviewList, reviewItemList);
                rv_latestReviews.setAdapter(homeReviewAdapter);
                rv_latestReviews.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        } else {
            rv_latestReviews.setVisibility(View.GONE);
            tv_noItemsAvailable.setVisibility(View.VISIBLE);
        }
    }
}
