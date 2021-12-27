package com.t2xm.application.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.t2xm.R;
import com.t2xm.application.activity.MyFavouriteActivity;
import com.t2xm.application.activity.MyReviewsActivity;
import com.t2xm.application.activity.SettingsActivity;
import com.t2xm.dao.UserDao;
import com.t2xm.entity.User;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.valuesConverter.ImageUtil;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iv_profileImage = view.findViewById(R.id.iv_profile_image);
        String username = SharedPreferenceUtil.getUsername(getActivity().getApplicationContext());

        ((TextView) view.findViewById(R.id.tv_username)).setText(username);
        User user = UserDao.getUserInfoByUsername(username);
        if (user.profileImg != null) {
            iv_profileImage.setImageBitmap(ImageUtil.byteArrayToBitmap(user.profileImg));
        } else {
            iv_profileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_24));
            iv_profileImage.setColorFilter(R.color.black);
        }

        view.findViewById(R.id.btn_my_favourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), MyFavouriteActivity.class));
            }
        });

        view.findViewById(R.id.btn_my_reviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), MyReviewsActivity.class));
            }
        });

        view.findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), SettingsActivity.class));
            }
        });
    }
}
