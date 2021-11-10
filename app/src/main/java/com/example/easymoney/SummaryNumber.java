package com.example.easymoney;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class SummaryNumber extends Fragment {


    TextView summary;

    public SummaryNumber() {
        // Required empty public constructor
    }

    public static SummaryNumber newInstance(String param1, String param2) {
        SummaryNumber fragment = new SummaryNumber();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        summary = view.findViewById(R.id.text_home);
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    void updateUI(){
        DBHelper helper = new DBHelper(getActivity());
        summary.setText("RM" + helper.getSum());
    }
}