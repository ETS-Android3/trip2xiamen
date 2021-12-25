package com.t2xm.application.fragment;

import android.app.AlertDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.t2xm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Button> btnList_whyVisitXiamen;
    private List<String> resource_whyVisitXiamenLong;

    private AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        builder = new AlertDialog.Builder(getActivity());

        btnList_whyVisitXiamen = new ArrayList<>();
        btnList_whyVisitXiamen.add(view.findViewById(R.id.btn_why_visit_xiamen_1));
        btnList_whyVisitXiamen.add(view.findViewById(R.id.btn_why_visit_xiamen_2));
        btnList_whyVisitXiamen.add(view.findViewById(R.id.btn_why_visit_xiamen_3));
        Integer resourceId = getResources().getIdentifier("why_visit_long_arr", "array", getActivity().getPackageName());
        resource_whyVisitXiamenLong = Arrays.asList(getResources().getStringArray(resourceId));

        for(int i = 0 ; i < btnList_whyVisitXiamen.size() ; i++) {
            int finalI = i;
            btnList_whyVisitXiamen.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.setMessage(resource_whyVisitXiamenLong.get(finalI)).setPositiveButton("OK", null);
                    builder.create().show();
                }
            });
        }

        //TODO ViewPager
        ImageView iv_homeAnimation = view.findViewById(R.id.iv_home_animation);
        iv_homeAnimation.setBackgroundResource(R.drawable.animation_list);
        AnimationDrawable frameAnimation = (AnimationDrawable) iv_homeAnimation.getBackground();
        frameAnimation.start();
    }
}
