package com.t2xm.application.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.t2xm.R;
import com.t2xm.application.activity.MyFavouriteActivity;
import com.t2xm.application.activity.SettingsActivity;
import com.t2xm.utils.SharedPreferenceUtil;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String username = SharedPreferenceUtil.getUsername(getActivity().getApplicationContext());

        //TODO set profile image
        ((TextView)view.findViewById(R.id.tv_username)).setText(username);

        view.findViewById(R.id.btn_my_favourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), MyFavouriteActivity.class));
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
