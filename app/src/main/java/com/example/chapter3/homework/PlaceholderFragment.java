package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {

    View view;
    private AnimatorSet animatorSet;
    private AnimatorSet animatorSet1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        ListView listview = view.findViewById(R.id.listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getData());
        listview.setAdapter(arrayAdapter);
        return view;
    }

    private List<String> getData(){
        List<String> data = new ArrayList<String>();
        for(int i = 0;i <20;i++) {
            data.add(i+"");
        }
        return data;


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                final LottieAnimationView animationView1 = view.findViewById(R.id.animation_view);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(animationView1,"alpha",1f,0f);
                alpha.setInterpolator(new LinearInterpolator());
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(alpha);
                final ListView lv = view.findViewById(R.id.listview);
                ObjectAnimator alpha1 = ObjectAnimator.ofFloat(lv,"alpha",0f,1f);
                alpha1.setInterpolator(new LinearInterpolator());
                animatorSet.playTogether(alpha,alpha1);
                animatorSet.start();
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入

            }
        }, 5000);
    }
}
